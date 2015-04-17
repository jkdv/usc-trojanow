package com.trojanow.gui.account;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/10/15.
 */
public class UpdateAccountIntent extends CallbackIntent {
    public UpdateAccountIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.account.services.action.UPDATE_ACCOUNT", receiver, sendingData);
    }
}
