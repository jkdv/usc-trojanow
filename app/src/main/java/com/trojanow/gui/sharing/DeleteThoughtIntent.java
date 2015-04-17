package com.trojanow.gui.sharing;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class DeleteThoughtIntent extends CallbackIntent {
    public DeleteThoughtIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.sharing.services.action.DELETE_THOUGHT", receiver, sendingData);
    }
}
