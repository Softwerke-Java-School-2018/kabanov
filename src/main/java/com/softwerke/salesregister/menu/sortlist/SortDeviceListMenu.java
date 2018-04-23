package com.softwerke.salesregister.menu.sortlist;

import com.softwerke.salesregister.console.IOStream;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.device.Device;

import java.util.Comparator;

public class SortDeviceListMenu extends Menu {
    public SortDeviceListMenu() {
        super("-- Sort device list menu --",
                new MenuItem("Sort by ID", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparingInt(Device::getId)
                            : Comparator.comparingInt(Device::getId).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by production date", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing(Device::getProductionDate)
                            : Comparator.comparing(Device::getProductionDate).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by vendor name", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing(Device::getVendor)
                            : Comparator.comparing(Device::getVendor).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by model name", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing(Device::getModel)
                            : Comparator.comparing(Device::getModel).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by color", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing(Device::getColor)
                            : Comparator.comparing(Device::getColor).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by device type", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing(Device::getDeviceType)
                            : Comparator.comparing(Device::getDeviceType).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by price", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing(Device::getPrice)
                            : Comparator.comparing(Device::getPrice).reversed());
                    incrementRollback();
                }));
    }
}