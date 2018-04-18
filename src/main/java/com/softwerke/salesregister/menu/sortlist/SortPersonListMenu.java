package com.softwerke.salesregister.menu.sortlist;

import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.person.Person;

import java.util.Comparator;

public class SortPersonListMenu extends Menu {
    public SortPersonListMenu() {
        /* Sort person list menu */
        super("-- Sort person list menu --", new MenuItem[]{
                new MenuItem("Sort by ID") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.personList.sort(isOrderAscending
                                ? Comparator.comparingInt(Person::getId)
                                : Comparator.comparingInt(Person::getId).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by birth date") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.personList.sort(isOrderAscending
                                ? Comparator.comparing(Person::getBirthDate)
                                : Comparator.comparing(Person::getBirthDate).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by first name") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.personList.sort(isOrderAscending
                                ? Comparator.comparing(Person::getFirstName)
                                : Comparator.comparing(Person::getFirstName).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by last name") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.personList.sort(isOrderAscending
                                ? Comparator.comparing(Person::getLastName)
                                : Comparator.comparing(Person::getLastName).reversed());
                        incrementRollback();
                    }
                },
        });
    }
}