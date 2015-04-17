package com.trojanow.sharing.events;

import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/10/15.
 */
abstract class CallbackIntent extends Intent {
    public CallbackIntent(String action, ResultReceiver receiver, Bundle sendingData) {
        super(action);
        putExtras(sendingData);
        putExtra("android.os.ResultReceiver", receiver);
    }
}
