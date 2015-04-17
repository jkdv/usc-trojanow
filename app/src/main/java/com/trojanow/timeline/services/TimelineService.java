package com.trojanow.timeline.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import com.trojanow.timeline.entity.Timeline;
import com.trojanow.timeline.events.ReadMyTimelineIntent;
import com.trojanow.timeline.events.ReadMyTimelineResultReceiver;
import com.trojanow.timeline.events.ReadRecentTimelineIntent;
import com.trojanow.timeline.events.ReadRecentTimelineResultReceiver;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class TimelineService extends IntentService {
    public static final String ACTION_READ_RECENT_TIMELINE = "com.trojanow.timeline.services.action.READ_RECENT_TIMELINE";
    public static final String ACTION_READ_MY_TIMELINE = "com.trojanow.timeline.services.action.READ_MY_TIMELINE";
    public static final String EXTRA_RESULT_RECEIVER = "android.os.ResultReceiver";

    /**
     * Starts this service to perform action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionReadRecentTimeline(Context context, ResultReceiver receiver) {
        Intent intent = new Intent(context, TimelineService.class);
        intent.setAction(ACTION_READ_RECENT_TIMELINE);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionReadMyTimeline(Context context, String userId, ResultReceiver receiver) {
        Intent intent = new Intent(context, TimelineService.class);
        intent.setAction(ACTION_READ_MY_TIMELINE);
        intent.putExtra(Timeline.EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    public TimelineService() {
        super("TimelineService");
    }

    /**
     * This method receives Intents (events) and invokes appropriate methods to handle them.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("TimelineService", "onHandleIntent");
        if (intent != null) {
            final String action  = intent.getAction();
            if (ACTION_READ_RECENT_TIMELINE.equals(action)) {
                final ResultReceiver receiver =
                        intent.getParcelableExtra(TimelineService.EXTRA_RESULT_RECEIVER);
                handleActionReadRecentTimeline(receiver);
            } else if (ACTION_READ_MY_TIMELINE.equals(action)) {
                final String userId = intent.getStringExtra(Timeline.EXTRA_USER_ID);
                final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionReadMyTimeline(userId, receiver);
            }
        }
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionReadRecentTimeline(ResultReceiver receiver) {
        Log.d("TimelineService", "handleActionReadRecentTimeline");
        Intent intent = new ReadRecentTimelineIntent(
                new ReadRecentTimelineResultReceiver(receiver));
        sendBroadcast(intent);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionReadMyTimeline(String userId, ResultReceiver receiver) {
        Log.d("TimelineService", "handleActionReadMyTimeline");
        Timeline timeline = new Timeline();
        timeline.putUserId(userId);

        Intent intent = new ReadMyTimelineIntent(
                new ReadMyTimelineResultReceiver(receiver), timeline.toBundle());
        sendBroadcast(intent);
    }
}
