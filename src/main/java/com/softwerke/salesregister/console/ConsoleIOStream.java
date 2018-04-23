package com.softwerke.salesregister.console;

import javax.annotation.Nullable;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class ConsoleIOStream implements IOStream {

    private final BufferedReader reader;
    private final BufferedWriter writer;

    public ConsoleIOStream(InputStream in, OutputStream out) {
        reader = new BufferedReader(new InputStreamReader(in));
        writer = new BufferedWriter(new OutputStreamWriter(out));
    }

    public void printLine(String message) {
        try {
            writer.write(message + System.getProperty("line.separator"));
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Can't write to System.out!");
        }
    }

    public void printLine() {
        printLine("");
    }

    public String ask(@Nullable String message) {
        if (Objects.nonNull(message) && !message.trim().equals("")) {
            printLine(message);
        }
        try {
            return reader.readLine();
        } catch (IOException e) {
            printLine("Occurred an input error.");
            return "";
        }
    }

    public String ask() {
        return ask(null);
    }

    @Override
    public String askNonEmptyString(String message) {
        String answer = ask(message);
        while (answer.length() < 1) {
            printLine(WRONG_DATA_TEXT);
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
                printLine(WRONG_DATA_TEXT);
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
                printLine(WRONG_DATA_TEXT);
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