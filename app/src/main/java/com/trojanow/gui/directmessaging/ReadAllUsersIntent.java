package com.trojanow.gui.directmessaging;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/10/15.
 */
public class ReadAllUsersIntent extends CallbackIntent {
    public ReadAllUsersIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.directmessaging.services.action.READ_ALL_USERS", receiver, sendingData);
    }
}
