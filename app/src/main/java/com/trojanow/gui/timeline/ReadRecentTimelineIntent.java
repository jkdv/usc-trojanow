package com.trojanow.gui.timeline;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 3/20/15.
 */
public class ReadRecentTimelineIntent extends CallbackIntent {

    public ReadRecentTimelineIntent(ResultReceiver receiver) {
        super("com.trojanow.timeline.services.action.READ_RECENT_TIMELINE", receiver, new Bundle());
    }

}
