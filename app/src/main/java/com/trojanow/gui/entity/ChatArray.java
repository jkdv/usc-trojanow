package com.trojanow.gui.entity;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by heetae on 4/14/15.
 */
public class ChatArray {
    public static final String EXTRA_CHATS = "chats";

    private ArrayList<Bundle> mList;

    public ChatArray() {
        mList = new ArrayList<>();
    }

    public ChatArray(final ArrayList<Bundle> bundles) {
        mList = new ArrayList<>();
        mList.addAll(bundles);
    }

    public void fromJSONArray(JSONArray jsonArray) {
        try {
            mList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                Bundle bundle = new Bundle();
                JSONObject json = jsonArray.getJSONObject(i);
                Iterator<String> it = json.keys();
                while (it.hasNext()) {
                    String key = it.next();
                    String value = json.getString(key);
                    bundle.putString(key, value);
                }
                mList.add(bundle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Chat> toChatArrayList() {
        ArrayList<Chat> users = new ArrayList<>();
        for (Bundle bundle : mList) {
            users.add(new Chat(bundle));
        }
        return users;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_CHATS, mList);
        return bundle;
    }
}
