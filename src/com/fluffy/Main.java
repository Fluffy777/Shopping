package com.fluffy;

import com.fluffy.items.Item;
import com.fluffy.items.NewItem;
import com.fluffy.items.RegularItem;
import com.fluffy.items.SaleItem;
import com.fluffy.items.SecondFreeItem;

public class Main {
    public static void main(String[] args) {
        ShoppingCart shoppingCart = new ShoppingCart();
        Item[] items = new Item[]{
                new NewItem("Apple", 0.99, 5),
                new SecondFreeItem("Banana", 20.00, 4),
                new SaleItem("A long piece of toilet paper", 17.20, 1),
                new RegularItem("Nails", 2.00, 500),
        };
        for (Item item : items) {
            shoppingCart.addItem(item);
        }
        System.out.println(shoppingCart.formatTicket());
    }
}
