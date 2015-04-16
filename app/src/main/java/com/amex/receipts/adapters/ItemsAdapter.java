package com.amex.receipts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amex.receipts.R;
import com.amex.receipts.models.Item;

import java.util.ArrayList;

/**
 * Created by Sunil on 4/14/2015.
 */

public class ItemsAdapter extends BaseAdapter {

    private ArrayList<Item> items;
    private Context context;

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_row, parent, false);

        Item item = items.get(position);
        TextView itemText = (TextView) row.findViewById(R.id.item);
        TextView quantity = (TextView) row.findViewById(R.id.qty);
        TextView cost = (TextView) row.findViewById(R.id.cost);
        TextView type = (TextView) row.findViewById(R.id.type);
        TextView imported = (TextView) row.findViewById(R.id.imported);

        itemText.setText(item.getItem());
        quantity.setText(String.valueOf(item.getQuantity()));
        cost.setText(String.valueOf(item.getCost()));
        type.setText(item.isExempt()? "Yes" : "No");
        imported.setText(item.isImported()? "Yes" : "No");

        return row;
    }
}
