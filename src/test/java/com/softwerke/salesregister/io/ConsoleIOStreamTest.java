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
    public void consoleIOStream_NullArgs_ExceptionThrown() {
        new ConsoleIOStream(null, null);
    }

    @Test
    public void printLine_Null_EmptyString() throws UnsupportedEncodingException {
        initConsoleWithInputText("");
        console.printLine(null);
        assertEquals(System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void printLine_EmptyString_EmptyString() throws UnsupportedEncodingException {
        initConsoleWithInputText("");
        console.printLine("");
        assertEquals(System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void printLine_Spaces_EmptyString() throws UnsupportedEncodingException {
        initConsoleWithInputText("");
        console.printLine("\n\t");
        assertEquals(System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void printLine_Text_Text() throws UnsupportedEncodingException {
        initConsoleWithInputText("");
        console.printLine(STUB);
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void ask_Null_EmptyString() throws UnsupportedEncodingException {
        initConsoleWithInputText(STUB);

        String answer = console.ask(null);

        assertEquals(STUB, answer);
        assertEquals(System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void ask_Text_Text() throws UnsupportedEncodingException {
        initConsoleWithInputText(STUB);

        String answer = console.ask(STUB);

        assertEquals(STUB, answer);
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void ask_WithoutArguments_EmptyString() throws UnsupportedEncodingException {
        initConsoleWithInputText(STUB);

        String answer = console.ask();

        assertEquals(STUB, answer);
        assertEquals("" + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askLocalDate_IllegalValue1_WRONG_DATA_TEXT() throws UnsupportedEncodingException {
        initConsoleWithInputText("ILLEGAL VALUE" + System.lineSeparator() + "20 10 2010");

        LocalDate answer = console.askLocalDate(STUB);

        assertEquals(LocalDate.of(2010, 10, 20), answer);
        assertEquals(STUB + System.lineSeparator() +
                        StringLiterals.WRONG_DATA_TEXT + System.lineSeparator() +
                        STUB + System.lineSeparator(),
                outputStream.toString("UTF-8"));
    }

    @Test
    public void askLocalDate_IllegalValue2_WRONG_DATA_TEXT() throws UnsupportedEncodingException {
        initConsoleWithInputText("20 10 0000\n20 10 2010");

        LocalDate answer = console.askLocalDate(STUB);

        assertEquals(LocalDate.of(2010, 10, 20), answer);
        assertEquals(STUB + System.lineSeparator() + StringLiterals.WRONG_DATA_TEXT + System.lineSeparator() + STUB + System.lineSeparator(),
                outputStream.toString("UTF-8"));
    }

    @Test
    public void askLocalDate_IllegalValue3_WRONG_DATA_TEXT() throws UnsupportedEncodingException {
        initConsoleWithInputText("21 - 11 - 2011 - 2018\n21 - 11 - 2011");

        LocalDate answer = console.askLocalDate(STUB);

        assertEquals(LocalDate.of(2011, 11, 21), answer);
        assertEquals(STUB + System.lineSeparator() + StringLiterals.WRONG_DATA_TEXT + System.lineSeparator() + STUB + System.lineSeparator(),
                outputStream.toString("UTF-8"));
    }

    @Test
    public void askLocalDate_IllegalValue4_WRONG_DATA_TEXT() throws UnsupportedEncodingException {
        initConsoleWithInputText("22_12_2012_2028\n22_12_2012");

        LocalDate answer = console.askLocalDate(STUB);

        assertEquals(LocalDate.of(2012, 12, 22), answer);
        assertEquals(STUB + System.lineSeparator() + StringLiterals.WRONG_DATA_TEXT + System.lineSeparator() + STUB + System.lineSeparator(),
                outputStream.toString("UTF-8"));
    }

    @Test
    public void askLocalDate_LegalValueFormat1_Success() throws UnsupportedEncodingException {
        initConsoleWithInputText("21 - 11 - 2011");

        LocalDate answer = console.askLocalDate(STUB);

        assertEquals(LocalDate.of(2011, 11, 21), answer);
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));

    }

    @Test
    public void askLocalDate_LegalValueFormat2_Success() throws UnsupportedEncodingException {
        initConsoleWithInputText("22_12_2012");

        LocalDate answer = console.askLocalDate(STUB);

        assertEquals(LocalDate.of(2012, 12, 22), answer);
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askInt_IllegalValue_WRONG_DATA_TEXT() throws UnsupportedEncodingException {
        initConsoleWithInputText("heh" + System.lineSeparator() + "20");

        int answer = console.askInt(STUB);

        assertEquals(Integer.parseInt("20"), answer);
        assertEquals(STUB + System.lineSeparator() +
                        StringLiterals.WRONG_DATA_TEXT + System.lineSeparator() +
                        STUB + System.lineSeparator(),

                outputStream.toString("UTF-8"));
    }

    @Test
    public void askInt_LegalValue_Success() throws UnsupportedEncodingException {
        initConsoleWithInputText("234567889");

        int answer = console.askInt(STUB);

        assertEquals(234567889, answer);
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askBoolean_Y_True() throws UnsupportedEncodingException {
        initConsoleWithInputText("Y");

        boolean answer = console.askBoolean(STUB);

        assertTrue(answer);
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void askBoolean_n_False() throws UnsupportedEncodingException {
        initConsoleWithInputText("n");

        boolean answer = console.askBoolean(STUB);

        assertFalse(answer);
        assertEquals(STUB + System.lineSeparator(), outputStream.toString("UTF-8"));
    }
}
