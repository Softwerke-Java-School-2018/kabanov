package com.softwerke.salesregister.menu.browselist;

import com.softwerke.salesregister.console.ConsoleIOStream;
import com.softwerke.salesregister.console.Formatter;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.menu.filterlist.FilterInvoiceListMenu;
import com.softwerke.salesregister.menu.sortlist.SortInvoiceListMenu;
import com.softwerke.salesregister.tables.invoice.Invoice;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

public class BrowseInvoiceListMenu extends Menu {
    public BrowseInvoiceListMenu() {
        super("-- Browse and search in sales history menu --",
                new MenuItem("Print current list",
                        () -> Formatter.printFormatInvoice(internalData.invoiceList.stream()
                                .filter(invoice -> !invoice.isDeleted()), internalData.ioStream)),

                new MenuItem("Apply filter to current list", () -> {
                    internalData.invoices = internalData.invoiceList.stream().filter(invoice -> !invoice.isDeleted());
                    try {
                        new FilterInvoiceListMenu().execute();
                    } catch (NumberFormatException | DateTimeParseException e) {
                        internalData.ioStream.printLine(ConsoleIOStream.WRONG_DATA_TEXT);
                    }
                    internalData.invoiceList = internalData.invoices.collect(Collectors.toList());
                }),

                new MenuItem("Reset current list", internalData::resetInvoiceList),

                new MenuItem("Sort current list", () -> new SortInvoiceListMenu().execute()),

                new MenuItem("Print sale details", () -> {
                    int saleId = internalData.ioStream.askInt("Enter invoice ID for printing:");
                    Invoice invoice = internalData.daoInvoice.getInvoice(saleId);
                    if (invoice.isDeleted()) {
                        internalData.ioStream.printLine(ConsoleIOStream.ENTRY_IS_DELETED);
                    } else {
                        Formatter.printReceipt(invoice, internalData.ioStream);
                    }
                }));
    }
}
