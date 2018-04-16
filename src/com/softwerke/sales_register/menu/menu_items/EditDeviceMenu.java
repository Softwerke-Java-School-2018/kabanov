package com.softwerke.menu.menu_items;

import com.softwerke.console.IOPipe;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;
import com.softwerke.tables.Color;
import com.softwerke.tables.Device;
import com.softwerke.tables.DeviceType;

import java.time.LocalDate;

import static com.softwerke.StringPool.*;
import static com.softwerke.menu.menu_items.MenuInternalData.currentDevice;
import static com.softwerke.menu.menu_items.MenuInternalData.database;

class EditDeviceMenu extends Menu {
    EditDeviceMenu() {
        super(new MenuAction[]{
                /* Update vendor name menu item */
                () -> {
                    String newName = IOPipe.getNotNullLineByDialog("Enter the new device manufacturer name:");
                    database.updateDevice(currentDevice.setVendorName(newName));
                    IOPipe.printLine(SUCCESSFUL);
                },

                /* Update model name menu item */
                () -> {
                    String newName = IOPipe.getNotNullLineByDialog("Enter the new device model name:");
                    database.updateDevice(currentDevice.setModelName(newName));
                    IOPipe.printLine(SUCCESSFUL);
                },

                /* Update color menu item */
                () -> {
                    String newColor = IOPipe.getNotNullLineByDialog("Enter the new device color:");
                    try {
                        database.updateDevice(currentDevice.setColor(Color.valueOf(newColor)));
                        IOPipe.printLine(SUCCESSFUL);
                    } catch (IllegalArgumentException e) {
                        IOPipe.printLine(WRONG_DATA_TEXT);
                    }
                },

                /* Update device type menu item */
                () -> {
                    String newType = IOPipe.getNotNullLineByDialog("Enter the new device type:");
                    try {
                        database.updateDevice(currentDevice.setType(DeviceType.valueOf(newType)));
                        IOPipe.printLine(SUCCESSFUL);
                    } catch (IllegalArgumentException e) {
                        IOPipe.printLine(WRONG_DATA_TEXT);
                    }
                },

                /* Update price menu item */
                () -> {
                    String newPrice = IOPipe.getNotNullLineByDialog("Enter the new device price:");
                    try {
                        database.updateDevice(currentDevice.setPrice(newPrice));
                        IOPipe.printLine(SUCCESSFUL);
                    } catch (NumberFormatException e) {
                        IOPipe.printLine(WRONG_DATA_TEXT);
                    }
                },

                /* Update production date menu item */
                () -> {
                    LocalDate newDate = IOPipe.getLocalDateByDialog("Enter the new device production date (dd-mm-yyyy with any separator):");
                    database.updateDevice(currentDevice.setProductionDate(newDate));
                    IOPipe.printLine(SUCCESSFUL);
                },

                /* Delete device */
                () -> {
                    database.updateDevice(currentDevice.getId(), Device.DELETED_DEVICE);
                    IOPipe.printLine(SUCCESSFUL);
                    Menu.incrementRollback();
                },
        }, EDIT_DEVICE_COMMANDS);
    }

}
