package com.trojanow.gui.account;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class SignInIntent extends CallbackIntent {
    public SignInIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.account.services.action.SIGN_IN", receiver, sendingData);
    }
}
