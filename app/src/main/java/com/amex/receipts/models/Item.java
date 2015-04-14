package com.amex.receipts.models;

/**
 * Created by Sunil on 4/14/2015.
 */
public class Item {

    private String item;
    private int quantity;
    private double cost;
    private boolean imported;

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    public boolean isImported() {
        return imported;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }
}
