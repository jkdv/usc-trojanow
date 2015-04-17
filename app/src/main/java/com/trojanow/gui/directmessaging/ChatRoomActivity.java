package com.trojanow.gui.directmessaging;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.trojanow.R;
import com.trojanow.gui.entity.Chat;
import com.trojanow.gui.entity.ChatArray;
import com.trojanow.gui.entity.ChatArrayAdapter;
import com.trojanow.gui.util.NoticeDialogFragment;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class ChatRoomActivity extends ActionBarActivity implements
        AdapterView.OnItemLongClickListener,
        NoticeDialogFragment.OnClickListener {
    private static final int RESULT_ON_RESPONSE = 1;
    private static final int REFRESH_INTERVAL = 2500;

    private Chat mChat;
    private ChatArrayAdapter chatArrayAdapter;
    private Handler refreshHandler;

    private EditText editTextContent;
    private ImageButton buttonSend;
    private ListView listView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        mChat = new Chat(getIntent().getExtras());
        setTitle(mChat.getUserToFirstName() + " " + mChat.getUserToLastName());
        refreshHandler = new Handler(Looper.getMainLooper());

        chatArrayAdapter =
                new ChatArrayAdapter(this, R.layout.item_chat, new ArrayList<Chat>());
        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(chatArrayAdapter);
        listView.setOnItemLongClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextContent = (EditText) findViewById(R.id.editTextContent);
        buttonSend = (ImageButton) findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendClick();
            }
        });

        showChatHistory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat_room, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method handles commands that are selected in the action bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete_all_messages) {
            DialogFragment noticeDialogFragment = new NoticeDialogFragment();
            Chat chat = new Chat();
            chat.putMessage("Delete entire conversation?");
            noticeDialogFragment.setArguments(chat.toBundle());
            noticeDialogFragment.show(getFragmentManager(), "notice");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        Intent intent = new DeleteChatsIntent(
                new DeleteChatsResultReceiver(), mChat.toBundle());
        sendBroadcast(intent);
    }

    @Override
    public void onNegativeClick(DialogFragment dialog) { /* Do nothing */ }

    private void showChatHistory() {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new ReadChatHistoryIntent(new ReadChatHistoryResultReceiver(), mChat.toBundle());
        sendBroadcast(intent);
    }

    private void refresh() {
        Intent intent = new ReadChatHistoryIntent(new ReadChatHistoryResultReceiver(), mChat.toBundle());
        sendBroadcast(intent);
    }

    private void onSendClick() {
        Log.d("ChatRoomActivity", "onSendClick");
        String content = editTextContent.getText().toString();
        if (!content.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            Chat chat = new Chat(mChat.toBundle());
            chat.putContent(content);
            Intent intent = new SendChatIntent(new SendChatResultReceiver(), chat.toBundle());
            sendBroadcast(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("ChatRoomActivity", "onItemClick");
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshHandler.postDelayed(refreshChatRunnable, REFRESH_INTERVAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        refreshHandler.removeCallbacks(refreshChatRunnable);
    }

    /**
     *
     */
    private Runnable refreshChatRunnable = new Runnable() {
        @Override
        public void run() {
            refresh();
            refreshHandler.postDelayed(this, REFRESH_INTERVAL);
        }
    };

    /**
     *
     */
    private class DeleteChatsResultReceiver extends ResultReceiver {
        public DeleteChatsResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d("DeleteChatsResult", "onReceiveResult");
            progressBar.setVisibility(View.INVISIBLE);
            if (resultCode == RESULT_ON_RESPONSE) {
                if (resultData != null) {
                    Chat chat = new Chat(resultData);
                    String msg = chat.getMessage();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    if (chat.getStatusAsInt() == HttpURLConnection.HTTP_OK) {
                        chatArrayAdapter.clear();
                        chatArrayAdapter.notifyDataSetChanged();
                        finish();
                    }
                }
            }
        }
    }

    /**
     *
     */
    private class ReadChatHistoryResultReceiver extends ResultReceiver {
        public ReadChatHistoryResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d("ReadChatHistoryResult", "onReceiveResult");
            progressBar.setVisibility(View.INVISIBLE);
            if (resultCode == RESULT_ON_RESPONSE) {
                ArrayList<Bundle> bundles =
                        resultData.getParcelableArrayList(ChatArray.EXTRA_CHATS);
                if (bundles != null) {
                    ChatArray chats = new ChatArray(bundles);
                    chatArrayAdapter.clear();
                    chatArrayAdapter.addAll(chats.toChatArrayList());
                    chatArrayAdapter.notifyDataSetChanged();

                    listView.smoothScrollToPosition(chatArrayAdapter.getCount());
                }
            }
        }
    }

    /**
     *
     */
    private class SendChatResultReceiver extends ResultReceiver {
        public SendChatResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d("SendChatResult", "onReceiveResult");
            progressBar.setVisibility(View.INVISIBLE);
            if (resultCode == RESULT_ON_RESPONSE) {
                ArrayList<Bundle> bundles =
                        resultData.getParcelableArrayList(ChatArray.EXTRA_CHATS);
                if (bundles != null) {
                    ChatArray chats = new ChatArray(bundles);
                    chatArrayAdapter.clear();
                    chatArrayAdapter.addAll(chats.toChatArrayList());
                    chatArrayAdapter.notifyDataSetChanged();

                    editTextContent.setText(null);
                    listView.smoothScrollToPosition(chatArrayAdapter.getCount());
                }
            }
        }
    }
}
