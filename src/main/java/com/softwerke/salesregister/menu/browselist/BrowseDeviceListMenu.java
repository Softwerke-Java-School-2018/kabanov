package com.softwerke.salesregister.menu.browselist;

import com.softwerke.salesregister.console.Formatter;
import com.softwerke.salesregister.console.ConsoleIOStream;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.menu.filterlist.FilterDeviceListMenu;
import com.softwerke.salesregister.menu.sortlist.SortDeviceListMenu;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

public class BrowseDeviceListMenu extends Menu {
    public BrowseDeviceListMenu() {
        super("-- Browse and search in device list menu --",
                new MenuItem("Print current list",
                        () -> Formatter.printFormatDevice(internalData.deviceList.stream()
                                .filter(device -> !device.isDeleted()), internalData.ioStream)),

                new MenuItem("Apply filter to current list", () -> {
                    internalData.devices = internalData.deviceList.stream().filter(device -> !device.isDeleted());
                    try {
                        new FilterDeviceListMenu().execute();
                        internalData.deviceList = internalData.devices.collect(Collectors.toList());
                    } catch (IllegalArgumentException | DateTimeParseException e) {
                        internalData.ioStream.ask(ConsoleIOStream.WRONG_DATA_TEXT);
                    }
                }),

                new MenuItem("Reset current list", internalData::resetDeviceList),

                new MenuItem("Sort current list", () -> new SortDeviceListMenu().execute()));
    }
}
