package com.fluffy.tests;

import com.fluffy.format.TextAlignment;
import org.junit.Assert;
import org.junit.Test;

public class TextAlignmentTest {
    @Test
    public void apply_LeftAlignment_ReturnsLeftAlignedString() {
        Assert.assertEquals("Value       ", TextAlignment.LEFT.apply("Value", 12));
    }

    @Test
    public void apply_RightAlignment_ReturnsRightAlignedString() {
        Assert.assertEquals("       Value", TextAlignment.RIGHT.apply("Value", 12));
    }

    @Test
    public void apply_EquidistantCenterAlignment_ReturnsCenterAlignedString() {
        Assert.assertEquals("   Value   ", TextAlignment.CENTER.apply("Value", 11));
    }

    @Test
    public void apply_CenterAlignment_ReturnsCenterAlignedString() {
        Assert.assertEquals("   Value    ", TextAlignment.CENTER.apply("Value", 12));
    }

    @Test
    public void apply_TooLongString_ReturnsTrimmedString() {
        Assert.assertEquals("Too lo", TextAlignment.LEFT.apply("Too long string", 6));
    }

    @Test
    public void apply_EmptyString_ReturnsEmptyStringWithRequiredLength() {
        Assert.assertEquals("          ", TextAlignment.LEFT.apply("", 10));
    }
}
