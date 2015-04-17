package com.trojanow.account.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class SignOutIntent extends CallbackIntent {
        public SignOutIntent(ResultReceiver receiver, Bundle sendingData) {
            super("com.trojanow.network.services.action.SIGN_OUT", receiver, sendingData);
        }
}
