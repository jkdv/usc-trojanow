package com.trojanow.gui.timeline;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 3/20/15.
 */
public class ReadMyTimelineIntent extends CallbackIntent {
    public ReadMyTimelineIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.timeline.services.action.READ_MY_TIMELINE", receiver, sendingData);
    }
}
