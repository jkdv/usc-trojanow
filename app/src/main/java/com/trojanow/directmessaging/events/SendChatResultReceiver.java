package com.trojanow.directmessaging.events;

import android.os.Bundle;
import android.os.ResultReceiver;

import com.trojanow.directmessaging.entity.ChatArray;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by heetae on 4/12/15.
 * A callback class to be notified of a result of action related to this class.
 */
public class SendChatResultReceiver extends ResultReceiver {
    private static final int RESULT_ON_RESPONSE = 1;
    private ResultReceiver mReceiver;
    public SendChatResultReceiver(ResultReceiver receiver) {
        super(null);
        mReceiver = receiver;
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == RESULT_ON_RESPONSE &&
                resultData.getString(ChatArray.EXTRA_CHATS)!= null) {
            try {
                JSONArray jsonArray =
                        new JSONArray(resultData.getString(ChatArray.EXTRA_CHATS));
                ChatArray chatArray = new ChatArray();
                chatArray.fromJSONArray(jsonArray);
                mReceiver.send(resultCode, chatArray.toBundle());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            mReceiver.send(resultCode, resultData);
        }
    }
}
