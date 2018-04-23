package com.softwerke.salesregister;

import com.softwerke.salesregister.tables.device.Color;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

public class UtilsTest {
    @Test
    public void testSplitInTwo() {
        String[] range;

        range = Utils.splitInTwo("Line", "", "");
        Assert.assertNotNull(range);
        Assert.assertEquals("Line", range[0]);
        Assert.assertEquals("Line", range[1]);

        range = Utils.splitInTwo("Line2   \tLine3", "", "");
        Assert.assertNotNull(range);
        Assert.assertEquals("Line2", range[0]);
        Assert.assertEquals("Line3", range[1]);

        range = Utils.splitInTwo("* \t  ", "1", "-1");
        Assert.assertNotNull(range);
        Assert.assertEquals("1", range[0]);
        Assert.assertEquals("-1", range[1]);

        range = Utils.splitInTwo("1 2 3", "1", "-1");
        Assert.assertNull(range);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertToEnumInstancesException() {
        Object[] stub = Utils.convertToEnumInstances("not_a_color", Color.class).toArray();
    }

    @Test
    public void testConvertToEnumInstancesPass() {
        Color[] colors = new Color[]{Color.RED, Color.RED, Color.BLACK, Color.CHAMPAGNE};

        Stream<Color> colours = Utils.convertToEnumInstances("red, ReD, BLACK, champagne", Color.class);

        Assert.assertArrayEquals(colors, colours.toArray());
    }

    @Test
    public void testIsBetween() {
        Assert.assertTrue(Utils.isBetween(0, 1, 2));
        Assert.assertTrue(Utils.isBetween(0.0, 1.0, 2.0));
        Assert.assertTrue(Utils.isBetween(new BigDecimal(0.0), new BigDecimal(1.0), new BigDecimal(2.0)));
        Assert.assertTrue(Utils.isBetween(LocalDate.MIN, LocalDate.now(), LocalDate.MAX));
        Assert.assertTrue(Utils.isBetween(LocalDate.MIN, LocalDate.MIN, LocalDate.MIN));

        Assert.assertFalse(Utils.isBetween(new BigDecimal(0.0), new BigDecimal(-0.01), new BigDecimal(2.0)));
    }
}
