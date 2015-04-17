package com.trojanow.gui.account;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/10/15.
 */
public class DeactivateAccountIntent extends CallbackIntent {
    public DeactivateAccountIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.account.services.action.DEACTIVATE_ACCOUNT", receiver, sendingData);
    }
}
