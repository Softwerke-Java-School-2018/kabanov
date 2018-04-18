package com.softwerke.salesregister.console;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class IOPipe {
    public static final String WRONG_COMMAND_TEXT = "Read command is wrong. Retry input.";
    public static final String WRONG_DATA_TEXT = "Read value is illegal.";
    public static final String ENTRY_IS_DELETED = "Entry with this ID has been deleted.";
    public static final String LIST_IS_EMPTY_TEXT = "List is empty. There's no items to process.";
    public static final String LIST_CONTAINS_ONE_ITEM_TEXT = "List already contains one item.";
    public static final String PRESS_ANYKEY_TEXT = "Enter something to continue...";
    public static final String ENTER_SORT_ORDER_TEXT = "Enter \"Y\" to set ascending order, otherwise - descending.";
    public static final String SUCCESSFUL = "Operation successful.";

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));


    public static String getNotNullLineByDialog(String message) {
        String answer = getLineByDialog(message);
        while (answer.length() < 1) {
            printLine(WRONG_DATA_TEXT);
            answer = getLineByDialog(message);
        }
        return answer;
    }

    /**
     * Asks a binary question. Answer is case-insensitive
     *
     * @param message A message to be printed
     * @return true, if entered "Y" and false otherwise
     */
    public static boolean getBooleanByDialog(String message) {
        String input = getLineByDialog(message);
        return input.equalsIgnoreCase("Y");
    }

    public static int getIntegerByDialog(String message) {
        while (true) {
            try {
                String inputLine = getNotNullLineByDialog(message);
                return Integer.parseInt(inputLine);
            } catch (NumberFormatException e) {
                printLine(WRONG_DATA_TEXT);
            }
        }
    }

    public static LocalDate getLocalDateByDialog(String message) {
        while (true) {
            String dateRaw = IOPipe.getNotNullLineByDialog(message);
            String dateFormatted = dateRaw.replaceAll("\\D+", "-");
            try {
                return LocalDate.parse(dateFormatted, DateTimeFormatter.ofPattern("d-MM-yyyy"));
            } catch (DateTimeParseException exception) {
                IOPipe.printLine(WRONG_DATA_TEXT);
            }
        }
    }

    public static String getLineByDialog(String message) {
        if (Objects.nonNull(message) && !message.trim().equals("")) {
            System.out.println(message);
        }
        try {
            return reader.readLine();
        } catch (IOException e) {
            printLine("Occurred an input error.");
            return "";
        }
    }

    public static String getCommand() {
        return getLineByDialog("");
    }

    public static void printLine(String message) {
        try {
            writer.write(message + System.getProperty("line.separator"));
            writer.flush();
        } catch (IOException e) {
            printLine("Occurred an output error.");
        }
    }

    public static void printLine() {
        printLine("");
    }
}
