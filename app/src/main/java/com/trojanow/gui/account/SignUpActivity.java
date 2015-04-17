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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends ActionBarActivity {
    private static final int RESULT_ON_RESPONSE = 1;

    private ProgressDialog progressDialog;
    private Button buttonSignUp;
    private EditText editTextUsername;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPassword;
    private EditText editTextConfirm;
    private EditText editTextEmail;
    private EditText editTextContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstname);
        editTextLastName = (EditText) findViewById(R.id.editTextLastname);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirm = (EditText) findViewById(R.id.editTextConfirm);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextContact = (EditText) findViewById(R.id.editTextContact);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSignUp();
            }
        });
    }

    public void actionSignUp() {
        Account account = new Account(new Bundle());
        account.putUsername(editTextUsername.getText().toString().trim());
        account.putFirstName(editTextFirstName.getText().toString().trim());
        account.putLastName(editTextLastName.getText().toString().trim());
        account.putPassword(editTextPassword.getText().toString());
        account.putEmail(editTextEmail.getText().toString().trim());
        account.putContact(editTextContact.getText().toString().trim());

        String confirm = editTextConfirm.getText().toString();

        editTextUsername.setError(null);
        editTextFirstName.setError(null);
        editTextLastName.setError(null);
        editTextPassword.setError(null);
        editTextConfirm.setError(null);
        editTextEmail.setError(null);
        editTextContact.setError(null);

        Pattern emailPattern = Pattern.compile(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
        Pattern phonePattern = Pattern.compile(
                "^\\([1-9][0-9]{2}\\)\\s[0-9]{3}-[0-9]{4}$");

        boolean bExit = false;
        if (account.getUsername().isEmpty()) {
            editTextUsername.setError("Username is required.");
            bExit = true;
        }
        if (account.getFirstName().isEmpty()) {
            editTextFirstName.setError("First name is required.");
            bExit = true;
        }
        if (account.getLastName().isEmpty()) {
            editTextLastName.setError("Last name is required.");
            bExit = true;
        }
        if (account.getPassword().isEmpty()) {
            editTextPassword.setError("Password is required.");
            bExit = true;
        }
        if (confirm.isEmpty()) {
            editTextConfirm.setError("Confirm password.");
            bExit = true;
        } else if (!confirm.equals(account.getPassword())) {
            editTextConfirm.setError("Password does not match.");
            bExit = true;
        }
        if (account.getEmail().isEmpty()) {
            editTextEmail.setError("Email is required.");
            bExit = true;
        } else {
            Matcher m = emailPattern.matcher(account.getEmail());
            if (!m.matches()) {
                editTextEmail.setError("Format: abcde@abc.com");
                bExit = true;
            }
        }
        if (account.getContact().isEmpty()) {
            editTextContact.setError("Contact is required.");
            bExit = true;
        } else {
            Matcher m = phonePattern.matcher(account.getContact());
            if (!m.matches()) {
                editTextContact.setError("Format: (###) ###-####. The number cannot start with 0.");
                bExit = true;
            }
        }
        if (bExit) {
            return;
        }

        Intent intent = new SignUpIntent(new SignUpResultReceiver(), account.toBundle());
        sendBroadcast(intent);

        progressDialog = ProgressDialog.show(this, "Signing Up", null);
    }

    private class SignUpResultReceiver extends ResultReceiver {
        public SignUpResultReceiver() {
            super(null);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
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
                            getApplicationContext(),
                            "Welcome " + name, Toast.LENGTH_SHORT).show();

                    Intent intent =
                            new Intent(SignUpActivity.this, AllShownTimelineActivity.class);
                    intent.putExtras(resultData);
                    startActivity(intent);

                    setResult(200);
                    finish();
                } else {
                    String msg = account.getMessage();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
