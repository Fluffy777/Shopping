package com.fluffy;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(Enclosed.class)
public class ShoppingCartTest {
    public static class Appending {
        @Test
        public void appendFormatted_LeftAlignment_AppendsLeftAlignedStringToStringBuilder() {
            StringBuilder stringBuilder = new StringBuilder();
            ShoppingCart.appendFormatted(stringBuilder, "Value", -1, 12);
            Assert.assertEquals("Value       ", stringBuilder.toString());
        }

        @Test
        public void appendFormatted_RightAlignment_AppendsRightAlignedStringToStringBuilder() {
            StringBuilder stringBuilder = new StringBuilder();
            ShoppingCart.appendFormatted(stringBuilder, "Value", 1, 12);
            Assert.assertEquals("       Value", stringBuilder.toString());
        }

        @Test
        public void appendFormatted_EquidistantCenterAlignment_AppendsCenterAlignedStringToStringBuilder() {
            StringBuilder stringBuilder = new StringBuilder();
            ShoppingCart.appendFormatted(stringBuilder, "Value", 0, 11);
            Assert.assertEquals("   Value   ", stringBuilder.toString());
        }

        @Test
        public void appendFormatted_CenterAlignment_AppendsCenterAlignedStringToStringBuilder() {
            StringBuilder stringBuilder = new StringBuilder();
            ShoppingCart.appendFormatted(stringBuilder, "Value", 0, 12);
            Assert.assertEquals("   Value    ", stringBuilder.toString());
        }

        @Test
        public void appendFormatted_TooLongString_AppendsTrimmedStringToStringBuilder() {
            StringBuilder stringBuilder = new StringBuilder();
            ShoppingCart.appendFormatted(stringBuilder, "Too long string", -1, 6);
            Assert.assertEquals("Too lo", stringBuilder.toString());
        }

        @Test
        public void appendFormatted_EmptyString_AppendsEmptyStringWithRequiredLengthToStringBuilder() {
            StringBuilder stringBuilder = new StringBuilder("Initial content");
            ShoppingCart.appendFormatted(stringBuilder, "", -1, 10);
            Assert.assertEquals("Initial content          ", stringBuilder.toString());
        }
    }

    @RunWith(Parameterized.class)
    public static class DiscountCalculation {
        private final DiscountCase discountCase;

        private static class DiscountCase {
            private final ShoppingCart.ItemType itemType;
            private final int quantity;
            private final int expectedDiscount;

            public DiscountCase(ShoppingCart.ItemType itemType, int quantity, int expectedDiscount) {
                this.itemType = itemType;
                this.quantity = quantity;
                this.expectedDiscount = expectedDiscount;
            }
        }

        public DiscountCalculation(DiscountCase discountCase) {
            this.discountCase = discountCase;
        }

        @Parameterized.Parameters
        public static Collection<Object[]> getParameters() {
            DiscountCase[] discountCases = new DiscountCase[] {
                    new DiscountCase(ShoppingCart.ItemType.REGULAR, 5, 0),
                    new DiscountCase(ShoppingCart.ItemType.NEW, 7, 0),
                    new DiscountCase(ShoppingCart.ItemType.SECOND_FREE, 1, 0),
                    new DiscountCase(ShoppingCart.ItemType.SECOND_FREE, 2, 50),
                    new DiscountCase(ShoppingCart.ItemType.SALE, 3, 70),

                    new DiscountCase(ShoppingCart.ItemType.REGULAR, 120, 12),
                    new DiscountCase(ShoppingCart.ItemType.NEW, 54, 0),
                    new DiscountCase(ShoppingCart.ItemType.SECOND_FREE, 31, 53),
                    new DiscountCase(ShoppingCart.ItemType.SALE, 20, 72),

                    new DiscountCase(ShoppingCart.ItemType.REGULAR, 1000, 80),
                    new DiscountCase(ShoppingCart.ItemType.SECOND_FREE, 1000, 80),
                    new DiscountCase(ShoppingCart.ItemType.SALE, 1000, 80),
            };

            return Stream.of(discountCases)
                    .map(discountCase -> new Object[]{ discountCase }).collect(Collectors.toCollection(ArrayList::new));
        }

        @Test
        public void calculateDiscount() {
            Assert.assertEquals(discountCase.expectedDiscount,
                    ShoppingCart.calculateDiscount(discountCase.itemType, discountCase.quantity));
        }
    }
}
