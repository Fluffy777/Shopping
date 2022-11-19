package com.fluffy;

import com.fluffy.format.Separator;
import com.fluffy.format.TableBuilder;
import com.fluffy.format.TableColumn;
import com.fluffy.format.TextAlignment;
import com.fluffy.items.Item;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ShoppingCart {
    private static final Separator horizontalSeparator = new Separator('-');
    private static final Separator verticalSeparator = new Separator(' ');
    private static final int TITLE_MAX_LENGTH = 20;
    private static final String ELLIPSIS = "...";
    private static final NumberFormat moneyFormat;
    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        moneyFormat = new DecimalFormat("$#.00", symbols);
    }
    private final List<Item> items;

    public ShoppingCart() {
        items = new LinkedList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String formatTicket() {
        if (items.size() == 0) {
            return "No items.";
        }

        BiFunction<Integer, Integer, Integer> numberAggregationFunction = (value, aggregatedValue) -> (aggregatedValue == null) ? value : Math.max(aggregatedValue, value);
        BiFunction<String, String, String> moneySumAggregationFunction = (value, aggregatedValue) -> (aggregatedValue == null) ? value : moneyFormat.format(Double.parseDouble(value.replace("$", "")) + Double.parseDouble(aggregatedValue.replace("$", "")));

        Function<Integer, String> integerFormatter = Object::toString;
        Function<String, String> titleFormatter = title -> {
            if (title.length() <= TITLE_MAX_LENGTH) {
                return title;
            }
            return title.substring(0, TITLE_MAX_LENGTH - ELLIPSIS.length()) + ELLIPSIS;
        };
        Function<String, String> stringFormatter = s -> s;

        return new TableBuilder<Item>()
                .append(new TableColumn<Item, Integer>("#", TextAlignment.RIGHT, item -> items.indexOf(item) + 1, numberAggregationFunction, integerFormatter))
                .append(new TableColumn<Item, String>("Item", TextAlignment.LEFT, Item::getTitle, null, titleFormatter))
                .append(new TableColumn<Item, String>("Price", TextAlignment.RIGHT, item -> moneyFormat.format(item.getPrice()), null, stringFormatter))
                .append(new TableColumn<Item, Integer>("Quan.", TextAlignment.RIGHT, Item::getQuantity, null, integerFormatter))
                .append(new TableColumn<Item, String>("Discount", TextAlignment.RIGHT, item -> { int discount = item.calculateDiscount(); return discount == 0 ? "-" : discount + "%"; }, null, stringFormatter))
                .append(new TableColumn<Item, String>("Total", TextAlignment.RIGHT, item -> moneyFormat.format(item.getPrice() * item.getQuantity() * (100 - item.calculateDiscount()) / 100), moneySumAggregationFunction, stringFormatter))
                .setHorizontalSeparator(horizontalSeparator)
                .setVerticalSeparator(verticalSeparator)
                .setObjects(items)
                .build()
                .toString();
    }

    @Override
    public String toString() {
        return formatTicket();
    }
}
