package com.trojanow.timeline.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import com.trojanow.timeline.entity.Timeline;
import com.trojanow.timeline.services.TimelineService;

public class TimelineEventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TimelineEventReceiver", "onReceive");
        final String action  = intent.getAction();
        if (TimelineService.ACTION_READ_RECENT_TIMELINE.equals(action)) {
            startActionReadRecentTimeline(context, intent);
        } else if (TimelineService.ACTION_READ_MY_TIMELINE.equals(action)) {
            startActionReadMyTimeline(context, intent);
        }
    }

    private void startActionReadRecentTimeline(Context context, Intent intent) {
        Log.d("TimelineEventReceiver", "startActionReadRecentTimeline");
        final ResultReceiver receiver = intent.getParcelableExtra(
                TimelineService.EXTRA_RESULT_RECEIVER);
        TimelineService.startActionReadRecentTimeline(context, receiver);
    }

    private void startActionReadMyTimeline(Context context, Intent intent) {
        Log.d("TimelineEventReceiver", "startActionReadMyTimeline");
        final String userId = intent.getStringExtra(Timeline.EXTRA_USER_ID);
        final ResultReceiver receiver = intent.getParcelableExtra(
                TimelineService.EXTRA_RESULT_RECEIVER);
        TimelineService.startActionReadMyTimeline(context, userId, receiver);
    }
}
