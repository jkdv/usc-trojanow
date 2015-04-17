package com.trojanow.gui.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trojanow.R;
import com.trojanow.gui.entity.Account;
import com.trojanow.gui.timeline.AllShownTimelineActivity;

import java.net.HttpURLConnection;

public class SignInActivity extends ActionBarActivity {
    private static final int RESULT_ON_RESPONSE = 1;
    private static final String ERROR_USERNAME = "Username required";
    private static final String ERROR_PASSWORD = "Password required";

    private ProgressDialog progressDialog;
    private Button buttonSignIn;
    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSignIn(v);
            }
        });
    }

    public void actionSignIn(View v) {
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        editTextUsername.setError(null);
        editTextPassword.setError(null);

        boolean bExit = false;
        if (username.isEmpty()) {
            editTextUsername.setError(ERROR_USERNAME);
            bExit = true;
        }
        if (password.isEmpty()) {
            editTextPassword.setError(ERROR_PASSWORD);
            bExit = true;
        }
        if (bExit) {
            return;
        }

        Account account = new Account();
        account.putUsername(username);
        account.putPassword(password);

        Intent intent = new SignInIntent(new SignInResultReceiver(), account.toBundle());
        sendBroadcast(intent);

        progressDialog = ProgressDialog.show(this, "Sign In", null);
    }

    /**
     * A callback class to be notified of a result of action related to this class.
     */
    private class SignInResultReceiver extends ResultReceiver {
        public SignInResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            progressDialog.dismiss();

            if (resultCode == RESULT_ON_RESPONSE) {
                Account account = new Account(resultData);
                if (account.getStatusAsInt() == HttpURLConnection.HTTP_OK) {
                    SignedInAccount signedInAccount = SignedInAccount.getInstance();
                    signedInAccount.setUserId(account.getUserId());
                    signedInAccount.setUsername(account.getUsername());
                    signedInAccount.setFirstName(account.getFirstName());
                    signedInAccount.setLastName(account.getLastName());
                    signedInAccount.setContact(account.getContact());
                    signedInAccount.setEmail(account.getEmail());

                    String name = account.getFirstName();
                    Toast.makeText(
                            getApplicationContext(), "Welcome " + name, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignInActivity.this, AllShownTimelineActivity.class);
                    intent.putExtras(account.toBundle());
                    startActivity(intent);

                    setResult(account.getStatusAsInt());
                    finish();
                } else {
                    String msg = account.getMessage();
                    Toast.makeText(SignInActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
