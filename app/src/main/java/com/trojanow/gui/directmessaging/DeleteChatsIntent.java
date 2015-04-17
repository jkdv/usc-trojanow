package com.trojanow.gui.directmessaging;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/10/15.
 */
public class DeleteChatsIntent extends CallbackIntent {
    public DeleteChatsIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.directmessaging.services.action.DELETE_CHATS", receiver, sendingData);
    }
}
