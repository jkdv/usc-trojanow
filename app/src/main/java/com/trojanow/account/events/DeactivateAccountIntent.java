package com.trojanow.account.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class DeactivateAccountIntent extends CallbackIntent {
    public DeactivateAccountIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.network.services.action.DEACTIVATE_ACCOUNT", receiver, sendingData);
    }
}
