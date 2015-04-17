package com.trojanow.account.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class UpdateAccountIntent extends CallbackIntent {
    public UpdateAccountIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.network.services.action.UPDATE_ACCOUNT", receiver, sendingData);
    }
}
