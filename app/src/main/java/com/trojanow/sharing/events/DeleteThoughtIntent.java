package com.trojanow.sharing.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class DeleteThoughtIntent extends CallbackIntent {
    public DeleteThoughtIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.network.services.action.DELETE_THOUGHT", receiver, sendingData);
    }
}
