package com.softwerke.menu.menu_items;


import com.softwerke.console.IOPipe;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuItem;
import com.softwerke.tables.Person;
import com.softwerke.tables.Sale;

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
                        internalData.saleDate = LocalDate.now();
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

                new MenuItem("Delete the sell record from history") {
                    @Override
                    public void runItem() {
                        long salesAmount = internalData.database.getSaleStream().count();
                        if (salesAmount == 0) {
                            IOPipe.printLine("Sales history is empty yet. Nothing to delete.");
                            return;
                        }
                        int idForDelete = IOPipe.getIntegerByDialog("Enter sale ID for removing:");
                        if (idForDelete < salesAmount) {
                            internalData.database.updateSell(idForDelete, Sale.DELETED_SALE);
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

                new MenuItem("Browse sales history") {
                    @Override
                    public void runItem() {
                        internalData.resetSaleList();
                        new BrowseSalesHistoryMenu().execute();
                    }
                },
        });
    }
}