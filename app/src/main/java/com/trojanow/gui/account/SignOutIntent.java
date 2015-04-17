package com.trojanow.gui.account;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class SignOutIntent extends CallbackIntent {
    public SignOutIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.account.services.action.SIGN_OUT", receiver, sendingData);
    }
}
