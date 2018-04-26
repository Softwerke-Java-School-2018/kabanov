package com.softwerke.salesregister;

import com.softwerke.salesregister.tables.device.Color;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class UtilsTest {
    @Test
    public void testSplitInTwo() {
        String[] range;

        range = Utils.splitInTwo("Line", "", "");
        assertNotNull(range);
        assertEquals("Line", range[0]);
        assertEquals("Line", range[1]);

        range = Utils.splitInTwo("Line2   \tLine3", "", "");
        assertNotNull(range);
        assertEquals("Line2", range[0]);
        assertEquals("Line3", range[1]);

        range = Utils.splitInTwo("* \t  ", "1", "-1");
        assertNotNull(range);
        assertEquals("1", range[0]);
        assertEquals("-1", range[1]);

        range = Utils.splitInTwo("1 2 3", "1", "-1");
        assertNull(range);
    }

    @Test
    public void testSplitInTwoNulls() {
        String[] range;

        range = Utils.splitInTwo("*", null, null);
        assertNotNull(range);
        assertNull(range[0]);
        assertNull(range[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSplitInTwoException() {
        Utils.splitInTwo(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseToEnumsException() {
        Utils.parseToEnums("not_a_color", Color.class).toArray();
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

    @Test
    public void testIsBetween() {
        assertTrue(Utils.isBetween(0, 1, 2));
        assertTrue(Utils.isBetween(0.0, 1.0, 2.0));
        assertTrue(Utils.isBetween(new BigDecimal(0.0), new BigDecimal(1.0), new BigDecimal(2.0)));
        assertTrue(Utils.isBetween(LocalDate.MIN, LocalDate.now(), LocalDate.MAX));
        assertTrue(Utils.isBetween(LocalDate.MIN, LocalDate.MIN, LocalDate.MIN));

        assertFalse(Utils.isBetween(new BigDecimal(0.0), new BigDecimal(-0.01), new BigDecimal(2.0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsBetweenNull0() {
        assertFalse(Utils.isBetween(LocalDate.MIN, null, LocalDate.MAX));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsBetweenNull1() {
        assertTrue(Utils.isBetween(null, LocalDate.now(), LocalDate.MAX));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsBetweenNull2() {
        assertTrue(Utils.isBetween(LocalDate.MIN, LocalDate.now(), null));
    }

    @Test
    public void testConvertToLocalDate() {
        LocalDate[] dates = Utils.convertToLocalDate("20-04-1997");
        LocalDate[] dates2 = Utils.convertToLocalDate("20-04-1997", "20-04-1998");
        assertArrayEquals(dates, new LocalDate[]{LocalDate.parse("1997-04-20")});
        assertArrayEquals(dates2, new LocalDate[]{LocalDate.parse("1997-04-20"), LocalDate.parse("1998-04-20")});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToLocalDateNull0() {
        Utils.convertToLocalDate(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToLocalDateNull1() {
        Utils.convertToLocalDate((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToLocalDateNull2() {
        Utils.convertToLocalDate((String[]) null);
    }
}
