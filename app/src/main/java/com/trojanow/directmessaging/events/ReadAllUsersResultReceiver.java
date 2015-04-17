package com.trojanow.directmessaging.events;

import android.os.Bundle;
import android.os.ResultReceiver;

import com.trojanow.directmessaging.entity.UserArray;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by heetae on 4/12/15.
 * A callback class to be notified of a result of action related to this class.
 */
public class ReadAllUsersResultReceiver extends ResultReceiver {
    private static final int RESULT_ON_RESPONSE = 1;
    private ResultReceiver mReceiver;
    public ReadAllUsersResultReceiver(ResultReceiver receiver) {
        super(null);
        mReceiver = receiver;
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == RESULT_ON_RESPONSE &&
                resultData.getString(UserArray.EXTRA_USERS) != null) {
            try {
                JSONArray jsonArray =
                        new JSONArray(resultData.getString(UserArray.EXTRA_USERS));
                UserArray userArray = new UserArray();
                userArray.fromJSONArray(jsonArray);
                mReceiver.send(resultCode, userArray.toBundle());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            mReceiver.send(resultCode, resultData);
        }
    }
}
