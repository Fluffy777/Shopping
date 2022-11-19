package com.fluffy.items;

public class RegularItem extends Item {
    public RegularItem(String title, double price, int quantity) {
        super(title, price, quantity);
    }

    @Override
    protected int calculateLocalDiscount() {
        return 0;
    }
}
