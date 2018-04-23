package com.softwerke.salesregister.menu.sortlist;

import com.softwerke.salesregister.console.IOStream;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.invoice.Invoice;

import java.util.Comparator;

public class SortInvoiceListMenu extends Menu {
    public SortInvoiceListMenu() {
        super("-- Sort invoice history menu --",
                new MenuItem("Sort by ID", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.invoiceList.sort(isOrderAscending
                            ? Comparator.comparingInt(Invoice::getId)
                            : Comparator.comparingInt(Invoice::getId).reversed());
                    incrementRollback();
                    incrementRollback();
                }),

                new MenuItem("Sort by sale date", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.invoiceList.sort(isOrderAscending
                            ? Comparator.comparing(Invoice::getDate)
                            : Comparator.comparing(Invoice::getDate).reversed());
                    incrementRollback();
                    incrementRollback();
                }),

                new MenuItem("Sort by total sum", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.invoiceList.sort(isOrderAscending
                            ? Comparator.comparing(Invoice::getTotalSum)
                            : Comparator.comparing(Invoice::getTotalSum).reversed());
                    incrementRollback();
                    incrementRollback();
                }),

                new MenuItem("Sort by last name", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.invoiceList.sort(isOrderAscending
                            /* IntelliJ IDEA reports an error; though there isn't any */
                            ? Comparator.comparing(invoice -> invoice.getPerson().getLastName())
                            : Comparator.comparing(invoice -> invoice.getPerson().getLastName()));
                    incrementRollback();
                }));
    }
}