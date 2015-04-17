package com.trojanow.gui.directmessaging;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/10/15.
 */
public class SendChatIntent extends CallbackIntent {
    public SendChatIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.directmessaging.services.action.SEND_CHAT", receiver, sendingData);
    }
}
