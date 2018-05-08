package com.softwerke.salesregister.menu.sortlist;

import com.softwerke.salesregister.console.ConsoleIOStream;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.person.Person;

import java.util.Comparator;

public class SortPersonListMenu extends Menu {
    public SortPersonListMenu() {
        super("-- Sort person list menu --",
                new MenuItem("Sort by ID", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(ConsoleIOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.personList.sort(isOrderAscending
                            ? Comparator.comparingInt((Person person) -> person.id)
                            : Comparator.comparingInt((Person person) -> person.id).reversed());
                    incrementRollback();
                    incrementRollback();
                }),

                new MenuItem("Sort by birth date", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(ConsoleIOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.personList.sort(isOrderAscending
                            ? Comparator.comparing((Person person) -> person.birthDate)
                            : Comparator.comparing((Person person) -> person.birthDate).reversed());
                    incrementRollback();
                    incrementRollback();
                }),

                new MenuItem("Sort by first name", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(ConsoleIOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.personList.sort(isOrderAscending
                            ? Comparator.comparing((Person person) -> person.firstName)
                            : Comparator.comparing((Person person) -> person.firstName).reversed());
                    incrementRollback();
                    incrementRollback();
                }),

                new MenuItem("Sort by last name", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(ConsoleIOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.personList.sort(isOrderAscending
                            ? Comparator.comparing((Person person) -> person.lastName)
                            : Comparator.comparing((Person person) -> person.lastName).reversed());
                    incrementRollback();
                }));
    }
}