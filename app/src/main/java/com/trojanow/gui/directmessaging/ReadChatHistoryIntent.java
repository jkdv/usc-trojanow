package com.trojanow.gui.directmessaging;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/10/15.
 */
public class ReadChatHistoryIntent extends CallbackIntent {
    public ReadChatHistoryIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.directmessaging.services.action.READ_CHAT_HISTORY", receiver, sendingData);
    }
}
