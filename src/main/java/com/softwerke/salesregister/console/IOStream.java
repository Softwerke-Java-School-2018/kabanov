package com.softwerke.salesregister.console;

import java.time.LocalDate;

public interface IOStream {
    String WRONG_COMMAND_TEXT = "Read command is wrong. Retry input.";
    String WRONG_DATA_TEXT = "Read value is illegal.";
    String ENTRY_IS_DELETED = "Entry with this ID has been deleted.";
    String LIST_IS_EMPTY_TEXT = "List is empty. There's no items to process.";
    String PRESS_ANYKEY_TEXT = "Enter something to continue...";
    String ENTER_SORT_ORDER_TEXT = "Enter \"Y\" to set ascending order, otherwise - descending.";
    String SUCCESSFUL = "Operation successful.";

    void printLine(String message);

    void printLine();

    String ask(String message);

    String ask();

    String askNonEmptyString(String message);

    LocalDate askLocalDate(String message);

    int askInt(String message);

    boolean askBoolean(String message);
}
