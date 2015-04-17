package com.trojanow.gui.entity;

import android.os.Bundle;

/**
 * Created by heetae on 4/11/15.
 */
public class Account {
    public static final String EXTRA_FIRST_NAME = "Firstname";
    public static final String EXTRA_LAST_NAME = "Lastname";
    public static final String EXTRA_USERNAME = "Username";
    public static final String EXTRA_PASSWORD = "Password";
    public static final String EXTRA_CONTACT = "Contact";
    public static final String EXTRA_EMAIL = "Email";
    public static final String EXTRA_USER_ID = "User_id";
    public static final String EXTRA_STATUS = "Status";
    public static final String EXTRA_MESSAGE = "Message";
    public static final String EXTRA_OPERATION = "Operation";
    public static final String VALUE_NEW = "New";
    public static final String VALUE_UPDATE = "Update";
    public static final String VALUE_DELETE = "Delete";

    private Bundle mBundle;

    public Account() {
        mBundle = new Bundle();
    }

    public Account(final Bundle bundle) {
        if (bundle == null)
            mBundle = new Bundle();
        else
            mBundle = (Bundle) bundle.clone();
    }

    public Account(final Account account) {
        if (account == null)
            mBundle = new Bundle();
        else
            mBundle = (Bundle) account.toBundle().clone();
    }

    public void putFirstName(String firstName) {
        mBundle.putString(EXTRA_FIRST_NAME, firstName);
    }

    public String getFirstName() {
        return mBundle.getString(EXTRA_FIRST_NAME);
    }

    public void putLastName(String lastName) {
        mBundle.putString(EXTRA_LAST_NAME, lastName);
    }

    public String getLastName() {
        return mBundle.getString(EXTRA_LAST_NAME);
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public void putUsername(String username) {
        mBundle.putString(EXTRA_USERNAME, username);
    }

    public String getUsername() {
        return mBundle.getString(EXTRA_USERNAME);
    }

    public void putPassword(String password) {
        mBundle.putString(EXTRA_PASSWORD, password);
    }

    public String getPassword() {
        return mBundle.getString(EXTRA_PASSWORD);
    }

    public void putContact(String contact) {
        mBundle.putString(EXTRA_CONTACT, contact);
    }

    public String getContact() {
        return mBundle.getString(EXTRA_CONTACT);
    }

    public void putEmail(String email) {
        mBundle.putString(EXTRA_EMAIL, email);
    }

    public String getEmail() {
        return mBundle.getString(EXTRA_EMAIL);
    }

    public void putUserId(String userId) {
        mBundle.putString(EXTRA_USER_ID, userId);
    }

    public String getUserId() {
        return mBundle.getString(EXTRA_USER_ID);
    }

    public void putStatus(String status) {
        mBundle.putString(EXTRA_STATUS, status);
    }

    public String getStatus() {
        return mBundle.getString(EXTRA_STATUS);
    }

    public int getStatusAsInt() {
        return Integer.parseInt(mBundle.getString(EXTRA_STATUS));
    }

    public void putMessage(String msg) {
        mBundle.putString(EXTRA_MESSAGE, msg);
    }

    public String getMessage() {
        return mBundle.getString(EXTRA_MESSAGE);
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

    public Bundle toBundle() {
        return mBundle;
    }

    public enum Operation {
        NEW, UPDATE, DELETE;
    }
}
