package com.trojanow.directmessaging.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class ReadChatHistoryIntent extends CallbackIntent {
        public ReadChatHistoryIntent(ResultReceiver receiver, Bundle sendingData) {
            super("com.trojanow.network.services.action.READ_CHAT_HISTORY", receiver, sendingData);
        }
}
