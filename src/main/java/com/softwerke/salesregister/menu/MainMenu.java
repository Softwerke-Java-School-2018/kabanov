package com.softwerke.salesregister.menu;


import com.softwerke.salesregister.io.StringLiterals;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.menu.browselist.BrowseDeviceListMenu;
import com.softwerke.salesregister.menu.browselist.BrowseInvoiceListMenu;
import com.softwerke.salesregister.menu.browselist.BrowsePersonListMenu;
import com.softwerke.salesregister.menu.checkout.OrderCheckoutMenu;
import com.softwerke.salesregister.menu.editlist.EditDeviceListMenu;
import com.softwerke.salesregister.menu.editlist.EditPersonListMenu;
import com.softwerke.salesregister.tables.invoice.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainMenu extends Menu {
    public MainMenu() {
        /* Application start point */
        super("-- Main menu --",
                new MenuItem("Sell device", () -> {
                    internalData.currentPerson = null;
                    internalData.orderItems = new ArrayList<>();
                    internalData.invoiceDate = LocalDate.now();
                    new OrderCheckoutMenu().execute();
                }),

                new MenuItem("Edit device list", () -> new EditDeviceListMenu().execute()),

                new MenuItem("Edit person list", () -> new EditPersonListMenu().execute()),

                new MenuItem("Delete invoice from history", () -> {
                    List<Invoice> invoices = internalData.daoInvoice.invoices().collect(Collectors.toList());
                    if (invoices.isEmpty()) {
                        internalData.ioStream.printLine("Invoice history is empty yet. Nothing to delete.");
                        return;
                    }
                    int idForDelete = internalData.ioStream.askInt("Enter invoice ID for removing:");
                    if (idForDelete < invoices.size()) {
                        internalData.daoInvoice.updateInvoice(invoices.get(idForDelete).getDisabledCopy());
                        internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                        return;
                    }
                    internalData.ioStream.printLine(StringLiterals.WRONG_DATA_TEXT);
                }),

                new MenuItem("Browse device list", () -> {
                    internalData.resetDeviceList();
                    new BrowseDeviceListMenu().execute();
                }),

                new MenuItem("Browse person list", () -> {
                    internalData.resetPersonList();
                    new BrowsePersonListMenu().execute();
                }),

                new MenuItem("Browse invoice history", () -> {
                    internalData.resetInvoiceList();
                    new BrowseInvoiceListMenu().execute();
                }));
    }
}