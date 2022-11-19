package com.fluffy.tests;

import com.fluffy.items.Item;
import com.fluffy.items.NewItem;
import com.fluffy.items.RegularItem;
import com.fluffy.items.SaleItem;
import com.fluffy.items.SecondFreeItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class ShoppingCartTest {
    private final DiscountCase discountCase;

    private static class DiscountCase {
        private final Item item;
        private final int expectedDiscount;

        public DiscountCase(Item item, int expectedDiscount) {
            this.item = item;
            this.expectedDiscount = expectedDiscount;
        }
    }

    public ShoppingCartTest(DiscountCase discountCase) {
        this.discountCase = discountCase;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        double dummyPrice = 1;
        DiscountCase[] discountCases = new DiscountCase[] {
                new DiscountCase(new RegularItem("Title", dummyPrice, 5), 0),
                new DiscountCase(new NewItem("Title", dummyPrice, 7), 0),
                new DiscountCase(new SecondFreeItem("Title", dummyPrice, 1), 0),
                new DiscountCase(new SecondFreeItem("Title", dummyPrice, 2), 50),
                new DiscountCase(new SaleItem("Title", dummyPrice, 3), 70),

                new DiscountCase(new RegularItem("Title", dummyPrice, 120), 12),
                new DiscountCase(new NewItem("Title", dummyPrice, 54), 0),
                new DiscountCase(new SecondFreeItem("Title", dummyPrice, 31), 53),
                new DiscountCase(new SaleItem("Title", dummyPrice, 20), 72),

                new DiscountCase(new RegularItem("Title", dummyPrice, 1000), 80),
                new DiscountCase(new SecondFreeItem("Title", dummyPrice, 1000), 80),
                new DiscountCase(new SaleItem("Title", dummyPrice, 1000), 80),
        };

        return Stream.of(discountCases)
                .map(discountCase -> new Object[]{ discountCase }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Test
    public void calculateDiscount() {
        Assert.assertEquals(discountCase.expectedDiscount, discountCase.item.calculateDiscount());
    }
}
