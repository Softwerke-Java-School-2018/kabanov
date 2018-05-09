package com.softwerke.salesregister.menu.filterlist;

import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.utils.Interval;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;

public class FilterPersonListMenu extends Menu {
    public FilterPersonListMenu() {
        super("-- Filter device list menu --",
                new MenuItem("Add filter by ID", () -> {
                    String answer = internalData.ioStream.ask("Enter person ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)").trim();
                    if (!"*".equals(answer)) {
                        Interval<Integer> interval = new Interval<Integer>(answer, Integer::new);
                        internalData.persons = internalData.persons
                                .filter(person -> interval.contains(person.getId()));
                    }
                }),

                new MenuItem("Add filter by first name", () -> {
                    String firstName = internalData.ioStream.askNonEmptyString("Enter person first name (or name part) to filter or \"*\" for any name.")
                            .toLowerCase();
                    internalData.persons = internalData.persons.filter(
                            person -> ("*".equals(firstName) || person.getFirstNameLowerCase().contains(firstName)));
                }),

                new MenuItem("Add filter by last name", () -> {
                    String lastName = internalData.ioStream.askNonEmptyString("Enter person last name (or name part) to filter or \"*\" for any surname.")
                            .toLowerCase();
                    internalData.persons = internalData.persons.filter(
                            person -> ("*".equals(lastName) || person.getLastNameLowerCase().contains(lastName)));
                }),

                new MenuItem("Add filter by birth date", () -> {
                    String answer = internalData.ioStream.ask("Enter person birth date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)").trim();
                    if (!"*".equals(answer)) {
                        Interval<ChronoLocalDate> interval = new Interval<ChronoLocalDate>(answer,
                                string -> LocalDate.parse(string.replaceAll("\\D+", "-"),
                                        DateTimeFormatter.ofPattern("d-MM-yyyy")));
                        internalData.persons = internalData.persons
                                .filter(person -> interval.contains(person.getBirthDate()));
                    }
                }));
    }
}
