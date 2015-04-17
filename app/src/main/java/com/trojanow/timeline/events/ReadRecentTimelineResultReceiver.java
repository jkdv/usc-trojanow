package com.trojanow.timeline.events;

import android.os.Bundle;
import android.os.ResultReceiver;

import com.trojanow.timeline.entity.ThoughtArray;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by heetae on 4/12/15.
 * A callback class to be notified of a result of action related to this class.
 */
public class ReadRecentTimelineResultReceiver extends ResultReceiver {
    private static final int RESULT_ON_RESPONSE = 1;
    private ResultReceiver mReceiver;
    public ReadRecentTimelineResultReceiver(ResultReceiver receiver) {
        super(null);
        mReceiver = receiver;
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == RESULT_ON_RESPONSE &&
                resultData.getString(ThoughtArray.EXTRA_POSTS) != null) {
            try {
                JSONArray jsonArray =
                        new JSONArray(resultData.getString(ThoughtArray.EXTRA_POSTS));
                ThoughtArray thoughtArray = new ThoughtArray();
                thoughtArray.fromJSONArray(jsonArray);
                mReceiver.send(resultCode, thoughtArray.toBundle());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            mReceiver.send(resultCode, resultData);
        }
    }
}
