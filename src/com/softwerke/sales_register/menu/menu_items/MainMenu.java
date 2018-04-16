package com.softwerke.menu.menu_items;


import com.softwerke.StringPool;
import com.softwerke.console.IOPipe;
import com.softwerke.list.SaleList;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;
import com.softwerke.tables.Database;
import com.softwerke.tables.Person;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.softwerke.StringPool.SUCCESSFUL;
import static com.softwerke.StringPool.WRONG_DATA_TEXT;
import static com.softwerke.tables.Sale.DELETED_SALE;

public class MainMenu extends Menu {
    public MainMenu() {
        /* Application start point */
        super(new MenuAction[]{
                /* Sell device */                                           // TODO
                () -> {
                    MenuInternalData.currentPerson = Person.DELETED_PERSON;
                    MenuInternalData.orderItems = new ArrayList<>();
                    MenuInternalData.saleDate = LocalDate.now();
                    new OrderCheckoutMenu().execute();
                },

                /* Edit device list */
                () -> {
                    MenuInternalData.searchDeviceList = MenuInternalData.database.getDeviceList();
                    new EditDeviceListMenu().execute();
                },

                /* Edit person list */
                () -> {
                    MenuInternalData.searchPersonList = MenuInternalData.database.getPersonList();
                    new EditPersonListMenu().execute();
                },

                /* Delete the sell record from history */
                () -> {
                    SaleList salesHistory = MenuInternalData.database.getSalesHistory();
                    if (salesHistory.isEmpty()) {
                        IOPipe.printLine("Sales history is empty yet. Nothing to delete.");
                        return;
                    }
                    int idForDelete = IOPipe.getIntegerByDialog("Enter sale ID for removing:");
                    if (idForDelete < salesHistory.size()) {
                        MenuInternalData.database.updateSell(idForDelete, DELETED_SALE);
                        IOPipe.printLine(SUCCESSFUL);
                        return;
                    }
                    IOPipe.printLine(WRONG_DATA_TEXT);
                },

                /* Browse device list */
                () -> {
                    MenuInternalData.searchDeviceList = MenuInternalData.database.getDeviceList();
                    new BrowseDeviceListMenu().execute();
                },

                /* Browse person list */
                () -> {
                    MenuInternalData.searchPersonList = MenuInternalData.database.getPersonList();
                    new BrowsePersonListMenu().execute();
                },

                /* Browse sales history */
                () -> {
                    MenuInternalData.searchSalesList = MenuInternalData.database.getSalesHistory();
                    new BrowseSalesHistoryMenu().execute();
                },
        }, StringPool.MAIN_COMMANDS);
    }

    public static void setDatabase(Database database) {
        MenuInternalData.database = database;
    }
}
