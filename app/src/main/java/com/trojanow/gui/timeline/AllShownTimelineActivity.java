package com.trojanow.gui.timeline;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.trojanow.R;
import com.trojanow.gui.account.AccountSettingsActivity;
import com.trojanow.gui.account.LoginActivity;
import com.trojanow.gui.account.SignOutIntent;
import com.trojanow.gui.account.SignedInAccount;
import com.trojanow.gui.directmessaging.DirectMessageActivity;
import com.trojanow.gui.entity.Account;
import com.trojanow.gui.entity.Thought;
import com.trojanow.gui.entity.ThoughtArray;
import com.trojanow.gui.entity.ThoughtArrayAdapter;
import com.trojanow.gui.sharing.DeleteThoughtIntent;
import com.trojanow.gui.sharing.SharingActivity;
import com.trojanow.gui.util.EditDeleteDialogFragment;
import com.trojanow.gui.util.NoticeDialogFragment;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * AllShownTimelineActivity is an Activity
 * to show a timelime that has all the recent thoughts.
 */
public class AllShownTimelineActivity extends ActionBarActivity implements
        AdapterView.OnItemLongClickListener,
        EditDeleteDialogFragment.ThoughtOptionDialogListener,
        NoticeDialogFragment.OnClickListener {
    private static final int RESULT_ON_RESPONSE = 1;
    private static final int REQUEST_UPDATE = 1;

    private Account mAccount;
    private ThoughtArrayAdapter thoughtArrayAdapter;

    private ListView listView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_shown_timeline);

//        mAccount = new Account(getIntent().getExtras());

        listView = (ListView) findViewById(android.R.id.list);
        thoughtArrayAdapter =
                new ThoughtArrayAdapter(this, R.layout.item_thought, new ArrayList<Thought>());
        listView.setAdapter(thoughtArrayAdapter);
        listView.setOnItemLongClickListener(this);

        showRecentThoughts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAccount = new Account();
        mAccount.putUserId(SignedInAccount.getInstance().getUserId());
        mAccount.putUsername(SignedInAccount.getInstance().getUsername());
        mAccount.putFirstName(SignedInAccount.getInstance().getFirstName());
        mAccount.putLastName(SignedInAccount.getInstance().getLastName());
        mAccount.putContact(SignedInAccount.getInstance().getContact());
        mAccount.putEmail(SignedInAccount.getInstance().getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_shown_timeline, menu);
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

        if (id == R.id.action_activity_sharing) {
            // Show sharing activity
            Intent intent = new Intent(this, SharingActivity.class);
            Thought thought = new Thought(mAccount.toBundle());
            thought.putOperation(Thought.Operation.NEW);
            intent.putExtras(thought.toBundle());
            startActivityForResult(intent, 0);
        } else if (id == R.id.action_activity_my_thought) {
            // Show my thoughts
            Intent intent = new Intent(this, FilteredTimelineActivity.class);
            Thought thought = new Thought(mAccount.toBundle());
            intent.putExtras(thought.toBundle());
            startActivity(intent);
        } else if (id == R.id.action_activity_account_settings) {
            // Show account settings
            Intent intent = new Intent(this, AccountSettingsActivity.class);
            Thought thought = new Thought(mAccount.toBundle());
            intent.putExtras(thought.toBundle());
            startActivity(intent);
        } else if (id == R.id.action_activity_direct_messaging) {
            // Show direct messaging
            Intent intent = new Intent(this, DirectMessageActivity.class);
            Thought thought = new Thought(mAccount.toBundle());
            intent.putExtras(thought.toBundle());
            startActivity(intent);
        } else if (id == R.id.action_sign_out) {
            // Sign out
            Thought thought = new Thought(mAccount.toBundle());
            Intent intent = new SignOutIntent(new SignOutResultReceiver(), thought.toBundle());
            sendBroadcast(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method grabs recent thoughts from the server and show them on a timeline.
     */
    private void showRecentThoughts() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Intent requestIntent = new ReadRecentTimelineIntent(new ReadRecentTimelineResultReceiver());
        sendBroadcast(requestIntent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Thought thought = (Thought) parent.getItemAtPosition(position);
        if (!mAccount.getUserId().equals(thought.getUserId()))
            return false;

        DialogFragment dialog = new EditDeleteDialogFragment();
        dialog.setArguments(thought.toBundle());
        dialog.show(getFragmentManager(), "thoughtOption");
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == HttpURLConnection.HTTP_OK) {
            showRecentThoughts();
        }
    }

    @Override
    public void onEditClick(DialogFragment dialog) {
        Thought thought = new Thought(dialog.getArguments());
        thought.putOperation(Thought.Operation.UPDATE);
        Intent intent = new Intent(this, SharingActivity.class);
        intent.putExtras(thought.toBundle());
        startActivityForResult(intent, REQUEST_UPDATE);
    }

    @Override
    public void onDeleteClick(DialogFragment dialog) {
        Thought thought = new Thought(dialog.getArguments());
        thought.putOperation(Thought.Operation.DELETE);

        DialogFragment noticeDialogFragment = new NoticeDialogFragment();
        thought.putMessage("Delete thought?");
        noticeDialogFragment.setArguments(thought.toBundle());
        noticeDialogFragment.show(getFragmentManager(), "notice");
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        Thought thought = new Thought(dialog.getArguments());
        Intent intent = new DeleteThoughtIntent(new DeleteThoughtResultReceiver(), thought.toBundle());
        sendBroadcast(intent);
    }

    @Override
    public void onNegativeClick(DialogFragment dialog) { /* Do nothing */ }

    /**
     *
     */
    private class SignOutResultReceiver extends ResultReceiver {
        public SignOutResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            Account thought = new Account(resultData);
            if (thought.getStatusAsInt() == HttpURLConnection.HTTP_OK) {
                Toast.makeText(
                        getApplicationContext(),
                        thought.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }

    /**
     *
     */
    private class ReadRecentTimelineResultReceiver extends ResultReceiver {
        public ReadRecentTimelineResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            progressBar.setVisibility(View.INVISIBLE);

            if (resultCode == RESULT_ON_RESPONSE) {
                Log.d("ReadAllUsersResult", "onReceiveResult");
                ArrayList<Bundle> bundles =
                        resultData.getParcelableArrayList(ThoughtArray.EXTRA_POSTS);
                thoughtArrayAdapter.clear();
                if (bundles != null) {
                    ThoughtArray posts = new ThoughtArray(bundles);
                    thoughtArrayAdapter.addAll(posts.toThoughtArrayList());
                }
                thoughtArrayAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     *
     */
    private class DeleteThoughtResultReceiver extends ResultReceiver {
        public DeleteThoughtResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == RESULT_ON_RESPONSE) {
                Log.d("DELETE_POST", "onReceiveResult");
                Thought thought = new Thought(resultData);
                Toast.makeText(
                        getApplicationContext(),
                        thought.getMessage(), Toast.LENGTH_SHORT).show();
                if (thought.getStatusAsInt() == HttpURLConnection.HTTP_OK) {
                    showRecentThoughts();
                }
            }
        }
    }
}
