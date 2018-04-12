package com.softwerke.menu.menu_items;

import com.softwerke.console.IOPipe;
import com.softwerke.list.comparators.HasIdComparator;
import com.softwerke.list.comparators.SaleDateComparator;
import com.softwerke.list.comparators.SaleLastNameComparator;
import com.softwerke.list.comparators.SaleTotalComparator;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;

import static com.softwerke.StringPool.ENTER_SORT_ORDER_TEXT;
import static com.softwerke.StringPool.SORT_SALES_HISTORY_COMMANDS;
import static com.softwerke.menu.menu_items.MenuInternalData.searchSalesList;

class SortSalesHistoryMenu extends Menu {
    SortSalesHistoryMenu() {
        /* Sort person list menu */
        super(new MenuAction[]{
                /* Sort by ID */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchSalesList.sort(new HasIdComparator<>(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by last name */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchSalesList.sort(new SaleLastNameComparator(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by sale date */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchSalesList.sort(new SaleDateComparator(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by total sum */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchSalesList.sort(new SaleTotalComparator(isOrderAscending));
                    incrementRollback();
                },
        }, SORT_SALES_HISTORY_COMMANDS);
    }
}
