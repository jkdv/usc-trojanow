package com.trojanow.gui.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.trojanow.R;
import com.trojanow.gui.account.SignInIntent;
import com.trojanow.gui.entity.Account;

import java.net.HttpURLConnection;

/**
 * Created by heetae on 4/11/15.
 */
public class PasswordDialogFragment extends DialogFragment {
    // Use this instance of the interface to deliver action events
    private static int RESULT_ON_RESPONSE = 1;
    private OnConfirmedListener mListener;
    private Context mContext;
    private Bundle mBundle;
    private EditText editTextPassword;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_input, null);

        mBundle = getArguments();
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);

        builder.setTitle("Confirm password")
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onPositiveClick();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Override the Fragment.onAttach() method to instantiate the OnClickListener
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (OnConfirmedListener) activity;
            mContext = activity.getApplicationContext();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement OnClickListener");
        }
    }

    private void onPositiveClick() {
        Account account = new Account(mBundle);
        account.putPassword(editTextPassword.getText().toString());
        Intent intent =
                new SignInIntent(new SignInResultReceiver(), account.toBundle());
        mContext.sendBroadcast(intent);
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
            if (resultCode == RESULT_ON_RESPONSE) {
                Log.d("PasswordDialog", resultData.toString());
                Account account = new Account(resultData);
                if (account.getStatusAsInt() == HttpURLConnection.HTTP_OK) {
                    mListener.OnConfirmed(resultData);
                } else {
                    mListener.OnNotConfirmed(resultData);
                }
            }
        }
    }

    public interface OnConfirmedListener {
        void OnConfirmed(Bundle resultData);
        void OnNotConfirmed(Bundle resultData);
    }
}
