package com.trojanow.account.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import com.trojanow.account.entity.Account;
import com.trojanow.account.events.SignInIntent;
import com.trojanow.account.events.SignInResultReceiver;
import com.trojanow.account.events.SignOutIntent;
import com.trojanow.account.events.SignOutResultReceiver;
import com.trojanow.account.events.SignUpIntent;
import com.trojanow.account.events.SignUpResultReceiver;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class SigningService extends IntentService {
    public static final String ACTION_SIGN_IN = "com.trojanow.account.services.action.SIGN_IN";
    public static final String ACTION_SIGN_UP = "com.trojanow.account.services.action.SIGN_UP";
    public static final String ACTION_SIGN_OUT = "com.trojanow.account.services.action.SIGN_OUT";
    public static final String EXTRA_RESULT_RECEIVER = "android.os.ResultReceiver";

    /**
     * Starts this service to perform action sign up with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSignUp(Context context, String firstName, String lastName,
                                         String username, String password, String contact,
                                         String email, ResultReceiver receiver) {
        Log.d("SigningService", "startActionSignUp");
        Intent intent = new Intent(context, SigningService.class);
        intent.setAction(ACTION_SIGN_UP);
        intent.putExtra(Account.EXTRA_FIRST_NAME, firstName);
        intent.putExtra(Account.EXTRA_LAST_NAME, lastName);
        intent.putExtra(Account.EXTRA_USERNAME, username);
        intent.putExtra(Account.EXTRA_PASSWORD, password);
        intent.putExtra(Account.EXTRA_CONTACT, contact);
        intent.putExtra(Account.EXTRA_EMAIL, email);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action sign in with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSignIn(Context context, String username, String password,
                                         ResultReceiver receiver) {
        Log.d("SigningService", "startActionSignIn");
        Intent intent = new Intent(context, SigningService.class);
        intent.setAction(ACTION_SIGN_IN);
        intent.putExtra(Account.EXTRA_USERNAME, username);
        intent.putExtra(Account.EXTRA_PASSWORD, password);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action sign out with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSignOut(Context context, String userId,
                                          ResultReceiver receiver) {
        Log.d("SigningService", "startActionSignOut");
        Intent intent = new Intent(context, SigningService.class);
        intent.setAction(ACTION_SIGN_OUT);
        intent.putExtra(Account.EXTRA_USER_ID, userId);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    public SigningService() {
        super("AccountService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SIGN_UP.equals(action)) {
                final String firstName = intent.getStringExtra(Account.EXTRA_FIRST_NAME);
                final String lastName = intent.getStringExtra(Account.EXTRA_LAST_NAME);
                final String username = intent.getStringExtra(Account.EXTRA_USERNAME);
                final String password = intent.getStringExtra(Account.EXTRA_PASSWORD);
                final String contact = intent.getStringExtra(Account.EXTRA_CONTACT);
                final String email = intent.getStringExtra(Account.EXTRA_EMAIL);
                final ResultReceiver receiver =
                        intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionSignUp(
                        firstName, lastName, username, password, contact, email, receiver);
            } else if (ACTION_SIGN_IN.equals(action)) {
                final String username = intent.getStringExtra(Account.EXTRA_USERNAME);
                final String password = intent.getStringExtra(Account.EXTRA_PASSWORD);
                final ResultReceiver receiver =
                        intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionSignIn(username, password, receiver);
            } else if (ACTION_SIGN_OUT.equals(action)) {
                final String userId = intent.getStringExtra(Account.EXTRA_USER_ID);
                final ResultReceiver receiver =
                        intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionSignOut(userId, receiver);
            }
        }
    }

    /**
     * Handle action sign up in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSignUp(String firstName, String lastName, String username,
                                    String password, String contact, String email,
                                    ResultReceiver receiver) {
        Log.d("SigningService", "handleActionSignUp");
        Account account = new Account();
        account.putFirstName(firstName);
        account.putLastName(lastName);
        account.putUsername(username);
        account.putEncryptPassword(password);
        account.putContact(contact);
        account.putEmail(email);
        Log.d("SigningService", "Encrypted password : " + account.getPassword());

        Intent intent = new SignUpIntent(new SignUpResultReceiver(receiver), account.toBundle());
        sendBroadcast(intent);
    }

    /**
     * Handle action sign in in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSignIn(String username, String password, ResultReceiver receiver) {
        Log.d("SigningService", "handleActionSignIn");
        Account account = new Account();
        account.putUsername(username);
        account.putEncryptPassword(password);

        Intent intent = new SignInIntent(new SignInResultReceiver(receiver), account.toBundle());
        sendBroadcast(intent);
    }

    /**
     * Handle action sign out in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSignOut(String userId, ResultReceiver receiver) {
        Log.d("SigningService", "handleActionSignOut");
        Account account = new Account();
        account.putUserId(userId);

        Intent intent = new SignOutIntent(new SignOutResultReceiver(receiver), account.toBundle());
        sendBroadcast(intent);
    }
}
