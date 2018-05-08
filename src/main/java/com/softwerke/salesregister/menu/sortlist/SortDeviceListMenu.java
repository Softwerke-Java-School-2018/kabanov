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
                            ? Comparator.comparingInt((Device device) -> device.id)
                            : Comparator.comparingInt((Device device) -> device.id).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by production date", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing((Device device) -> device.productionDate)
                            : Comparator.comparing((Device device) -> device.productionDate).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by vendor name", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing((Device device) -> device.vendor)
                            : Comparator.comparing((Device device) -> device.vendor).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by model name", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing((Device device) -> device.model)
                            : Comparator.comparing((Device device) -> device.model).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by color", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing((Device device) -> device.color)
                            : Comparator.comparing((Device device) -> device.color).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by device type", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing((Device device) -> device.deviceType)
                            : Comparator.comparing((Device device) -> device.deviceType).reversed());
                    incrementRollback();
                }),

                new MenuItem("Sort by price", () -> {
                    boolean isOrderAscending = internalData.ioStream.askBoolean(IOStream.ENTER_SORT_ORDER_TEXT);
                    internalData.deviceList.sort(isOrderAscending
                            ? Comparator.comparing((Device device) -> device.price)
                            : Comparator.comparing((Device device) -> device.price).reversed());
                    incrementRollback();
                }));
    }
}