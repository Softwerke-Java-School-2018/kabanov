package com.softwerke.salesregister.io;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ConsoleIOStreamTest {
    private static ConsoleIOStream console;
    private static ByteArrayOutputStream outputStream;
    public static final String STUB = "stub!";

    private static void initConsoleWithInputText(String input) {
        try {
            outputStream = new ByteArrayOutputStream();
            console = new ConsoleIOStream(IOUtils.toInputStream(input, "UTF-8"), outputStream);
        } catch (IOException e) {
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void consoleIOStreamTestNull() {
        new ConsoleIOStream(null, null);
    }

    @Test
    public void printLineTest() throws UnsupportedEncodingException {
        initConsoleWithInputText("");
        console.printLine(null);
        assertEquals(System.lineSeparator(), outputStream.toString("UTF-8"));

        initConsoleWithInputText("");
        console.printLine("");
        assertEquals(System.lineSeparator(), outputStream.toString("UTF-8"));

        initConsoleWithInputText("");
        console.printLine("\n\t");
        assertEquals(System.lineSeparator(), outputStream.toString("UTF-8"));

        initConsoleWithInputText("");
        console.printLine(STUB);
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askTest0() throws UnsupportedEncodingException {
        initConsoleWithInputText(STUB);

        String answer = console.ask(null);

        assertEquals(STUB, answer);
        assertEquals(System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askTest1() throws UnsupportedEncodingException {
        initConsoleWithInputText(STUB);

        String answer = console.ask(STUB);

        assertEquals(STUB, answer);
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askTest2() throws UnsupportedEncodingException {
        initConsoleWithInputText(STUB);

        String answer = console.ask();

        assertEquals(STUB, answer);
        assertEquals("" + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askLocalDateTest() throws UnsupportedEncodingException {
        initConsoleWithInputText(System.lineSeparator() + "20 10 2010");

        assertEquals(LocalDate.parse("2010-10-20"), console.askLocalDate(STUB));
        assertEquals(STUB + System.lineSeparator() +
                        StringLiterals.WRONG_DATA_TEXT + System.lineSeparator() +
                        STUB + System.lineSeparator(),

                outputStream.toString("UTF-8"));

        initConsoleWithInputText("21 - 11 - 2011");

        assertEquals(LocalDate.parse("2011-11-21"), console.askLocalDate(STUB));
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));

        initConsoleWithInputText("22_12_2012");

        assertEquals(LocalDate.parse("2012-12-22"), console.askLocalDate(STUB));
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askIntTest() throws UnsupportedEncodingException {
        initConsoleWithInputText("heh" + System.lineSeparator() + "20");

        assertEquals(Integer.parseInt("20"), console.askInt(STUB));
        assertEquals(STUB + System.lineSeparator() +
                StringLiterals.WRONG_DATA_TEXT + System.lineSeparator() +
                STUB + System.lineSeparator(),

                outputStream.toString("UTF-8"));
    }

    @Test
    public void askBooleanTest1() throws UnsupportedEncodingException {
        initConsoleWithInputText("Y");

        assertTrue(console.askBoolean(STUB));
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askBooleanTest2() throws UnsupportedEncodingException {
        initConsoleWithInputText("n");

        assertFalse(console.askBoolean(STUB));
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askLocalDateTestBadInput() throws UnsupportedEncodingException {
        initConsoleWithInputText("20 10 0000\n20 10 2010");

        assertEquals(LocalDate.parse("2010-10-20"), console.askLocalDate(STUB));
        assertEquals(STUB + System.lineSeparator() + StringLiterals.WRONG_DATA_TEXT + System.lineSeparator() + STUB + System.lineSeparator(),
                outputStream.toString("UTF-8"));

        initConsoleWithInputText("21 - 11 - 2011 - 2018\n21 - 11 - 2011");

        assertEquals(console.askLocalDate(STUB), LocalDate.parse("2011-11-21"));
        assertEquals(STUB + System.lineSeparator() + StringLiterals.WRONG_DATA_TEXT + System.lineSeparator() + STUB + System.lineSeparator(),
                outputStream.toString("UTF-8"));

        initConsoleWithInputText("22_12_2012_2028\n22_12_2012");

        assertEquals(console.askLocalDate(STUB), LocalDate.parse("2012-12-22"));
        assertEquals(STUB + System.lineSeparator() + StringLiterals.WRONG_DATA_TEXT + System.lineSeparator() + STUB + System.lineSeparator(),
                outputStream.toString("UTF-8"));
    }
}
