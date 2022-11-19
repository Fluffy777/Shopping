package com.fluffy.items;

public class NewItem extends Item {
    public NewItem(String title, double price, int quantity) {
        super(title, price, quantity);
    }

    @Override
    public int calculateDiscount() {
        return 0;
    }

    @Override
    protected int calculateLocalDiscount() {
        return 0;
    }
}
