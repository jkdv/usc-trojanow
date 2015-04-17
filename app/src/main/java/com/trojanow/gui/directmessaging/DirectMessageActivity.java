package com.trojanow.gui.directmessaging;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.trojanow.R;
import com.trojanow.gui.entity.Chat;
import com.trojanow.gui.entity.User;
import com.trojanow.gui.entity.UserArray;
import com.trojanow.gui.entity.UserArrayAdapter;

import java.util.ArrayList;

public class DirectMessageActivity extends ActionBarActivity implements
        AdapterView.OnItemClickListener {
    private static final int RESULT_ON_RESPONSE = 1;

    private Chat mChat;
    private UserArrayAdapter userArrayAdapter;

    private ListView listView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_message);

        mChat = new Chat(getIntent().getExtras());

        userArrayAdapter =
                new UserArrayAdapter(this, R.layout.item_user, new ArrayList<User>());
        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(userArrayAdapter);
        listView.setOnItemClickListener(this);

        showUserList();
    }

    private void showUserList() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new ReadAllUsersIntent(new ReadAllUsersResultReceiver(), mChat.toBundle());
        sendBroadcast(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("DirectMessageActivity", "onItemClick");
        User userTo = (User) parent.getItemAtPosition(position);
        User userFrom = new User(mChat.toBundle());

        Chat chat = new Chat();
        chat.putUserIdFrom(userFrom.getUserId());
        chat.putUserIdTo(userTo.getUserId());
        chat.putUserToFirstName(userTo.getFirstName());
        chat.putUserToLastName(userTo.getLastName());

        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtras(chat.toBundle());
        startActivity(intent);
    }

    /**
     *
     */
    private class ReadAllUsersResultReceiver extends ResultReceiver {
        public ReadAllUsersResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            progressBar.setVisibility(View.INVISIBLE);

            if (resultCode == RESULT_ON_RESPONSE) {
                Log.d("ReadAllUsersResult", "onReceiveResult");
                ArrayList<Bundle> bundles =
                        resultData.getParcelableArrayList(UserArray.EXTRA_USERS);
                userArrayAdapter.clear();
                if (bundles != null) {
                    UserArray users = new UserArray(bundles);
                    userArrayAdapter.addAll(users.toUserArrayList());
                }
                userArrayAdapter.notifyDataSetChanged();
            }
        }
    }
}
