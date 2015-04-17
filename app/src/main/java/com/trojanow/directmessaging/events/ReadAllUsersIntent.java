package com.trojanow.directmessaging.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class ReadAllUsersIntent extends CallbackIntent {
        public ReadAllUsersIntent(ResultReceiver receiver, Bundle sendingData) {
            super("com.trojanow.network.services.action.READ_ALL_USERS", receiver, sendingData);
        }
}
