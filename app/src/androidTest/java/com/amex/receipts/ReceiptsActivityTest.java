package com.amex.receipts;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.LauncherActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.EditText;

import com.amex.receipts.fragments.AddItemFragment;

import junit.framework.Assert;

/**
 * Created by sunilsingh.monsingh on 4/16/2015.
 *
 * This class contains unit tests for the UI layout of the ReceiptsActivity and verifies that
 * the AddItemFragment shows up as a DialogFragment. Full code coverage in
 * unit tests, while important, is quite a lengthy process. I have used my discretion
 * while writing these unit tests and have used them more as a proof of knowledge and to be
 * complete in the coding exercise using a resaonable amount of time.
 */
public class ReceiptsActivityTest extends ActivityInstrumentationTestCase2<ReceiptsActivity> {

    private ReceiptsActivity receipts;

    private Button add, clear, calculate;

    private static final String wrongButtonText = " button contains wrong text!!";

    public ReceiptsActivityTest() {
        super(ReceiptsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        receipts = getActivity();
        add = (Button) receipts.findViewById(R.id.add);
        clear = (Button) receipts.findViewById(R.id.clear);
        calculate = (Button) receipts.findViewById(R.id.calculate);
    }

    public void testAddItemButtonPresent() {
        final String expected = receipts.getString(R.string.add);
        final String actual = add.getText().toString();
        Assert.assertEquals("Add Item"+wrongButtonText, expected, actual);
    }

    public void testClearButtonPresent() {
        final String expected = receipts.getString(R.string.clear);
        final String actual = clear.getText().toString();
        Assert.assertEquals("Add Item"+wrongButtonText, expected, actual);
    }

    public void testCalculateButtonPresent() {
        final String expected = receipts.getString(R.string.show);
        final String actual = calculate.getText().toString();
        Assert.assertEquals("Add Item"+wrongButtonText, expected, actual);
    }

    public void testClearNotEnabled() {
        Assert.assertFalse(clear.isEnabled());
    }

    public void testCalculateNotEnabled() {
        Assert.assertFalse(calculate.isEnabled());
    }

    /**
     * This test verifies that the AddItemFragment opens up as a DialogFragment
     */
    public void testAddItemFragmentShowsUpAsDialog() {
        TouchUtils.clickView(this, add);
        getInstrumentation().waitForIdleSync();
        Fragment dialog = getActivity().getFragmentManager().findFragmentByTag("addFragment");
        assertTrue(dialog instanceof DialogFragment);
    }

}
