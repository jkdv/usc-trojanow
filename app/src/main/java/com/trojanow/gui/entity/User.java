package com.trojanow.gui.entity;

import android.os.Bundle;

/**
 * Created by heetae on 4/14/15.
 */
public class User {
    public static final String EXTRA_USER_ID = "User_id";
    public static final String EXTRA_USERNAME = "Username";
    public static final String EXTRA_FIRST_NAME = "Firstname";
    public static final String EXTRA_LAST_NAME = "Lastname";
    public static final String EXTRA_CONTACT = "Contact";
    public static final String EXTRA_EMAIL = "Email";
    public static final String EXTRA_ONLINE = "Online";

    public static final String VALUE_ONLINE = "1";
    public static final String VALUE_OFFLINE = "0";

    private Bundle mBundle;

    public User() {
        mBundle = new Bundle();
    }

    public User(final Bundle bundle) {
        if (bundle == null)
            mBundle = new Bundle();
        else
            mBundle = (Bundle) bundle.clone();
    }

    public void putUserId(String userId) {
        mBundle.putString(EXTRA_USER_ID, userId);
    }

    public String getUserId() {
        return mBundle.getString(EXTRA_USER_ID);
    }
    public void putUsername(String username) {
        mBundle.putString(EXTRA_USERNAME, username);
    }

    public String getUsername() {
        return mBundle.getString(EXTRA_USERNAME);
    }

    public String getFirstName() {
        return mBundle.getString(EXTRA_FIRST_NAME);
    }

    public void putFirstName(String firstName) {
        mBundle.putString(EXTRA_FIRST_NAME, firstName);
    }

    public String getLastName() {
        return mBundle.getString(EXTRA_LAST_NAME);
    }

    public void putLastName(String lastName) {
        mBundle.putString(EXTRA_LAST_NAME, lastName);
    }

    public String getContact() {
        return mBundle.getString(EXTRA_CONTACT);
    }

    public void putContact(String contact) {
        mBundle.putString(EXTRA_CONTACT, contact);
    }

    public void putEmail(String email) {
        mBundle.putString(EXTRA_EMAIL, email);
    }

    public String getEmail() {
        return mBundle.getString(EXTRA_EMAIL);
    }

    public boolean getOnline() {
        final String operation = mBundle.getString(EXTRA_ONLINE);
        if (VALUE_ONLINE.equals(operation))
            return true;
        return false;
    }

    public void putOnline(Online online) {
        switch (online) {
            case ONLINE:
                mBundle.putString(EXTRA_ONLINE, VALUE_ONLINE);
                break;
            case OFFLINE:
                mBundle.putString(EXTRA_ONLINE, VALUE_OFFLINE);
                break;
        }
    }

    public Bundle toBundle() {
        return mBundle;
    }

    public enum Online {
        ONLINE, OFFLINE;
    }
}
