package com.softwerke.menu.menu_items;

import com.softwerke.console.IOPipe;
import com.softwerke.list.comparators.*;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;

import static com.softwerke.StringPool.ENTER_SORT_ORDER_TEXT;
import static com.softwerke.StringPool.SORT_DEVICE_LIST_COMMANDS;
import static com.softwerke.menu.menu_items.MenuInternalData.searchDeviceList;

class SortDeviceListMenu extends Menu {
    SortDeviceListMenu() {
        /* Sort device list menu */
        super(new MenuAction[]{
                /* Sort by ID */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchDeviceList.sort(new HasIdComparator<>(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by production date */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchDeviceList.sort(new DeviceProductionDateComparator(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by vendor name */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchDeviceList.sort(new DeviceVendorComparator(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by model name */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchDeviceList.sort(new DeviceModelComparator(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by color */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchDeviceList.sort(new DeviceColorComparator(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by device type */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchDeviceList.sort(new DeviceTypeComparator(isOrderAscending));
                    incrementRollback();
                },

                /* Sort by price */
                () -> {
                    boolean isOrderAscending = IOPipe.getBooleanByDialog(ENTER_SORT_ORDER_TEXT);
                    searchDeviceList.sort(new DevicePriceComparator(isOrderAscending));
                    incrementRollback();
                },
        }, SORT_DEVICE_LIST_COMMANDS);
    }
}
