package com.trojanow.account.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;

import com.trojanow.account.entity.Account;
import com.trojanow.account.events.DeactivateAccountIntent;
import com.trojanow.account.events.DeactivateAccountResultReceiver;
import com.trojanow.account.events.UpdateAccountIntent;
import com.trojanow.account.events.UpdateAccountResultReceiver;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class AccountSettingService extends IntentService {
    public static final String ACTION_DEACTIVATE_ACCOUNT = "com.trojanow.account.services.action.DEACTIVATE_ACCOUNT";
    public static final String ACTION_UPDATE_ACCOUNT = "com.trojanow.account.services.action.UPDATE_ACCOUNT";
    public static final String EXTRA_RESULT_RECEIVER = "android.os.ResultReceiver";

    /**
     * Starts this service to perform action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDeactivateAccount(Context context, String userId,
                                                    ResultReceiver receiver) {
        Intent intent = new Intent(context, AccountSettingService.class);
        intent.setAction(ACTION_DEACTIVATE_ACCOUNT);
        intent.putExtra(Account.EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateAccount(Context context, String userId, String username,
                                                String firstName, String lastName, String password,
                                                String contact, String email,
                                                ResultReceiver receiver) {
        Intent intent = new Intent(context, AccountSettingService.class);
        intent.setAction(ACTION_UPDATE_ACCOUNT);
        intent.putExtra(Account.EXTRA_USER_ID, userId);
        intent.putExtra(Account.EXTRA_USERNAME, username);
        intent.putExtra(Account.EXTRA_FIRST_NAME, firstName);
        intent.putExtra(Account.EXTRA_LAST_NAME, lastName);
        intent.putExtra(Account.EXTRA_PASSWORD, password);
        intent.putExtra(Account.EXTRA_EMAIL, email);
        intent.putExtra(Account.EXTRA_CONTACT, contact);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    public AccountSettingService() {
        super("AccountSettingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DEACTIVATE_ACCOUNT.equals(action)) {
                final String userId = intent.getStringExtra(Account.EXTRA_USER_ID);
                final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionDeactivateAccount(userId, receiver);
            } else if (ACTION_UPDATE_ACCOUNT.equals(action)) {
                final String userId = intent.getStringExtra(Account.EXTRA_USER_ID);
                final String username = intent.getStringExtra(Account.EXTRA_USERNAME);
                final String firstName = intent.getStringExtra(Account.EXTRA_FIRST_NAME);
                final String lastName = intent.getStringExtra(Account.EXTRA_LAST_NAME);
                final String password = intent.getStringExtra(Account.EXTRA_PASSWORD);
                final String email = intent.getStringExtra(Account.EXTRA_EMAIL);
                final String contact = intent.getStringExtra(Account.EXTRA_CONTACT);
                final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionUpdateAccount(
                        userId, username, firstName, lastName, password, contact, email, receiver);
            }
        }
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDeactivateAccount(String userId, ResultReceiver receiver) {
        Account account = new Account();
        account.putUserId(userId);
        account.putOperation(Account.Operation.DELETE);

        Intent intent = new DeactivateAccountIntent(
                new DeactivateAccountResultReceiver(receiver), account.toBundle());
        sendBroadcast(intent);
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateAccount(String userId, String username,
                                           String firstName, String lastName, String password,
                                           String contact, String email,
                                           ResultReceiver receiver) {
        Account account = new Account();
        account.putUserId(userId);
        account.putUsername(username);
        account.putFirstName(firstName);
        account.putLastName(lastName);
        account.putEncryptPassword(password);
        account.putEmail(email);
        account.putContact(contact);
        account.putOperation(Account.Operation.UPDATE);

        Intent intent = new UpdateAccountIntent(
                new UpdateAccountResultReceiver(receiver), account.toBundle());
        sendBroadcast(intent);
    }
}
