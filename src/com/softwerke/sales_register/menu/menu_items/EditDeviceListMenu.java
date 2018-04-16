package com.softwerke.menu.menu_items;

import com.softwerke.console.IOPipe;
import com.softwerke.Utils;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;
import com.softwerke.tables.Color;
import com.softwerke.tables.Device;
import com.softwerke.tables.DeviceType;

import java.time.LocalDate;

import static com.softwerke.StringPool.*;
import static com.softwerke.menu.menu_items.MenuInternalData.*;

class EditDeviceListMenu extends Menu {
    EditDeviceListMenu() {
        /* Edit device list menu */
        super(new MenuAction[]{
                /* Print device list */
                () -> database.getDeviceList().print(),

                /* Add device */
                () -> {
                    String vendor = IOPipe.getNotNullLineByDialog("Enter device manufacturer:");
                    String model = IOPipe.getNotNullLineByDialog("Enter device model:");
                    LocalDate productionDate = IOPipe.getLocalDateByDialog("Enter production date (dd/mm/yyyy with any separator):");
                    String price = IOPipe.getNotNullLineByDialog("Enter device price:");
                    Color color;
                    DeviceType type;
                    try {
                        color = Color.valueOf(IOPipe.getNotNullLineByDialog("Enter device color:").toUpperCase());
                        type = DeviceType.valueOf(IOPipe.getNotNullLineByDialog("Enter device type:").toUpperCase());
                        database.addDevice(model, vendor, color, productionDate, type, price);
                    } catch (IllegalArgumentException e) {
                        IOPipe.printLine(WRONG_DATA_TEXT);
                        return;
                    }
                    IOPipe.printLine(SUCCESSFUL);
                    searchDeviceList = database.getDeviceList();
                },

                /* Edit device */
                () -> {
                    Utils.updateList(searchDeviceList, database.getDeviceList());
                    exitWhileLoop:
                    while (true) {
                        switch (searchDeviceList.getNotDeletedItemsNumber()) {
                            case 0:
                                IOPipe.printLine("Device list is empty. Nothing to edit.");
                                return;
                            case 1:
                                currentDevice = searchDeviceList.get(0);
                                IOPipe.printLine("Found one device to edit: " + currentDevice);
                                break exitWhileLoop;
                            default:
                                IOPipe.printLine("Found " + searchDeviceList.getNotDeletedItemsNumber() + " devices.");
                                String personIdOrName = IOPipe.getNotNullLineByDialog("Enter device ID:");
                                try {
                                    int readId = Integer.parseInt(personIdOrName);
                                    Device device = searchDeviceList.get(readId);
                                    if (readId != device.getId()) {
                                        /* ID belongs to the deleted device */
                                        IOPipe.printLine(ENTRY_IS_DELETED);
                                        return;
                                    }
                                    Utils.updateList(searchDeviceList, device);
                                } catch (IndexOutOfBoundsException e) {
                                    IOPipe.printLine(WRONG_DATA_TEXT);
                                    return;
                                }
                        }
                    }
                    new EditDeviceMenu().execute();
                },
        }, EDIT_DEVICE_LIST_COMMANDS);
    }
}

