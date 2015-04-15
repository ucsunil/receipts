package com.amex.receipts.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amex.receipts.R;
import com.amex.receipts.models.Item;

/**
 * Created by Sunil on 4/14/2015.
 */
public class AddItemFragment extends DialogFragment implements View.OnClickListener {

    private static final String HEADER = "Add New Item";

    private EditText itemText, quantity, cost;
    private Spinner imported;
    private Button ok, cancel;

    private OnItemSave itemSave;

    private AddItemFragment() {};

    public static AddItemFragment getInstance() {
        AddItemFragment fragment = new AddItemFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        itemSave = (OnItemSave) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item_dialog, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        itemText = (EditText) view.findViewById(R.id.item);
        quantity = (EditText) view.findViewById(R.id.quantity);
        cost = (EditText) view.findViewById(R.id.cost);
        imported = (Spinner) view.findViewById(R.id.imported);

        ok = (Button) view.findViewById(R.id.ok);
        cancel = (Button) view.findViewById(R.id.cancel);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
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
                Item item = new Item();
                item.setItem(itemText.getText().toString());
                item.setQuantity(Integer.parseInt(quantity.getText().toString()));
                item.setCost(Double.parseDouble(cost.getText().toString()));
                item.setImported(Boolean.valueOf(imported.getSelectedItem().toString()));
                itemSave.onItemSaved(item);
                break;
            case R.id.cancel:
                this.dismiss();
                break;
        }

    }

    public interface OnItemSave {
        public void onItemSaved(Item item);
    }
}