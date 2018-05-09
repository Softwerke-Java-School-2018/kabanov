package com.softwerke.salesregister.menu.browselist;

import com.softwerke.salesregister.io.Formatter;
import com.softwerke.salesregister.io.ConsoleIOStream;
import com.softwerke.salesregister.io.StringLiterals;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.menu.filterlist.FilterPersonListMenu;
import com.softwerke.salesregister.menu.sortlist.SortPersonListMenu;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

public class BrowsePersonListMenu extends Menu {
    public BrowsePersonListMenu() {
        /* Browse person list */
        super("-- Browse and search in person list menu --",
                new MenuItem("Print current list",
                        () -> Formatter.printFormatPerson(internalData.personList.stream()
                                .filter(person -> !person.isDeleted()), internalData.ioStream)),

                new MenuItem("Apply filter to current list", () -> {
                    internalData.persons = internalData.personList.stream().filter(person -> !person.isDeleted());
                    try {
                        new FilterPersonListMenu().execute();
                    } catch (DateTimeParseException e) {
                        internalData.ioStream.ask(StringLiterals.WRONG_DATA_TEXT);
                    }
                    internalData.personList = internalData.persons.collect(Collectors.toList());
                }),

                new MenuItem("Reset current list", internalData::resetPersonList),

                new MenuItem("Sort current list", () -> new SortPersonListMenu().execute()));
    }
}
