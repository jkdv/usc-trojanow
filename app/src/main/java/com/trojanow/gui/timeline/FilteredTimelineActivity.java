package com.trojanow.gui.timeline;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.trojanow.R;
import com.trojanow.gui.account.SignedInAccount;
import com.trojanow.gui.entity.Account;
import com.trojanow.gui.entity.Thought;
import com.trojanow.gui.entity.ThoughtArray;
import com.trojanow.gui.entity.ThoughtArrayAdapter;
import com.trojanow.gui.sharing.SharingActivity;
import com.trojanow.gui.util.EditDeleteDialogFragment;
import com.trojanow.gui.util.NoticeDialogFragment;
import com.trojanow.gui.sharing.DeleteThoughtIntent;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public class FilteredTimelineActivity extends ActionBarActivity
        implements AdapterView.OnItemLongClickListener,
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
        setContentView(R.layout.activity_filtered_timeline);
        Log.d("FilteredTimeline", "onCreate");

        // Get data from parent Activity
//        mAccount = new Account(getIntent().getExtras());

        listView = (ListView) findViewById(android.R.id.list);
        thoughtArrayAdapter = new ThoughtArrayAdapter(
                this, R.layout.item_thought, new ArrayList<Thought>());
        listView.setAdapter(thoughtArrayAdapter);
        listView.setOnItemLongClickListener(this);
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

        // Change title name on Action Bar.
        setTitle(mAccount.getFullName());
        showMyThoughts();
    }

    /**
     * This method filters a timeline according to a text in the text box.
     */
    private void showMyThoughts() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Intent requestIntent = new ReadMyTimelineIntent(
                new ReadMyTimelineResultReceiver(), mAccount.toBundle());
        sendBroadcast(requestIntent);
    }

    private void updateDisplay() {
        thoughtArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Thought thought = (Thought) parent.getItemAtPosition(position);
        if (!mAccount.getUserId().equals(thought.getUserId()))
            return false;

        DialogFragment dialog = new EditDeleteDialogFragment();
        thought.putMessage("Delete thought?");
        dialog.setArguments(thought.toBundle());
        dialog.show(getFragmentManager(), "thoughtOption");
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_UPDATE) {
            if (resultCode == HttpURLConnection.HTTP_OK) {
                showMyThoughts();
            }
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
        noticeDialogFragment.setArguments(thought.toBundle());
        noticeDialogFragment.show(getFragmentManager(), "notice");
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        Thought thought = new Thought(dialog.getArguments());
        Intent intent = new DeleteThoughtIntent(
                new DeleteThoughtResultReceiver(), thought.toBundle());
        sendBroadcast(intent);
    }

    @Override
    public void onNegativeClick(DialogFragment dialog) { /* Do nothing */ }

    /**
     *
     */
    private class ReadMyTimelineResultReceiver extends ResultReceiver {
        public ReadMyTimelineResultReceiver() {
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
                    showMyThoughts();
                }
            }
        }
    }
}
