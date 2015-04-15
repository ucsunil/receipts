package com.amex.receipts.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amex.receipts.R;

/**
 * Created by Sunil on 4/15/2015.
 */
public class ShowReceiptFragment extends DialogFragment implements View.OnClickListener{

    private static final String HEADER = "Receipt";

    private TextView results;
    private Button ok;

    private ShowReceiptFragment() {}

    public static ShowReceiptFragment getInstance(Bundle bundle) {
        ShowReceiptFragment fragment = new ShowReceiptFragment();
        Bundle args = bundle;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_receipt, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        results = (TextView) view.findViewById(R.id.receipt);
        ok = (Button) view.findViewById(R.id.ok);
        ok.setOnClickListener(this);

        String receipt = getArguments().getString("receipt");
        results.setText(receipt);
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
            case R.id.ok:
                this.dismiss();
                break;
        }

    }
}
