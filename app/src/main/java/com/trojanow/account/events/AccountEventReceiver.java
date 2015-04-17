package com.trojanow.account.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import com.trojanow.account.entity.Account;
import com.trojanow.account.services.AccountSettingService;
import com.trojanow.account.services.SigningService;

public class AccountEventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("AccountEventReceiver", "onReceive");
        final String action  = intent.getAction();
        if (SigningService.ACTION_SIGN_UP.equals(action)) {
            startActionSignUp(context, intent);
        } else if (SigningService.ACTION_SIGN_IN.equals(action)) {
            startActionSignIn(context, intent);
        } else if (SigningService.ACTION_SIGN_OUT.equals(action)) {
            startActionSignOut(context, intent);
        } else if (SigningService.ACTION_SIGN_OUT.equals(action)) {
            startActionSignOut(context, intent);
        } else if (AccountSettingService.ACTION_UPDATE_ACCOUNT.equals(action)) {
            startActionUpdateAccount(context, intent);
        } else if (AccountSettingService.ACTION_DEACTIVATE_ACCOUNT.equals(action)) {
            startActionDeactivateAccount(context, intent);
        }
    }

    private void startActionSignUp(Context context, Intent intent) {
        Log.d("AccountEventReceiver", "startActionSignUp");
        final String firstName = intent.getStringExtra(Account.EXTRA_FIRST_NAME);
        final String lastName = intent.getStringExtra(Account.EXTRA_LAST_NAME);
        final String username = intent.getStringExtra(Account.EXTRA_USERNAME);
        final String password = intent.getStringExtra(Account.EXTRA_PASSWORD);
        final String contact = intent.getStringExtra(Account.EXTRA_CONTACT);
        final String email = intent.getStringExtra(Account.EXTRA_EMAIL);
        final ResultReceiver receiver = intent.getParcelableExtra(
                SigningService.EXTRA_RESULT_RECEIVER);
        SigningService.startActionSignUp(
                context, firstName, lastName, username, password, contact, email, receiver);
    }

    private void startActionSignIn(Context context, Intent intent) {
        Log.d("AccountEventReceiver", "startActionSignIn");
        final String username = intent.getStringExtra(Account.EXTRA_USERNAME);
        final String password = intent.getStringExtra(Account.EXTRA_PASSWORD);
        final ResultReceiver receiver = intent.getParcelableExtra(
                SigningService.EXTRA_RESULT_RECEIVER);
        SigningService.startActionSignIn(context, username, password, receiver);
    }

    private void startActionSignOut(Context context, Intent intent) {
        Log.d("AccountEventReceiver", "startActionSignOut");
        final String userId = intent.getStringExtra(Account.EXTRA_USER_ID);
        final ResultReceiver receiver = intent.getParcelableExtra(
                SigningService.EXTRA_RESULT_RECEIVER);
        SigningService.startActionSignOut(context, userId, receiver);
    }

    private void startActionUpdateAccount(Context context, Intent intent) {
        Log.d("AccountEventReceiver", "startActionUpdateAccount");
        final String userId = intent.getStringExtra(Account.EXTRA_USER_ID);
        final String username = intent.getStringExtra(Account.EXTRA_USERNAME);
        final String firstName = intent.getStringExtra(Account.EXTRA_FIRST_NAME);
        final String lastName = intent.getStringExtra(Account.EXTRA_LAST_NAME);
        final String password = intent.getStringExtra(Account.EXTRA_PASSWORD);
        final String email = intent.getStringExtra(Account.EXTRA_EMAIL);
        final String contact = intent.getStringExtra(Account.EXTRA_CONTACT);
        final ResultReceiver receiver = intent.getParcelableExtra(
                AccountSettingService.EXTRA_RESULT_RECEIVER);
        AccountSettingService.startActionUpdateAccount(
                context, userId, username, firstName, lastName, password, contact, email, receiver);
    }

    private void startActionDeactivateAccount(Context context, Intent intent) {
        Log.d("AccountEventReceiver", "startActionDeactivateAccount");
        final String userId = intent.getStringExtra(Account.EXTRA_USER_ID);
        final ResultReceiver receiver = intent.getParcelableExtra(
                AccountSettingService.EXTRA_RESULT_RECEIVER);
        AccountSettingService.startActionDeactivateAccount(context, userId, receiver);
    }
}
