package com.fluffy.items;

public abstract class Item {
    protected final String title;
    protected final double price;
    protected final int quantity;

    protected Item(String title, double price, int quantity) {
        if (title == null || title.length() == 0 || title.length() > 32) {
            throw new IllegalArgumentException("Illegal title");
        }
        if (price < 0.01) {
            throw new IllegalArgumentException("Illegal price");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Illegal quantity");
        }

        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public int calculateDiscount() {
        return Math.min(calculateLocalDiscount() + quantity / 10, 80);
    }

    protected abstract int calculateLocalDiscount();

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
