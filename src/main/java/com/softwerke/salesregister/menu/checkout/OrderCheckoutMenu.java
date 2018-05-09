package com.softwerke.salesregister.menu.checkout;


import com.softwerke.salesregister.io.Formatter;
import com.softwerke.salesregister.io.StringLiterals;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.utils.Utils;

import java.util.Objects;

public class OrderCheckoutMenu extends Menu {
    public OrderCheckoutMenu() {
        super("-- Order checkout menu --",
                new MenuItem("Select the customer", () -> internalData.currentPerson = Utils.selectPerson(internalData.daoPerson.persons(), internalData.ioStream)),

                new MenuItem("Add item", () -> {
                    int deviceId = internalData.ioStream.askInt("Enter the device ID to sell:");
                    for (InvoiceLine orderItem : internalData.orderItems) {
                        if (orderItem.getDevice().getId() == deviceId) {
                            internalData.ioStream.printLine("Device with this ID has already been added to shop list.");
                            return;
                        }
                    }
                    Device device;
                    try {
                        device = internalData.daoDevice.getDevice(deviceId);
                        if (device.isDeleted()) {
                            internalData.ioStream.printLine(StringLiterals.ENTRY_IS_DELETED);
                            return;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        internalData.ioStream.printLine(StringLiterals.WRONG_DATA_TEXT);
                        return;
                    }
                    int amount = internalData.ioStream.askInt("Enter the device's amount to sell:");
                    if (amount < 1) {
                        internalData.ioStream.printLine(StringLiterals.WRONG_DATA_TEXT);
                        return;
                    }
                    internalData.orderItems.add(new InvoiceLine(device, amount));
                    internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                }),

                new MenuItem("Print shop list",
                        () -> Formatter.printShopList(internalData.orderItems.stream(), internalData.ioStream)),

                new MenuItem("Select the sale date",
                        () -> internalData.invoiceDate = internalData.ioStream.askLocalDate("Enter the sale date:")),

                new MenuItem("Remove item", () -> {
                    if (internalData.orderItems.isEmpty()) {
                        internalData.ioStream.printLine("Shop list is empty: nothing to remove.");
                        return;
                    }
                    int deviceId = internalData.ioStream.askInt("Enter the device id for removing:");
                    internalData.orderItems.removeIf(x -> x.getDevice().getId() == deviceId);
                    internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                }),

                new MenuItem("Proceed", () -> {
                    if (Objects.isNull(internalData.currentPerson) || internalData.orderItems.isEmpty()) {
                        internalData.ioStream.printLine("Customer isn't set or shop list is empty.");
                        return;
                    }
                    internalData.daoInvoice
                            .sell(internalData.currentPerson, internalData.orderItems, internalData.invoiceDate);
                    internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                    incrementRollback();
                }));
    }
}
