package com.fluffy.items;

public class SecondFreeItem extends Item {
    public SecondFreeItem(String title, double price, int quantity) {
        super(title, price, quantity);
    }

    @Override
    protected int calculateLocalDiscount() {
        return (quantity > 1) ? 50 : 0;
    }
}
