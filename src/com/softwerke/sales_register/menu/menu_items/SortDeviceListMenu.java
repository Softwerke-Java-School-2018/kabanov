package com.softwerke.menu.menu_items;

import com.softwerke.console.IOPipe;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuItem;
import com.softwerke.tables.Device;

import java.util.Comparator;

class SortDeviceListMenu extends Menu {
    SortDeviceListMenu() {
        /* Sort device list menu */
        super("-- Sort device list menu --", new MenuItem[]{
                new MenuItem("Sort by ID") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.deviceList.sort(isOrderAscending
                                ? Comparator.comparingInt(Device::getId)
                                : Comparator.comparingInt(Device::getId).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by production date") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.deviceList.sort(isOrderAscending
                                ? Comparator.comparing(Device::getProductionDate)
                                : Comparator.comparing(Device::getProductionDate).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by vendor name") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.deviceList.sort(isOrderAscending
                                ? Comparator.comparing(Device::getVendor)
                                : Comparator.comparing(Device::getVendor).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by model name") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.deviceList.sort(isOrderAscending
                                ? Comparator.comparing(Device::getModel)
                                : Comparator.comparing(Device::getModel).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by color") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.deviceList.sort(isOrderAscending
                                ? Comparator.comparing(Device::getColor)
                                : Comparator.comparing(Device::getColor).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by device type") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.deviceList.sort(isOrderAscending
                                ? Comparator.comparing(Device::getDeviceType)
                                : Comparator.comparing(Device::getDeviceType).reversed());
                        incrementRollback();
                    }
                },

                new MenuItem("Sort by price") {
                    @Override
                    public void runItem() {
                        boolean isOrderAscending = IOPipe.getBooleanByDialog(IOPipe.ENTER_SORT_ORDER_TEXT);
                        internalData.deviceList.sort(isOrderAscending
                                ? Comparator.comparing(Device::getPrice)
                                : Comparator.comparing(Device::getPrice).reversed());
                        incrementRollback();
                    }
                },
        });
    }
}