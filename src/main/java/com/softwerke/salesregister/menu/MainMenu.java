package com.softwerke.salesregister.menu;


import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.checkout.OrderCheckoutMenu;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.menu.browselist.BrowseDeviceListMenu;
import com.softwerke.salesregister.menu.browselist.BrowsePersonListMenu;
import com.softwerke.salesregister.menu.browselist.BrowseInvoiceListMenu;
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
        super("-- Main menu --", new MenuItem[]{
                new MenuItem("Sell device") {
                    @Override
                    public void runItem() {
                        internalData.currentPerson = null;
                        internalData.orderItems = new ArrayList<>();
                        internalData.invoiceDate = LocalDate.now();
                        new OrderCheckoutMenu().execute();
                    }
                },

                new MenuItem("Edit device list") {
                    @Override
                    public void runItem() {
                        new EditDeviceListMenu().execute();
                    }
                },

                new MenuItem("Edit person list") {
                    @Override
                    public void runItem() {
                        new EditPersonListMenu().execute();
                    }
                },

                new MenuItem("Delete invoice from history") {
                    @Override
                    public void runItem() {
                        List<Invoice> invoices = internalData.daoInvoice.getInvoiceStream().collect(Collectors.toList());
                        if (invoices.isEmpty()) {
                            IOPipe.printLine("Invoice history is empty yet. Nothing to delete.");
                            return;
                        }
                        int idForDelete = IOPipe.getIntegerByDialog("Enter invoice ID for removing:");
                        if (idForDelete < invoices.size()) {
                            internalData.daoInvoice.updateInvoice(invoices.get(idForDelete).getDisabledCopy());
                            IOPipe.printLine(IOPipe.SUCCESSFUL);
                            return;
                        }
                        IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                    }
                },

                new MenuItem("Browse device list") {
                    @Override
                    public void runItem() {
                        internalData.resetDeviceList();
                        new BrowseDeviceListMenu().execute();
                    }
                },

                new MenuItem("Browse person list") {
                    @Override
                    public void runItem() {
                        internalData.resetPersonList();
                        new BrowsePersonListMenu().execute();
                    }
                },

                new MenuItem("Browse invoice history") {
                    @Override
                    public void runItem() {
                        internalData.resetInvoiceList();
                        new BrowseInvoiceListMenu().execute();
                    }
                },
        });
    }
}