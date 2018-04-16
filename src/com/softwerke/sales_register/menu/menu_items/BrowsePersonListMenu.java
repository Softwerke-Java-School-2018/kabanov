package com.softwerke.menu.menu_items;

import com.softwerke.Utils;
import com.softwerke.console.Formatter;
import com.softwerke.console.IOPipe;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuItem;
import com.softwerke.tables.Person;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

class BrowsePersonListMenu extends Menu {
    BrowsePersonListMenu() {
        /* Browse person list */
        super("-- Browse and search in person list menu --", new MenuItem[]{
                new MenuItem("Print current list") {
                    @Override
                    public void runItem() {
                        Formatter.printFormatPerson(internalData.personList.stream().filter(x -> x.getId() != -1));
                    }
                },

                new MenuItem("Apply filter to current list") {
                    @Override
                    public void runItem() {
                        List<Person> personList = new ArrayList<>(internalData.personList);
                        personList.removeIf(person -> person.getId() == -1);
                        while (true) {
                            try {
                                /* Check the list size: if it contains 0 or 1 elements -> notify and stop filtering */
                                if (Utils.checkListSize(personList.size())) break;

                                /* Filter by ID */
                                int[] idBounds = Utils.convertToInt(Utils.readRangeFromConsole(
                                        "Enter ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                        "0",
                                        String.valueOf(Integer.MAX_VALUE)));
                                personList.removeIf(person ->
                                        !Utils.isBetween(idBounds[0], person.getId(), idBounds[1]));
                                if (Utils.checkListSize(personList.size())) break;       /* Check the list size */

                                /* Filter by first name */
                                String firstNameMask = IOPipe.getNotNullLineByDialog("Enter the person first name (or name part) to filter or \"*\" for any name.")
                                        .toLowerCase();
                                personList.removeIf(person -> !("*".equals(firstNameMask) ||
                                        person.getFirstNameLowerCase().contains(firstNameMask)));
                                if (Utils.checkListSize(personList.size())) break;       /* Check the list size */

                                /* Filter by last name */
                                String lastNameMask = IOPipe.getNotNullLineByDialog("Enter the person last name (or name part) to filter or \"*\" for any surname.")
                                        .toLowerCase();
                                personList.removeIf(person -> !("*".equals(lastNameMask) ||
                                        person.getLastNameLowerCase().contains(lastNameMask)));
                                if (Utils.checkListSize(personList.size())) break;       /* Check the list size */

                                /* Filter by birth date */
                                LocalDate[] dateBounds = Utils.convertToDate(Utils.readRangeFromConsole(
                                        "Enter birth date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)",
                                        "01-01-0001",
                                        "31-12-9999"));
                                personList.removeIf(person ->
                                        !Utils.isBetween(dateBounds[0], person.getBirthDate(), dateBounds[1]));
                                /* No need to check the list size */
                                break;
                            } catch (DateTimeParseException | NumberFormatException e) {
                                IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                            }
                        }
                        /* Update value with a filtered one */
                        internalData.personList = personList;
                    }
                },

                new MenuItem("Reset current list") {
                    @Override
                    public void runItem() {
                        internalData.resetPersonList();
                    }
                },

                new MenuItem("Sort current list") {
                    @Override
                    public void runItem() {
                        new SortPersonListMenu().execute();
                    }
                },
        });
    }
}