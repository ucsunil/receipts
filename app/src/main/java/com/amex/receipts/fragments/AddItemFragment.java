package com.amex.receipts.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amex.receipts.R;
import com.amex.receipts.models.Item;

/**
 * Created by Sunil on 4/14/2015.
 */
public class AddItemFragment extends DialogFragment implements View.OnClickListener {

    private static final String HEADER = "Add New Item";

    private EditText itemText, quantity, cost;
    private Spinner type, imported;
    private Button ok, cancel;

    private OnItemSaveListener itemSave;

    private AddItemFragment() {};

    public static AddItemFragment getInstance() {
        AddItemFragment fragment = new AddItemFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        itemSave = (OnItemSaveListener) activity;
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
        type = (Spinner) view.findViewById(R.id.type);
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
                if(TextUtils.isEmpty(itemText.getText().toString()) ||
                        TextUtils.isEmpty(quantity.getText().toString()) ||
                        TextUtils.isEmpty(cost.getText().toString())) {
                    Toast.makeText(getActivity(), "Please enter all fields before continuing", Toast.LENGTH_LONG).show();
                    return;
                }
                item.setItem(itemText.getText().toString());
                item.setQuantity(Integer.parseInt(quantity.getText().toString()));
                item.setCost(Double.parseDouble(cost.getText().toString()));
                boolean isExempt = type.getSelectedItem().toString().equals("Others") ?
                                        false : true;
                item.setExempt(isExempt);
                boolean isImport = imported.getSelectedItem().toString().equals("Yes") ?
                                        true : false;
                item.setImported(isImport);
                itemSave.onItemSaved(item);
                this.dismiss();
                break;
            case R.id.cancel:
                this.dismiss();
                break;
        }

    }

    /**
     * This is used as a listener to listen to when the user saves an item
     */
    public interface OnItemSaveListener {
        public void onItemSaved(Item item);
    }
}
