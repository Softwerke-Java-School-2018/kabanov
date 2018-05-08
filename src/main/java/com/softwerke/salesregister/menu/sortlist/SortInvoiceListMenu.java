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
                            ? Comparator.comparingInt((Invoice invoice) -> invoice.id)
                            : Comparator.comparingInt((Invoice invoice) -> invoice.id).reversed());
                    incrementRollback();
                    incrementRollback();
                }),

                new MenuItem("Sort by sale date", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.invoiceList.sort(isOrderAscending
                            ? Comparator.comparing((Invoice invoice) -> invoice.date)
                            : Comparator.comparing((Invoice invoice) -> invoice.date).reversed());
                    incrementRollback();
                    incrementRollback();
                }),

                new MenuItem("Sort by total sum", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.invoiceList.sort(isOrderAscending
                            ? Comparator.comparing((Invoice invoice) -> invoice.totalSum)
                            : Comparator.comparing((Invoice invoice) -> invoice.totalSum).reversed());
                    incrementRollback();
                    incrementRollback();
                }),

                new MenuItem("Sort by name", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.invoiceList.sort(isOrderAscending
                            ? Comparator.comparing((Invoice invoice) -> invoice.person.getShortName())
                            : Comparator.comparing((Invoice invoice) -> invoice.person.getShortName()).reversed());
                    incrementRollback();
                    incrementRollback();
                }));
    }
}