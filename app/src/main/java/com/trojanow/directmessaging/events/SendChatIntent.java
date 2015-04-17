package com.trojanow.directmessaging.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class SendChatIntent extends CallbackIntent {
        public SendChatIntent(ResultReceiver receiver, Bundle sendingData) {
            super("com.trojanow.network.services.action.SEND_CHAT", receiver, sendingData);
        }
}
