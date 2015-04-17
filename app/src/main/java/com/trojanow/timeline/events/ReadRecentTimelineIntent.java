package com.trojanow.timeline.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/12/15.
 */
public class ReadRecentTimelineIntent extends CallbackIntent {
    public ReadRecentTimelineIntent(ResultReceiver receiver) {
        super("com.trojanow.network.services.action.READ_RECENT_TIMELINE",
                receiver, new Bundle());
    }
}
