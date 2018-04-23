package com.softwerke.salesregister.menu.filterlist;

import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;

import java.time.LocalDate;

public class FilterPersonListMenu extends Menu {
    public FilterPersonListMenu() {
        super("-- Filter device list menu --",
                new MenuItem("Filter by ID", () -> {
                    String answer = internalData.ioStream.ask("Enter person ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)");
                    String[] splitAnswer = Utils.splitInTwo(answer, "0", String.valueOf(Integer.MAX_VALUE));
                    int[] bounds = Utils.convertToInt(splitAnswer);
                    internalData.persons = internalData.persons.filter(
                            person -> Utils.isBetween(bounds[0], person.getId(), bounds[1]));
                }),

                new MenuItem("Filter by first name", () -> {
                    String firstName = internalData.ioStream.askNonEmptyString("Enter person first name (or name part) to filter or \"*\" for any name.")
                            .toLowerCase();
                    internalData.persons = internalData.persons.filter(
                            person -> ("*".equals(firstName) || person.getFirstNameLowerCase().contains(firstName)));
                }),

                new MenuItem("Filter by last name", () -> {
                    String lastName = internalData.ioStream.askNonEmptyString("Enter person last name (or name part) to filter or \"*\" for any surname.")
                            .toLowerCase();
                    internalData.persons = internalData.persons.filter(
                            person -> ("*".equals(lastName) || person.getLastNameLowerCase().contains(lastName)));
                }),

                new MenuItem("Filter by birth date", () -> {
                    String answer = internalData.ioStream.ask("Enter person birth date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)");
                    String[] splitAnswer = Utils.splitInTwo(answer, "01-01-0001", "31-12-9999");
                    LocalDate[] bounds = Utils.convertToLocalDate(splitAnswer);
                    internalData.persons = internalData.persons.filter(
                            person -> Utils.isBetween(bounds[0], person.getBirthDate(), bounds[1]));
                }));
    }
}
