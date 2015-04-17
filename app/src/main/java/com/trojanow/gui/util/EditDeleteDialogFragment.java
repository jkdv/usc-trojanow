package com.trojanow.gui.util;

/**
 * Created by heetae on 4/10/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class EditDeleteDialogFragment extends DialogFragment {
    private ThoughtOptionDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] items = {"Edit", "Delete"};
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do you want to")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (items[which]) {
                            case "Edit":
                                mListener.onEditClick(EditDeleteDialogFragment.this);
                                break;
                            case "Delete":
                                mListener.onDeleteClick(EditDeleteDialogFragment.this);
                                break;
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ThoughtOptionDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ThoughtOptionDialogListener");
        }
    }

    public interface ThoughtOptionDialogListener {
        public void onEditClick(DialogFragment dialog);
        public void onDeleteClick(DialogFragment dialog);
    }
}
