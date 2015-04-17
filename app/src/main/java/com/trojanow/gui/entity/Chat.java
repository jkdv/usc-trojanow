package com.trojanow.gui.entity;

import android.os.Bundle;

/**
 * Created by heetae on 4/14/15.
 */
public class Chat {
    public static final String EXTRA_CHAT_ID = "Chat_id";
    public static final String EXTRA_USER_ID_FROM = "User_id_from";
    public static final String EXTRA_USER_ID_TO = "User_id_to";
    public static final String EXTRA_USER_FROM_FIRST_NAME = "User_from_Firstname";
    public static final String EXTRA_USER_FROM_LAST_NAME = "User_from_Lastname";
    public static final String EXTRA_USER_TO_FIRST_NAME = "User_to_Firstname";
    public static final String EXTRA_USER_TO_LAST_NAME = "User_to_Lastname";
    public static final String EXTRA_SENT_BY_ME = "Sent_by_me";
    public static final String EXTRA_CONTENT = "Content";
    public static final String EXTRA_CREATED_DATE = "Created_Date";
    public static final String EXTRA_TIME_ELAPSED = "Time_Elapsed";
    public static final String EXTRA_OPERATION = "Operation";
    public static final String EXTRA_MESSAGE = "Message";
    private static final String EXTRA_STATUS = "Status";

    private static final String VALUE_TRUE = "True";
    private static final String VALUE_FALSE = "False";
    private static final String VALUE_FETCH = "Fetch";
    private static final String VALUE_DELETE = "Delete";

    private Bundle mBundle;

    public Chat() {
        mBundle = new Bundle();
    }

    public Chat(final Bundle bundle) {
        if (bundle == null)
            mBundle = new Bundle();
        else
            mBundle = (Bundle) bundle.clone();
    }

    public void putChatId(String chatId) {
        mBundle.putString(EXTRA_CHAT_ID, chatId);
    }

    public String getChatId() {
        return mBundle.getString(EXTRA_CHAT_ID);
    }

    public void putUserIdFrom(String userIdFrom) {
        mBundle.putString(EXTRA_USER_ID_FROM, userIdFrom);
    }

    public String getUserIdFrom() {
        return mBundle.getString(EXTRA_USER_ID_FROM);
    }

    public void putUserIdTo(String userIdTo) {
        mBundle.putString(EXTRA_USER_ID_TO, userIdTo);
    }

    public String getUserFromFirstName() {
        return mBundle.getString(EXTRA_USER_FROM_FIRST_NAME);
    }

    public void putUserFromFirstName(String userFromFirstName) {
        mBundle.putString(EXTRA_USER_FROM_FIRST_NAME, userFromFirstName);
    }

    public String getUserFromLastName() {
        return mBundle.getString(EXTRA_USER_FROM_LAST_NAME);
    }

    public void putUserFromLastName(String userFromLastName) {
        mBundle.putString(EXTRA_USER_FROM_LAST_NAME, userFromLastName);
    }

    public String getUserToFirstName() {
        return mBundle.getString(EXTRA_USER_TO_FIRST_NAME);
    }

    public void putUserToFirstName(String userToFirstName) {
        mBundle.putString(EXTRA_USER_TO_FIRST_NAME, userToFirstName);
    }

    public String getUserToLastName() {
        return mBundle.getString(EXTRA_USER_TO_LAST_NAME);
    }

    public void putUserToLastName(String userToLastName) {
        mBundle.putString(EXTRA_USER_TO_LAST_NAME, userToLastName);
    }

    public String getUserIdTo() {
        return mBundle.getString(EXTRA_USER_ID_TO);
    }

    public void putContent(String content) {
        mBundle.putString(EXTRA_CONTENT, content);
    }

    public String getContent() {
        return mBundle.getString(EXTRA_CONTENT);
    }

    public void putCreatedDate(String createdDate) {
        mBundle.putString(EXTRA_CREATED_DATE, createdDate);
    }

    public String getCreatedDate() {
        return mBundle.getString(EXTRA_CREATED_DATE);
    }

    public void putTimeElapsed(String timeElapsed) {
        mBundle.putString(EXTRA_TIME_ELAPSED, timeElapsed);
    }

    public boolean getSentByMe() {
        return VALUE_TRUE.equals(mBundle.getString(EXTRA_SENT_BY_ME));
    }

    public void putSentByMe(boolean sentByMe) {
        if (sentByMe)
            mBundle.putString(EXTRA_SENT_BY_ME, VALUE_TRUE);
        else
            mBundle.putString(EXTRA_SENT_BY_ME, VALUE_TRUE);
    }

    public String getTimeElapsed() {
        return mBundle.getString(EXTRA_TIME_ELAPSED);
    }

    public Operation getOperation() {
        String op = mBundle.getString(EXTRA_OPERATION);
        if (VALUE_FETCH.equals(op)) {
            return Operation.FETCH;
        } else if (VALUE_DELETE.equals(op)) {
            return Operation.DELETE;
        } else {
            return null;
        }
    }

    public void putOperation(Operation op) {
        if (op == Operation.FETCH)
            mBundle.putString(EXTRA_OPERATION, VALUE_FETCH);
        else if (op == Operation.DELETE)
            mBundle.putString(EXTRA_OPERATION, VALUE_DELETE);
    }

    public String getMessage() {
        return mBundle.getString(EXTRA_MESSAGE);
    }

    public void putMessage(String msg) {
        mBundle.putString(EXTRA_MESSAGE, msg);
    }

    public String getStatus() {
        return mBundle.getString(EXTRA_STATUS);
    }

    public int getStatusAsInt() {
        return Integer.parseInt(mBundle.getString(EXTRA_STATUS));
    }

    public Bundle toBundle() {
        return mBundle;
    }

    public enum Operation {
        FETCH, DELETE
    }
}
