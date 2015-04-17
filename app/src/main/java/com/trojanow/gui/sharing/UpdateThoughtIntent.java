package com.trojanow.gui.sharing;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class UpdateThoughtIntent extends CallbackIntent {
    public UpdateThoughtIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.sharing.services.action.UPDATE_THOUGHT", receiver, sendingData);
    }
}
