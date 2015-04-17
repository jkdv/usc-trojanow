package com.trojanow.directmessaging.events;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class DeleteChatsIntent extends CallbackIntent {
        public DeleteChatsIntent(ResultReceiver receiver, Bundle sendingData) {
            super("com.trojanow.network.services.action.DELETE_CHATS", receiver, sendingData);
        }
}
