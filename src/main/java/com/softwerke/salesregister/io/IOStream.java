package com.softwerke.salesregister.io;

import java.time.LocalDate;

public interface IOStream {

    void printLine(String message);

    void printLine();

    String ask(String message);

    String ask();

    String askNonEmptyString(String message);

    LocalDate askLocalDate(String message);

    int askInt(String message);

    boolean askBoolean(String message);
}
