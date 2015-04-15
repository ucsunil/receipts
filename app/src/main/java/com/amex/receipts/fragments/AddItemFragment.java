package com.amex.receipts.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.amex.receipts.R;

/**
 * Created by Sunil on 4/14/2015.
 */
public class AddItemFragment extends DialogFragment implements View.OnClickListener {

    public static final int ITEM_ADDED = 1;

    private static final String HEADER = "Add New Item";

    private AddItemFragment() {};

    public static AddItemFragment getInstance() {
        AddItemFragment fragment = new AddItemFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item_dialog, null);
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(HEADER);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                // TO DO
                break;
            case R.id.cancel:
                this.dismiss();
                break;
        }

    }

    private void sendDataToActivity(Intent intent, int code) {

    }
}
