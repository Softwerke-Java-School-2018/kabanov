package com.softwerke.salesregister.console;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

public class ConsoleIOStreamTest {
    private static ConsoleIOStream console;

    private static void setConsole(String input) {
        try {
            console = new ConsoleIOStream(IOUtils.toInputStream(input, "UTF-8"), System.out);
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void askLocalDateTest() {
        setConsole("20 10 2010");

        Assert.assertEquals(console.askLocalDate("stub"), LocalDate.parse("2010-10-20"));

        setConsole("21 - 11 - 2011");

        Assert.assertEquals(console.askLocalDate("stub"), LocalDate.parse("2011-11-21"));

        setConsole("22_12_2012");

        Assert.assertEquals(console.askLocalDate("stub"), LocalDate.parse("2012-12-22"));
    }

    @Test
    public void askLocalDateTestBadInput() {
        setConsole("20 10 0000\n20 10 2010");

        Assert.assertEquals(console.askLocalDate("stub"), LocalDate.parse("2010-10-20"));

        setConsole("21 - 11 - 2011 - 2018\n21 - 11 - 2011");

        Assert.assertEquals(console.askLocalDate("stub"), LocalDate.parse("2011-11-21"));

        setConsole("22_12_2012_2028\n22_12_2012");

        Assert.assertEquals(console.askLocalDate("stub"), LocalDate.parse("2012-12-22"));
    }
}
