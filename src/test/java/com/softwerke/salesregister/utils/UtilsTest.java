package com.softwerke.salesregister.utils;

import com.softwerke.salesregister.tables.device.Color;
import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class UtilsTest {
    @Test(expected = IllegalArgumentException.class)
    public void testParseToEnumsException() {
        assertNull(Utils.parseToEnums("not_a_color", Color.class).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseToEnumsNull0() {
        Utils.parseToEnums("red", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseToEnumsNull1() {
        Utils.parseToEnums(null, Color.class);
    }

    @Test
    public void testConvertToEnumInstancesPass() {
        Color[] colors = new Color[]{Color.RED, Color.RED, Color.BLACK, Color.CHAMPAGNE};

        Stream<Color> colours = Utils.parseToEnums("red, ReD, BLACK, champagne", Color.class);

        assertArrayEquals(colors, colours.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void centerIndexOfTestNull() {
        Utils.centerIndexOf(null, (char) 0);
    }

    @Test
    public void centerIndexOfTest() {
        assertEquals(3, Utils.centerIndexOf("123%567", '%'));
        assertEquals(1, Utils.centerIndexOf("1 34567", ' '));
        assertEquals(6, Utils.centerIndexOf("123456S", 'S'));
        assertEquals(-1, Utils.centerIndexOf("1234567", '0'));
    }
}
