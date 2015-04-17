package com.trojanow.gui.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by heetae on 4/10/15.
 */
public class NoticeDialogFragment extends DialogFragment {
    // Use this instance of the interface to deliver action events
    private OnClickListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = getArguments().getString("Message");
        builder.setTitle(title)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onPositiveClick(NoticeDialogFragment.this);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onNegativeClick(NoticeDialogFragment.this);
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
            listener = (OnClickListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement OnClickListener");
        }
    }

    /**
     * The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.
     */
    public interface OnClickListener {
        public void onPositiveClick(DialogFragment dialog);
        public void onNegativeClick(DialogFragment dialog);
    }
}