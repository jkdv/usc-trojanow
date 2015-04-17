package com.trojanow.network;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by heetae on 4/12/15.
 */
class JSONUtils {
    public static JSONObject convertToJSONObject(Bundle bundle) {
        JSONObject json = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            try {
                json.put(key, JSONObject.wrap(bundle.get(key)));
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    public static Bundle convertToBundle(JSONObject json) {
        Bundle bundle = new Bundle();
        Iterator<String> it = json.keys();
        while (it.hasNext()) {
            try {
                String key = it.next();
                String value = json.getString(key);
                bundle.putString(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bundle;
    }
}
