package com.trojanow.sharing.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/12/15.
 * A callback class to be notified of a result of action related to this class.
 */
public class UpdateThoughtResultReceiver extends ResultReceiver {
    private ResultReceiver mReceiver;
    public UpdateThoughtResultReceiver(ResultReceiver receiver) {
        super(null);
        mReceiver = receiver;
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        mReceiver.send(resultCode, resultData);
    }
}
