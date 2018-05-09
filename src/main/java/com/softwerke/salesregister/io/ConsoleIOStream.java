package com.softwerke.salesregister.io;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConsoleIOStream implements IOStream {

    private final BufferedReader reader;
    private final BufferedWriter writer;

    public ConsoleIOStream(InputStream in, OutputStream out) {
        if (!ObjectUtils.allNotNull(in, out)) {
            Logger.fatal("One or more arguments is null! [ConsoleIOStream constructor]");
            throw new IllegalArgumentException(StringLiterals.NULL_ARG_EXC);
        }
        reader = new BufferedReader(new InputStreamReader(in));
        writer = new BufferedWriter(new OutputStreamWriter(out));
    }

    public void printLine(String message) {
        try {
            if (!StringUtils.isBlank(message)) {
                writer.write(message);
            }
            writer.write(System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            Logger.fatal("Can't write to output stream!");
            System.exit(-1);
        }
    }

    public void printLine() {
        printLine(null);
    }

    public String ask(String message) {
        printLine(message);
        try {
            return reader.readLine();
        } catch (IOException e) {
            printLine("Occurred an input error.");
            Logger.error("Occurred an input error.");
            return "";
        }
    }

    public String ask() {
        return ask(null);
    }

    @Override
    public String askNonEmptyString(String message) {
        String answer = ask(message);
        while (StringUtils.isEmpty(answer)) {
            printLine(StringLiterals.WRONG_DATA_TEXT);
            answer = ask(message);
        }
        return answer;
    }

    public LocalDate askLocalDate(String message) {
        while (true) {
            String dateRaw = askNonEmptyString(message);
            String dateFormatted = dateRaw.replaceAll("\\D+", "-");
            try {
                return LocalDate.parse(dateFormatted, DateTimeFormatter.ofPattern("d-MM-yyyy"));
            } catch (DateTimeParseException exception) {
                printLine(StringLiterals.WRONG_DATA_TEXT);
            }
        }
    }

    @Override
    public int askInt(String message) {
        while (true) {
            try {
                String inputLine = askNonEmptyString(message);
                return Integer.parseInt(inputLine);
            } catch (NumberFormatException e) {
                printLine(StringLiterals.WRONG_DATA_TEXT);
            }
        }
    }

    /**
     * Asks a binary question. Answer is case-insensitive
     *
     * @param message A message to be printed
     * @return true, if entered "Y" and false otherwise
     */
    @Override
    public boolean askBoolean(String message) {
        String input = ask(message);
        return input.equalsIgnoreCase("Y");
    }
}