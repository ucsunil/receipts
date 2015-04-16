package com.amex.receipts;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.amex.receipts.adapters.ItemsAdapter;
import com.amex.receipts.fragments.AddItemFragment;
import com.amex.receipts.fragments.ShowReceiptFragment;
import com.amex.receipts.models.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class ReceiptsActivity extends Activity implements View.OnClickListener, AddItemFragment.OnItemSave{

    private AddItemFragment addItem;
    private ShowReceiptFragment showReceipt;
    private Button add, clear, calculate;
    private ListView listView;

    private ArrayList<Item> items;
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        add = (Button) findViewById(R.id.add);
        clear = (Button) findViewById(R.id.clear);
        calculate = (Button) findViewById(R.id.calculate);
        add.setOnClickListener(this);
        clear.setOnClickListener(this);
        calculate.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.list);
        items = new ArrayList<Item>();
        adapter = new ItemsAdapter(this, items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(items.size() == 0) {
            clear.setEnabled(false);
            calculate.setEnabled(false);
        } else {
            clear.setEnabled(true);
            calculate.setEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receipts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add:
                addItem = AddItemFragment.getInstance();
                addItem.show(getFragmentManager(), null);
                break;
            case R.id.clear:
                deleteCart();
                break;
            case R.id.calculate:
                String receipt = calculateCart();
                Bundle bundle = new Bundle();
                bundle.putString("receipt", receipt);

                showReceipt = ShowReceiptFragment.getInstance(bundle);
                showReceipt.show(getFragmentManager(), null);
                break;
        }
    }

    @Override
    public void onItemSaved(Item item) {
        new AddItemTask().execute(item);
    }

    private void deleteCart() {
        new DeleteCartTask().execute();
    }

    /**
     * This method does the actual calculation of the cost and taxes of the items in
     * the shopping cart and returns a String in the format required for this
     * excercise.
     *
     * @return The result string object formatted for the manner to be displayed as
     * requested in this excercise
     */
    private String calculateCart() {
        double totalPrice = 0;
        double totalTax = 0;

        // To ensure the results display in the manner expected
        DecimalFormat dec = new DecimalFormat("#.00");

        // Set the import tax
        double importTax = 0.05;
        double salesTax = 0.1;
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);

            double itemSalesTax = 0;
            double itemImportTax = 0;
            double itemTax = 0;

            String name = item.getItem();
            int quantity = item.getQuantity();
            double cost = item.getCost();
            boolean exempt = item.isExempt();
            boolean imported = item.isImported();

            if(!exempt) {
                itemSalesTax = salesTax*quantity*cost;
            }
            if(imported) {
                itemImportTax = importTax*quantity*cost;
            }
            double actualItemTax = itemSalesTax + itemImportTax;
            itemTax = roundUpToNearestFiveCents(actualItemTax);
            double actualItemPrice = (quantity*cost) + itemTax;
            double roundedItemPrice = roundPriceToTwoPlaces(actualItemPrice);

            totalTax = totalTax + itemTax;
            totalPrice = totalPrice + roundedItemPrice;


            builder.append(quantity).append(" ").append(imported? "imported" : "")
                    .append(" ").append(name).append(": ").append(dec.format(roundedItemPrice))
                    .append("\n");
        }

        // Ensure they are rounded to two decimal digits
        totalTax = new BigDecimal(String.valueOf(totalTax)).setScale(2, RoundingMode.HALF_UP).doubleValue();
        totalPrice = new BigDecimal(String.valueOf(totalPrice)).setScale(2, RoundingMode.HALF_UP).doubleValue();

        builder.append("Sales Taxes: ").append(dec.format(totalTax)).append("\n")
                .append("Total: ").append(dec.format(totalPrice));
        return builder.toString();
    }

    /**
     * This method is used to round the double passed in the parameter to two places
     * and return the result. Note that the rounding used here is HALF_EVEN which is
     * also commonly known as Banker's rounding.
     *
     * @param input The double decimal value to be rounded
     * @return The rounded double value
     */
    private double roundPriceToTwoPlaces(double input) {
        BigDecimal value = new BigDecimal(String.valueOf(input));
        return value.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * This method is used to round the input double value up to the nearest
     * 5 cent value. It does this by using BigDecimal values to retain
     * precision.
     *
     * @param input The value to be rounded up to the nearest 5 cent value
     * @return The nearest 5 cent value
     */
    private double roundUpToNearestFiveCents(double input) {
        BigDecimal value = new BigDecimal(String.valueOf(input));
        BigDecimal result = new BigDecimal(Math.ceil(value.doubleValue()*20)/20);
        return result.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * A simple AsyncTask to add elements in the background
     */
    public class AddItemTask extends AsyncTask<Item, Void, Void> {

        @Override
        protected Void doInBackground(Item... itemList) {
            Item item = itemList[0];
            items.add(item);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            adapter.notifyDataSetChanged();
            Toast.makeText(ReceiptsActivity.this, getString(R.string.item_added), Toast.LENGTH_SHORT).show();
            if(!clear.isEnabled()) {
                clear.setEnabled(true);
                calculate.setEnabled(true);
            }
        }
    }

    /**
     * A simple AsyncTask to delete the cart
     */
    public class DeleteCartTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... unused) {
            items.clear();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            adapter.notifyDataSetChanged();
            Toast.makeText(ReceiptsActivity.this, getString(R.string.cart_cleared), Toast.LENGTH_SHORT).show();
            if(clear.isEnabled()) {
                clear.setEnabled(false);
                calculate.setEnabled(false);
            }
        }
    }
}
