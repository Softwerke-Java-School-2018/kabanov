package com.softwerke.menu.menu_items;


import com.softwerke.StringPool;
import com.softwerke.console.IOPipe;
import com.softwerke.Utils;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;
import com.softwerke.tables.Device;
import com.softwerke.tables.SeveralDevices;

import static com.softwerke.StringPool.*;
import static com.softwerke.menu.menu_items.MenuInternalData.*;

class OrderCheckoutMenu extends Menu {
    OrderCheckoutMenu() {
        /* Order checkout menu actions */
        super(new MenuAction[]{
                /* Select the customer */
                () -> currentPerson = Utils.selectPerson(database.getPersonList()),

                /* Add item */
                () -> {
                    int deviceId = IOPipe.getIntegerByDialog("Enter the device ID for sale:");
                    for (SeveralDevices orderItem : orderItems)
                        if (orderItem.getDevice().getId() == deviceId) {
                            IOPipe.printLine("Device with this ID has already been added to shop list.");
                            return;
                        }
                    Device device;
                    try {
                        device = database.getDeviceList().get(deviceId);
                        if (device.getId() == -1) {
                            IOPipe.printLine(ENTRY_IS_DELETED);
                            return;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        IOPipe.printLine(WRONG_DATA_TEXT);
                        return;
                    }
                    int amount = IOPipe.getIntegerByDialog("Enter the device's amount for sale:");
                    if (amount < 1) {
                        IOPipe.printLine(WRONG_DATA_TEXT);
                        return;
                    }
                    orderItems.add(new SeveralDevices(device, amount));
                    IOPipe.printLine(SUCCESSFUL);
                },

                /* Print shop list */
                () -> Utils.printShopList(orderItems),

                /* Select sale date */
                () -> saleDate = IOPipe.getLocalDateByDialog("Enter the sale date:"),

                /* Remove item */
                () -> {
                    if (orderItems.isEmpty()) {
                        IOPipe.printLine("Shop list is empty: nothing to remove.");
                        return;
                    }
                    int deviceId = IOPipe.getIntegerByDialog("Enter the device id for removing:");
                    orderItems.removeIf(x -> x.getDevice().getId() == deviceId);
                    IOPipe.printLine(SUCCESSFUL);
                },

                /* Proceed */
                () -> {
                    if (currentPerson.getId() == -1 || orderItems.isEmpty()) {
                        IOPipe.printLine("Customer isn't set or shop list is empty.");
                        return;
                    }
                    database.sell(currentPerson, orderItems, saleDate);
                    IOPipe.printLine(SUCCESSFUL);
                    incrementRollback();
                },
        }, StringPool.ORDER_CHECKOUT_COMMANDS);
    }
}
