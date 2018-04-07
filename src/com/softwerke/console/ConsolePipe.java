package com.softwerke.console;

import java.util.Scanner;

/** Provides static methods for user interface */
public class ConsolePipe {
    private static final long SLEEP_TIME = 1000L;
    public static final String WRONG_COMMAND_TEXT = "Read command is wrong. Retry input.";
    public static final String WRONG_DATA_TEXT = "Read value is illegal.";
    public static final String LIST_IS_EMPTY_TEXT = "List is already empty. Nothing to filter.";
    public static final String LIST_CONTAINS_ONE_ITEM_TEXT = "List already contains one item.";
    public static final String PRESS_ANYKEY_TEXT = "Enter some key to continue...";
    public static final String ENTER_SORT_ORDER_TEXT = "Enter \"Y\" to set ascending order, otherwise - descending.";
    private static final Scanner scanner = new Scanner(System.in);

    public static String getNotNullLineByDialog(String message) {
        String answer = getLineByDialog(message);
        while (answer.length() < 1) {
            printWithDelay(WRONG_DATA_TEXT);
            answer = getLineByDialog(message);
        }
        return answer;
    }

    /** Asks a binary question. Answer is case-insensitive
     *
     * @param message A message to be printed
     * @return true, if entered "Y" and false otherwise
     */
    public static boolean getBooleanByDialog(String message) {
        String input = getLineByDialog(message);
        return input.equalsIgnoreCase("Y");
    }

    /** Prints a message and gets a single line from console.
     *
     * @param message A message to be printed
     */
    public static String getLineByDialog(String message) {
        System.out.println(message);
        return scanner.nextLine();                      // FIXME Sometimes freezing at line getting
    }

    /** Prints a message and freezes the thread for <b>SLEEP_TIME</b> ms.
     *
     * @param message A message to be printed
     */
    public static void printWithDelay(String message) {
        System.out.println(message);
        /* Disabled (temporarily or not) */
        // System.out.flush();
        // pause();
    }

    /** Prints a strings array at new line each.
     *
     * @param strings Strings to be printed
     */
    public static void printStringArray(String... strings) {
        for (String s : strings)
            System.out.println(s);
    }

    /** Returns a line from console. */
    public static String getCommand() {
        return scanner.nextLine();
    }

    private static void pause() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
