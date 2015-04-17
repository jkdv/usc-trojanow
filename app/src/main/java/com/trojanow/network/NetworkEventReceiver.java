package com.trojanow.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import org.json.JSONObject;

public class NetworkEventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NetworkEventReceiver", "onReceive");
        final String action  = intent.getAction();
        if (SenderService.ACTION_SIGN_IN.equals(action) ||
                SenderService.ACTION_SIGN_UP.equals(action) ||
                SenderService.ACTION_SIGN_OUT.equals(action) ||
                SenderService.ACTION_READ_RECENT_TIMELINE.equals(action) ||
                SenderService.ACTION_READ_MY_THOUGHTS_TIMELINE.equals(action)||
                SenderService.ACTION_SHARE_THOUGHT.equals(action) ||
                SenderService.ACTION_UPDATE_THOUGHT.equals(action) ||
                SenderService.ACTION_DELETE_THOUGHT.equals(action) ||
                SenderService.ACTION_UPDATE_ACCOUNT.equals(action) ||
                SenderService.ACTION_DEACTIVATE_ACCOUNT.equals(action) ||
                SenderService.ACTION_READ_ALL_USERS.equals(action) ||
                SenderService.ACTION_READ_CHAT_HISTORY.equals(action) ||
                SenderService.ACTION_DELETE_CHATS.equals(action) ||
                SenderService.ACTION_SEND_CHAT.equals(action) ||
                SenderService.ACTION_READ_WEATHER.equals(action)) {
            startActionJsonRequest(context, intent);
        }
    }

    private void startActionJsonRequest(Context context, Intent intent) {
        Log.d("NetworkEventReceiver", "startActionJsonRequest");
        final String action  = intent.getAction();
        JSONObject jsonObject = JSONUtils.convertToJSONObject(intent.getExtras());
        ResultReceiver receiver = intent.getParcelableExtra(SenderService.EXTRA_RESULT_RECEIVER);
        HTTPSenderService.startActionJsonRequest(action, context, jsonObject, receiver);
    }
}
