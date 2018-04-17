package com.softwerke.salesregister.menu.menuitems;


import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.Menu;
import com.softwerke.salesregister.menu.MenuItem;
import com.softwerke.salesregister.tables.Invoice;
import com.softwerke.salesregister.tables.Person;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainMenu extends Menu {
    public MainMenu() {
        /* Application start point */
        super("-- Main menu --", new MenuItem[]{
                new MenuItem("Sell device") {
                    @Override
                    public void runItem() {
                        internalData.currentPerson = Person.DELETED_PERSON;
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
                        long invoicesAmount = internalData.database.getInvoiceStream().count();
                        if (invoicesAmount == 0) {
                            IOPipe.printLine("Invoice history is empty yet. Nothing to delete.");
                            return;
                        }
                        int idForDelete = IOPipe.getIntegerByDialog("Enter invoice ID for removing:");
                        if (idForDelete < invoicesAmount) {
                            internalData.database.updateInvoice(idForDelete, Invoice.DELETED_INVOICE);
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
                        new BrowseSalesHistoryMenu().execute();
                    }
                },
        });
    }
}