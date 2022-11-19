package com.fluffy.items;

public class SaleItem extends Item {
    public SaleItem(String title, double price, int quantity) {
        super(title, price, quantity);
    }

    @Override
    protected int calculateLocalDiscount() {
        return 70;
    }
}
