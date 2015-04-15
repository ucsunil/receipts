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

import com.amex.receipts.adapters.ItemsAdapter;
import com.amex.receipts.fragments.AddItemFragment;
import com.amex.receipts.models.Item;

import java.util.ArrayList;


public class ReceiptsActivity extends Activity implements View.OnClickListener, AddItemFragment.OnItemSave{

    private AddItemFragment addItem;
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
                //TO DO
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
            if(!clear.isEnabled()) {
                clear.setEnabled(true);
                calculate.setEnabled(true);
            }
        }
    }

    public class DeleteCartTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... unused) {
            items.clear();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            adapter.notifyDataSetChanged();
            if(clear.isEnabled()) {
                clear.setEnabled(false);
                calculate.setEnabled(false);
            }
        }
    }
}
