package com.trojanow.directmessaging.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import com.trojanow.directmessaging.entity.Chat;
import com.trojanow.directmessaging.entity.User;
import com.trojanow.directmessaging.services.DirectMessageService;

public class DirectMessageEventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("DMEventReceiver", "onReceive");
        final String action  = intent.getAction();
        if (DirectMessageService.ACTION_READ_ALL_USERS.equals(action)) {
            startActionReadAllUsers(context, intent);
        } else if (DirectMessageService.ACTION_READ_CHAT_HISTORY.equals(action)) {
            startActionReadChatHistory(context, intent);
        } else if (DirectMessageService.ACTION_DELETE_CHATS.equals(action)) {
            startActionDeleteChats(context, intent);
        } else if (DirectMessageService.ACTION_SEND_CHAT.equals(action)) {
            startActionSendChat(context, intent);
        }
    }

    private void startActionReadAllUsers(Context context, Intent intent) {
        Log.d("DMEventReceiver", "startActionReadAllUsers");
        final String userId = intent.getStringExtra(User.EXTRA_USER_ID);
        final ResultReceiver receiver =
                intent.getParcelableExtra(DirectMessageService.EXTRA_RESULT_RECEIVER);
        DirectMessageService.startActionReadAllUsers(context, userId, receiver);
    }

    private void startActionReadChatHistory(Context context, Intent intent) {
        Log.d("DMEventReceiver", "startActionReadChatHistory");
        final String userIdFrom = intent.getStringExtra(Chat.EXTRA_USER_ID_FROM);
        final String userIdTo = intent.getStringExtra(Chat.EXTRA_USER_ID_TO);
        final ResultReceiver receiver =
                intent.getParcelableExtra(DirectMessageService.EXTRA_RESULT_RECEIVER);
        DirectMessageService.startActionReadChatHistory(context, userIdFrom, userIdTo, receiver);
    }

    private void startActionDeleteChats(Context context, Intent intent) {
        Log.d("DMEventReceiver", "startActionDeleteChats");
        final String userIdFrom = intent.getStringExtra(Chat.EXTRA_USER_ID_FROM);
        final String userIdTo = intent.getStringExtra(Chat.EXTRA_USER_ID_TO);
        final ResultReceiver receiver =
                intent.getParcelableExtra(DirectMessageService.EXTRA_RESULT_RECEIVER);
        DirectMessageService.startActionDeleteChats(context, userIdFrom, userIdTo, receiver);
    }

    private void startActionSendChat(Context context, Intent intent) {
        Log.d("DMEventReceiver", "startActionSendChat");
        final String userIdFrom = intent.getStringExtra(Chat.EXTRA_USER_ID_FROM);
        final String userIdTo = intent.getStringExtra(Chat.EXTRA_USER_ID_TO);
        final String content = intent.getStringExtra(Chat.EXTRA_CONTENT);
        final ResultReceiver receiver =
                intent.getParcelableExtra(DirectMessageService.EXTRA_RESULT_RECEIVER);
        DirectMessageService.startActionSendChat(context, userIdFrom, userIdTo, content, receiver);
    }
}
