package com.trojanow.gui.sharing;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class ShareThoughtIntent extends CallbackIntent {
    public ShareThoughtIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.sharing.services.action.SHARE_THOUGHT", receiver, sendingData);
    }
}
