package com.trojanow.sharing.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import com.trojanow.sharing.entity.Thought;
import com.trojanow.sharing.events.DeleteThoughtIntent;
import com.trojanow.sharing.events.DeleteThoughtResultReceiver;
import com.trojanow.sharing.events.ShareThoughtIntent;
import com.trojanow.sharing.events.ShareThoughtResultReceiver;
import com.trojanow.sharing.events.UpdateThoughtIntent;
import com.trojanow.sharing.events.UpdateThoughtResultReceiver;

/**
 * ThoughtService generates thought data that are used for the GUI.
 */
public class SharingService extends IntentService {
    public static final String ACTION_SHARE_THOUGHT = "com.trojanow.sharing.services.action.SHARE_THOUGHT";
    public static final String ACTION_UPDATE_THOUGHT = "com.trojanow.sharing.services.action.UPDATE_THOUGHT";
    public static final String ACTION_DELETE_THOUGHT = "com.trojanow.sharing.services.action.DELETE_THOUGHT";
    public static final String EXTRA_RESULT_RECEIVER = "android.os.ResultReceiver";

    /**
     * Starts this service to perform action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionShareThought(Context context, String userId, String content,
                                               String anonymous, ResultReceiver receiver) {
        Intent intent = new Intent(context, SharingService.class);
        intent.setAction(ACTION_SHARE_THOUGHT);
        intent.putExtra(Thought.EXTRA_USER_ID, userId);
        intent.putExtra(Thought.EXTRA_CONTENT, content);
        intent.putExtra(Thought.EXTRA_ANONYMOUS, anonymous);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateThought(Context context, String postId, String userId,
                                                String content, String anonymous,
                                                ResultReceiver receiver) {
        Intent intent = new Intent(context, SharingService.class);
        intent.setAction(ACTION_UPDATE_THOUGHT);
        intent.putExtra(Thought.EXTRA_POST_ID, postId);
        intent.putExtra(Thought.EXTRA_USER_ID, userId);
        intent.putExtra(Thought.EXTRA_CONTENT, content);
        intent.putExtra(Thought.EXTRA_ANONYMOUS, anonymous);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDeleteThought(Context context, String postId, String userId,
                                                ResultReceiver receiver) {
        Intent intent = new Intent(context, SharingService.class);
        intent.setAction(ACTION_DELETE_THOUGHT);
        intent.putExtra(Thought.EXTRA_POST_ID, postId);
        intent.putExtra(Thought.EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    public SharingService() {
        super("SharingService");
    }

    /**
     * This method receives Intents (events) and invokes appropriate methods to handle them.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("SharingService", "onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHARE_THOUGHT.equals(action)) {
                final String userId = intent.getStringExtra(Thought.EXTRA_USER_ID);
                final String content = intent.getStringExtra(Thought.EXTRA_CONTENT);
                final String anonymous = intent.getStringExtra(Thought.EXTRA_ANONYMOUS);
                final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionShareThought(userId, content, anonymous, receiver);
            } else if (ACTION_UPDATE_THOUGHT.equals(action)) {
                final String postId = intent.getStringExtra(Thought.EXTRA_POST_ID);
                final String userId = intent.getStringExtra(Thought.EXTRA_USER_ID);
                final String content = intent.getStringExtra(Thought.EXTRA_CONTENT);
                final String anonymous = intent.getStringExtra(Thought.EXTRA_ANONYMOUS);
                final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionUpdateThought(postId, userId, content, anonymous, receiver);
            } else if (ACTION_DELETE_THOUGHT.equals(action)) {
                final String postId = intent.getStringExtra(Thought.EXTRA_POST_ID);
                final String userId = intent.getStringExtra(Thought.EXTRA_USER_ID);
                final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionDeleteThought(postId, userId, receiver);
            }
        }
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionShareThought(String userId, String content,
                                          String anonymous, ResultReceiver receiver) {
        Thought thought = new Thought();
        thought.putUserId(userId);
        thought.putContent(content);
        thought.putAnonymous(anonymous);
        thought.putOperation(Thought.Operation.NEW);

        Intent intent = new ShareThoughtIntent(
                new ShareThoughtResultReceiver(receiver), thought.toBundle());
        sendBroadcast(intent);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateThought(String postId, String userId, String content,
                                           String anonymous, ResultReceiver receiver) {
        Thought thought = new Thought();
        thought.putPostId(postId);
        thought.putUserId(userId);
        thought.putContent(content);
        thought.putAnonymous(anonymous);
        thought.putOperation(Thought.Operation.UPDATE);

        Intent intent = new UpdateThoughtIntent(
                new UpdateThoughtResultReceiver(receiver), thought.toBundle());
        sendBroadcast(intent);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDeleteThought(String postId, String userId, ResultReceiver receiver) {
        Thought thought = new Thought();
        thought.putPostId(postId);
        thought.putUserId(userId);
        thought.putOperation(Thought.Operation.DELETE);

        Intent intent = new DeleteThoughtIntent(
                new DeleteThoughtResultReceiver(receiver), thought.toBundle());
        sendBroadcast(intent);
    }
}
