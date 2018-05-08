package com.softwerke.salesregister.menu.filterlist;

import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;

import java.time.LocalDate;

public class FilterPersonListMenu extends Menu {
    public FilterPersonListMenu() {
        super("-- Filter device list menu --",
                new MenuItem("Add filter by ID", () -> {
                    String answer = internalData.ioStream.ask("Enter person ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)");
                    String[] splitAnswer = Utils.splitInTwo(answer, "0", String.valueOf(Integer.MAX_VALUE));
                    int[] bounds = Utils.convertToInt(splitAnswer);
                    internalData.persons = internalData.persons.filter(
                            person -> Utils.isBetween(bounds[0], person.id, bounds[1]));
                }),

                new MenuItem("Add filter by first name", () -> {
                    String firstName = internalData.ioStream.askNonEmptyString("Enter person first name (or name part) to filter or \"*\" for any name.")
                            .toLowerCase();
                    internalData.persons = internalData.persons.filter(
                            person -> ("*".equals(firstName) || person.firstNameLowerCase.contains(firstName)));
                }),

                new MenuItem("Add filter by last name", () -> {
                    String lastName = internalData.ioStream.askNonEmptyString("Enter person last name (or name part) to filter or \"*\" for any surname.")
                            .toLowerCase();
                    internalData.persons = internalData.persons.filter(
                            person -> ("*".equals(lastName) || person.lastNameLowerCase.contains(lastName)));
                }),

                new MenuItem("Add filter by birth date", () -> {
                    String answer = internalData.ioStream.ask("Enter person birth date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)");
                    String[] splitAnswer = Utils.splitInTwo(answer, "01-01-0001", "31-12-9999");
                    LocalDate[] bounds = Utils.convertToLocalDate(splitAnswer);
                    internalData.persons = internalData.persons.filter(
                            person -> Utils.isBetween(bounds[0], person.birthDate, bounds[1]));
                }));
    }
}
