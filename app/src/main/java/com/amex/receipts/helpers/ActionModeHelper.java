package com.amex.receipts.helpers;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amex.receipts.R;
import com.amex.receipts.ReceiptsActivity;

/**
 * Created by Sunil on 4/15/2015.
 */
public class ActionModeHelper implements ActionMode.Callback, AdapterView.OnItemLongClickListener {

    ReceiptsActivity host;
    ActionMode actionMode;
    ListView listView;

    public ActionModeHelper(ReceiptsActivity host, ListView view) {
        this.host = host;
        this.listView = view;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = host.getMenuInflater();

        inflater.inflate(R.menu.menu_receipts, menu);
        mode.setTitle(R.string.context_title);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        boolean result = host.performAction(item.getItemId(), listView.getCheckedItemPosition());

        if(item.getItemId() == R.id.cancel) {
            actionMode.finish();
        }
        return result;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        listView.clearChoices();
        listView.requestLayout();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        listView.clearChoices();
        listView.setItemChecked(position, true);

        if(actionMode == null) {
            actionMode = host.startActionMode(this);
        }
        return true;
    }
}
