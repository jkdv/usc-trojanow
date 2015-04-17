package com.trojanow.gui.account;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class SignUpIntent extends CallbackIntent {
    public SignUpIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.account.services.action.SIGN_UP", receiver, sendingData);
    }
}
