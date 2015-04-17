package com.trojanow.timeline.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/12/15.
 */
public class ReadMyTimelineIntent extends CallbackIntent {
    public ReadMyTimelineIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.network.services.action.READ_MY_TIMELINE",
                receiver, sendingData);
    }
}
