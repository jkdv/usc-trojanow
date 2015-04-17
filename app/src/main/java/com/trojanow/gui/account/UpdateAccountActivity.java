package com.trojanow.gui.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trojanow.R;
import com.trojanow.gui.entity.Account;

import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateAccountActivity extends ActionBarActivity {
    private static final int RESULT_ON_RESPONSE = 1;
//    private Account mAccount;
    private ProgressDialog progressDialog;
    private Button buttonUpdateAccount;
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
        setContentView(R.layout.activity_update_account);
        Log.d("UpdateAccountActivity", "onCreate");

//        mAccount = new Account(getIntent().getExtras());
        Log.d("UpdateAccountActivity", "User_id : " +
                SignedInAccount.getInstance().getUserId());

        buttonUpdateAccount = (Button) findViewById(R.id.buttonUpdateAccount);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstname);
        editTextLastName = (EditText) findViewById(R.id.editTextLastname);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirm = (EditText) findViewById(R.id.editTextConfirm);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextContact = (EditText) findViewById(R.id.editTextContact);

        buttonUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionUpdateAccount();
            }
        });

        setInitData();
    }

    private void setInitData() {
        Account account = new Account(getIntent().getExtras());
        editTextUsername.setText(account.getUsername());
        editTextFirstName.setText(account.getFirstName());
        editTextLastName.setText(account.getLastName());
        editTextEmail.setText(account.getEmail());
        editTextContact.setText(account.getContact());
    }

    private void actionUpdateAccount() {
        Log.d("UpdateAccountActivity", "actionUpdateAccount");
        Account account = new Account(new Bundle());
        account.putUserId(SignedInAccount.getInstance().getUserId());

        account.putUsername(editTextUsername.getText().toString());
        account.putFirstName(editTextFirstName.getText().toString());
        account.putLastName(editTextLastName.getText().toString());
        account.putPassword(editTextPassword.getText().toString());
        account.putEmail(editTextEmail.getText().toString());
        account.putContact(editTextContact.getText().toString());

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

        Intent intent =
                new UpdateAccountIntent(new UpdateAccountResultReceiver(), account.toBundle());
        sendBroadcast(intent);

        progressDialog = ProgressDialog.show(this, "Updating Account", null);
    }

    private class UpdateAccountResultReceiver extends ResultReceiver {
        public UpdateAccountResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            progressDialog.dismiss();
            if (resultCode == RESULT_ON_RESPONSE) {
                Account account = new Account(resultData);
                Toast.makeText(
                        getApplicationContext(),
                        account.getMessage(), Toast.LENGTH_SHORT).show();

                if (account.getStatusAsInt() == HttpURLConnection.HTTP_OK) {
                    SignedInAccount signedInAccount = SignedInAccount.getInstance();
                    signedInAccount.setUsername(editTextUsername.getText().toString());
                    signedInAccount.setFirstName(editTextFirstName.getText().toString());
                    signedInAccount.setLastName(editTextLastName.getText().toString());
                    signedInAccount.setContact(editTextContact.getText().toString());
                    signedInAccount.setEmail(editTextEmail.getText().toString());

                    setResult(HttpURLConnection.HTTP_OK);
                    finish();
                }
            }
        }
    }
}
