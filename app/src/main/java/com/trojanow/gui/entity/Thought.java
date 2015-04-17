package com.trojanow.gui.entity;

import android.os.Bundle;

/**
 * Created by heetae on 4/12/15.
 */
public class Thought {
    public static final String EXTRA_USER_ID = "User_id";
    public static final String EXTRA_USERNAME = "Username";
    public static final String EXTRA_PASSWORD = "Password";
    public static final String EXTRA_STATUS = "Status";
    public static final String EXTRA_MESSAGE = "Message";
    public static final String EXTRA_ANONYMOUS = "Anonymous";
    public static final String EXTRA_FIRST_NAME = "Firstname";
    public static final String EXTRA_LAST_NAME = "Lastname";
    public static final String EXTRA_OPERATION = "Operation";
    public static final String EXTRA_POST_ID = "Post_id";
    public static final String EXTRA_CREATED_DATE = "Created_Date";
    public static final String EXTRA_CONTENT = "Content";
    public static final String EXTRA_POSTS = "posts";
    public static final String EXTRA_TIME_ELAPSED = "Time_Elapsed";

    public static final String VALUE_NEW = "New";
    public static final String VALUE_UPDATE = "Update";
    public static final String VALUE_DELETE = "Delete";
    public static final String VALUE_TRUE = "True";
    public static final String VALUE_FALSE = "False";

    private Bundle mBundle;

    public Thought() {
        mBundle = new Bundle();
    }

    public Thought(final Bundle bundle) {
        if (bundle == null)
            mBundle = new Bundle();
        else
            mBundle = new Bundle(bundle);
    }

    public Bundle toBundle() {
        return mBundle;
    }

    public void putUserId(String userId) {
        mBundle.putString(EXTRA_USER_ID, userId);
    }

    public String getUserId() {
        return mBundle.getString(EXTRA_USER_ID);
    }

    public String getPassword() {
        return mBundle.getString(EXTRA_PASSWORD);
    }

    public void putPassword(String password) {
        mBundle.putString(EXTRA_PASSWORD, password);
    }

    public String getStatus() {
        return mBundle.getString(EXTRA_STATUS);
    }

    public int getStatusAsInt() {
        return Integer.parseInt(mBundle.getString(EXTRA_STATUS));
    }

    public String getMessage() {
        return mBundle.getString(EXTRA_MESSAGE);
    }

    public void putMessage(String msg) {
        mBundle.putString(EXTRA_MESSAGE, msg);
    }

    public String getAnonymous() {
        return mBundle.getString(EXTRA_ANONYMOUS);
    }

    public boolean getAnonymous(boolean defaultValue) {
        String anonymous = mBundle.getString(EXTRA_ANONYMOUS);
        if (anonymous == null)
            return defaultValue;
        return VALUE_TRUE.equals(anonymous);
    }

    public void putAnonymous(boolean anonymous) {
        mBundle.putString(EXTRA_ANONYMOUS, anonymous ? VALUE_TRUE : VALUE_FALSE);
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

    public String getFullName() {
        StringBuffer sb = new StringBuffer();
        sb.append(getFirstName())
                .append(" ")
                .append(getLastName());
        return sb.toString();
    }

    public Operation getOperation() {
        final String operation = mBundle.getString(EXTRA_OPERATION);
        if (VALUE_NEW.equals(operation))
            return Operation.NEW;
        else if (VALUE_UPDATE.equals(operation))
            return Operation.UPDATE;
        else if (VALUE_DELETE.equals(operation))
            return Operation.DELETE;
        return null;
    }

    public void putOperation(Operation operation) {
        switch (operation) {
            case NEW:
                mBundle.putString(EXTRA_OPERATION, VALUE_NEW);
                break;
            case UPDATE:
                mBundle.putString(EXTRA_OPERATION, VALUE_UPDATE);
                break;
            case DELETE:
                mBundle.putString(EXTRA_OPERATION, VALUE_DELETE);
                break;
        }
    }

    public void putPostId(String postId) {
        mBundle.putString(EXTRA_POST_ID, postId);
    }

    public String getPostId() {
        return mBundle.getString(EXTRA_POST_ID);
    }

    public String getCreatedDate() {
        return mBundle.getString(EXTRA_CREATED_DATE);
    }

    public String getContent() {
        return mBundle.getString(EXTRA_CONTENT);
    }

    public void putContent(String content) {
        mBundle.putString(EXTRA_CONTENT, content);
    }

    public void putUsername(String username) {
        mBundle.putString(EXTRA_USERNAME, username);
    }

    public String getUsername() {
        return mBundle.getString(EXTRA_USERNAME);
    }

    public void putPosts(String posts) {
        mBundle.putString(EXTRA_POSTS, posts);
    }


    public void putTimeElapsed(String timeElapsed) {
        mBundle.putString(EXTRA_TIME_ELAPSED, timeElapsed);
    }

    public String getTimeElapsed() {
        return mBundle.getString(EXTRA_TIME_ELAPSED);
    }

    public enum Operation {
        NEW, UPDATE, DELETE;
    }
}
