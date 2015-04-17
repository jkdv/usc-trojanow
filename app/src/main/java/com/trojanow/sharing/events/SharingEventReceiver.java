package com.trojanow.sharing.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import com.trojanow.sharing.entity.Thought;
import com.trojanow.sharing.services.SharingService;

/**
 * Created by heetae on 4/11/15.
 */
public class SharingEventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SharingEventReceiver", "onReceive");
        final String action  = intent.getAction();
        if (SharingService.ACTION_SHARE_THOUGHT.equals(action)) {
            startActionShareThought(context, intent);
        } else if (SharingService.ACTION_UPDATE_THOUGHT.equals(action)) {
            startActionUpdateThought(context, intent);
        } else if (SharingService.ACTION_DELETE_THOUGHT.equals(action)) {
            startActionDeleteThought(context, intent);
        }
    }

    private void startActionShareThought(Context context, Intent intent) {
        Log.d("SharingEventReceiver", "startActionShareThought");
        final String userId = intent.getStringExtra(Thought.EXTRA_USER_ID);
        final String content = intent.getStringExtra(Thought.EXTRA_CONTENT);
        final String anonymous = intent.getStringExtra(Thought.EXTRA_ANONYMOUS);
        final ResultReceiver receiver = intent.getParcelableExtra(
                SharingService.EXTRA_RESULT_RECEIVER);
        SharingService.startActionShareThought(context, userId, content, anonymous, receiver);
    }

    private void startActionUpdateThought(Context context, Intent intent) {
        Log.d("SharingEventReceiver", "startActionUpdateThought");
        final String postId = intent.getStringExtra(Thought.EXTRA_POST_ID);
        final String userId = intent.getStringExtra(Thought.EXTRA_USER_ID);
        final String content = intent.getStringExtra(Thought.EXTRA_CONTENT);
        final String anonymous = intent.getStringExtra(Thought.EXTRA_ANONYMOUS);
        final ResultReceiver receiver = intent.getParcelableExtra(
                SharingService.EXTRA_RESULT_RECEIVER);
        SharingService.startActionUpdateThought(context, postId, userId, content, anonymous, receiver);
    }

    private void startActionDeleteThought(Context context, Intent intent) {
        Log.d("SharingEventReceiver", "startActionDeleteThought");
        final String postId = intent.getStringExtra(Thought.EXTRA_POST_ID);
        final String userId = intent.getStringExtra(Thought.EXTRA_USER_ID);
        final ResultReceiver receiver = intent.getParcelableExtra(
                SharingService.EXTRA_RESULT_RECEIVER);
        SharingService.startActionDeleteThought(context, postId, userId, receiver);
    }
}
