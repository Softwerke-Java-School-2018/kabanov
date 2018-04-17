package com.softwerke.menu.menu_items;

import com.softwerke.console.IOPipe;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuItem;
import com.softwerke.tables.Sale;

import java.util.Comparator;

class SortSalesHistoryMenu extends Menu {
    SortSalesHistoryMenu() {
        /* Sort person list menu */
        super("-- Sort sales history menu --", new MenuItem[]{
                new MenuItem("Sort by ID") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.saleList.sort(isOrderAscending
                                ? Comparator.comparingInt(Sale::getId)
                                : Comparator.comparingInt(Sale::getId).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by sale date") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.saleList.sort(isOrderAscending
                                ? Comparator.comparing(Sale::getSaleDate)
                                : Comparator.comparing(Sale::getSaleDate).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by total sum") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.saleList.sort(isOrderAscending
                                ? Comparator.comparing(Sale::getTotalSum)
                                : Comparator.comparing(Sale::getTotalSum).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by last name") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.saleList.sort(isOrderAscending
                                /* IntelliJ IDEA reports an error; though there isn't any */
                                ? Comparator.comparing(sale -> sale.getPerson().getLastName())
                                : Comparator.comparing(sale -> sale.getPerson().getLastName()));
                        incrementRollback();
                    }
                },
        });
    }
}
