package com.softwerke.menu.menu_items;

import com.softwerke.Utils;
import com.softwerke.console.IOPipe;
import com.softwerke.list.PersonList;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;

import java.time.format.DateTimeParseException;

import static com.softwerke.StringPool.BROWSE_PERSON_LIST_COMMANDS;
import static com.softwerke.StringPool.WRONG_DATA_TEXT;
import static com.softwerke.menu.menu_items.MenuInternalData.database;
import static com.softwerke.menu.menu_items.MenuInternalData.searchPersonList;

class BrowsePersonListMenu extends Menu {
    BrowsePersonListMenu() {
        /* Browse person list */
        super(new MenuAction[]{
                /* Print current list */
                () -> searchPersonList.print(),

                /* Apply filter to current list */
                () -> {
                    PersonList oldPersonList = searchPersonList;
                    oldPersonList.removeIf(x -> x.getId() == -1);
                    while (true) {
                        try {
                            /* Check the list size */
                            /* If list contains 0 or 1 element -> notify and stop filtering */
                            if (Utils.checkListSize(oldPersonList.size())) break;

                            /* Filter by ID */
                            String[] idFromTo = Utils.readRangeFromConsole(
                                    "Enter ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                    "0",
                                    String.valueOf(oldPersonList.size()));
                            oldPersonList = oldPersonList.maskByPersonId(idFromTo[0], idFromTo[1]);
                            if (Utils.checkListSize(oldPersonList.size())) break;           /* Check the list size */

                            /* Filter by first name */
                            String firstNameMask = IOPipe.getNotNullLineByDialog("Enter the person first name (or name part) to filter or \"*\" for any name.");
                            oldPersonList = oldPersonList.maskByPersonFirstName(firstNameMask);
                            if (Utils.checkListSize(oldPersonList.size())) break;           /* Check the list size */

                            /* Filter by last name */
                            String lastNameMask = IOPipe.getNotNullLineByDialog("Enter the person last name (or name part) to filter or \"*\" for any surname.");
                            oldPersonList = oldPersonList.maskByPersonLastName(lastNameMask);
                            if (Utils.checkListSize(oldPersonList.size())) break;           /* Check the list size */

                            /* Filter by birth date */
                            String[] dateFromTo = Utils.readRangeFromConsole(
                                    "Enter birth date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)",
                                    "01-01-0001",
                                    "31-12-9999");
                            oldPersonList = oldPersonList.maskByPersonBirthDate(dateFromTo[0], dateFromTo[1]);
                            /* No need to check the list size */
                            break;
                        } catch (DateTimeParseException | NumberFormatException e) {
                            IOPipe.printLine(WRONG_DATA_TEXT);
                        }
                    }
                    /* Update value with a filtered one */
                    Utils.updateList(searchPersonList, oldPersonList);
                },

                /* Reset current list */
                () -> {
                    /* Update value with a fresh one */
                    Utils.updateList(searchPersonList, database.getPersonList());
                },

                /* Sort current list */
                () -> new SortPersonListMenu().execute(),
        }, BROWSE_PERSON_LIST_COMMANDS);
    }
}
