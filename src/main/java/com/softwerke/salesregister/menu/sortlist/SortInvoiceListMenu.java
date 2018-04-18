package com.softwerke.salesregister.menu.sortlist;

import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.invoice.Invoice;

import java.util.Comparator;

public class SortInvoiceListMenu extends Menu {
    public SortInvoiceListMenu() {
        /* Sort person list menu */
        super("-- Sort invoice history menu --", new MenuItem[]{
                new MenuItem("Sort by ID") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.saleList.sort(isOrderAscending
                                ? Comparator.comparingInt(Invoice::getId)
                                : Comparator.comparingInt(Invoice::getId).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by sale date") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.saleList.sort(isOrderAscending
                                ? Comparator.comparing(Invoice::getDate)
                                : Comparator.comparing(Invoice::getDate).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by total sum") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.saleList.sort(isOrderAscending
                                ? Comparator.comparing(Invoice::getTotalSum)
                                : Comparator.comparing(Invoice::getTotalSum).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by last name") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.saleList.sort(isOrderAscending
                                /* IntelliJ IDEA reports an error; though there isn't any */
                                ? Comparator.comparing(invoice -> invoice.getPerson().getLastName())
                                : Comparator.comparing(invoice -> invoice.getPerson().getLastName()));
                        incrementRollback();
                    }
                },
        });
    }
}
