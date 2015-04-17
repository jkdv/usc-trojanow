package com.trojanow.gui.account;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.trojanow.R;
import com.trojanow.gui.entity.Account;
import com.trojanow.gui.util.NoticeDialogFragment;
import com.trojanow.gui.util.PasswordDialogFragment;

import java.net.HttpURLConnection;

public class AccountSettingsActivity extends ActionBarActivity implements
        NoticeDialogFragment.OnClickListener,
        PasswordDialogFragment.OnConfirmedListener {
    private static int REQUEST_UPDATE_ACCOUNT = 1;
    private Button buttonUpdateAccount;
    private Button buttonDeactivateAccount;
    private Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

//        mAccount = new Account(getIntent().getExtras());
        buttonUpdateAccount = (Button) findViewById(R.id.buttonUpdateAccount);
        buttonDeactivateAccount = (Button) findViewById(R.id.buttonDeactivateAccount);

        int redLight = getResources().getColor(android.R.color.holo_red_light);
        buttonDeactivateAccount.getBackground()
                .setColorFilter(redLight, PorterDuff.Mode.MULTIPLY);

        buttonUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateAccountClick();
            }
        });
        buttonDeactivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeactivateAccountClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAccount = new Account();
        mAccount.putUserId(SignedInAccount.getInstance().getUserId());
        mAccount.putUsername(SignedInAccount.getInstance().getUsername());
        mAccount.putFirstName(SignedInAccount.getInstance().getFirstName());
        mAccount.putLastName(SignedInAccount.getInstance().getLastName());
        mAccount.putContact(SignedInAccount.getInstance().getContact());
        mAccount.putEmail(SignedInAccount.getInstance().getEmail());
    }

    private void onSignOutClick() {
        Intent intent = new SignOutIntent(new SignOutResultReceiver(), mAccount.toBundle());
        sendBroadcast(intent);
    }

    private void onUpdateAccountClick() {
        DialogFragment passwordDialog = new PasswordDialogFragment();
        passwordDialog.setArguments(mAccount.toBundle());
        passwordDialog.show(getFragmentManager(), "password");
    }

    private void onDeactivateAccountClick() {
        DialogFragment noticeDialog = new NoticeDialogFragment();
        mAccount.putMessage("Deactivate account?");
        noticeDialog.setArguments(mAccount.toBundle());
        noticeDialog.show(getFragmentManager(), "notice");
    }

    @Override
    public void onPositiveClick(DialogFragment dialog) {
        if (dialog instanceof NoticeDialogFragment) {
            // Deactivate account
            Log.d("ACCOUNT", "NoticeDialogFragment.onPositiveClick");
            Intent intent = new DeactivateAccountIntent(
                    new DeactivateResultReceiver(), mAccount.toBundle());
            sendBroadcast(intent);
        }
    }

    @Override
    public void onNegativeClick(DialogFragment dialog) { /* Do nothing */ }

    @Override
    public void OnConfirmed(Bundle resultData) {
        Log.d("ACCOUNT", "OnConfirmed");
        Intent intent = new Intent(this, UpdateAccountActivity.class);
        intent.putExtras(resultData);
        Log.d("ACCOUNT", resultData.getString("User_id"));
        startActivityForResult(intent, REQUEST_UPDATE_ACCOUNT);
    }

    @Override
    public void OnNotConfirmed(Bundle resultData) {
        Toast.makeText(getApplicationContext(), "Wrong password.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_UPDATE_ACCOUNT) {
            if (resultCode == HttpURLConnection.HTTP_OK) {
                finish();
            }
        }
    }

    /**
     *
     */
    private class SignOutResultReceiver extends ResultReceiver {
        public SignOutResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            Account account = new Account(resultData);
            if (account.getStatusAsInt() == HttpURLConnection.HTTP_OK) {
                SignedInAccount.removeInstance();
                Toast.makeText(
                        getApplicationContext(),
                        account.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }

    private class DeactivateResultReceiver extends ResultReceiver {
        public DeactivateResultReceiver() {
            super(null);
        }
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            Account account = new Account(resultData);
            Toast.makeText(
                    getApplicationContext(), account.getMessage(), Toast.LENGTH_SHORT).show();
            if (account.getStatusAsInt() == HttpURLConnection.HTTP_OK) {
                SignedInAccount.removeInstance();
                Toast.makeText(
                        getApplicationContext(),
                        account.getMessage(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }
}
