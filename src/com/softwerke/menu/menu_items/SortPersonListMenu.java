package com.softwerke.menu.menu_items;

import com.softwerke.console.IOPipe;
import com.softwerke.list.comparators.PersonBirthDateComparator;
import com.softwerke.list.comparators.PersonFirstNameComparator;
import com.softwerke.list.comparators.HasIdComparator;
import com.softwerke.list.comparators.PersonLastNameComparator;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;

import static com.softwerke.StringPool.ENTER_SORT_ORDER_TEXT;
import static com.softwerke.StringPool.SORT_PERSON_LIST_COMMANDS;
import static com.softwerke.menu.menu_items.MenuInternalData.searchPersonList;

class SortPersonListMenu extends Menu {
    SortPersonListMenu() {
        /* Sort person list menu */
        super(new MenuAction[]{
                /* Sort by ID */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchPersonList.sort(new HasIdComparator<>(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by first name */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchPersonList.sort(new PersonFirstNameComparator(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by last name */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchPersonList.sort(new PersonLastNameComparator(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by birth date */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchPersonList.sort(new PersonBirthDateComparator(isOrderAscending));
                    incrementRollback();
                },
        }, SORT_PERSON_LIST_COMMANDS);
    }
}
