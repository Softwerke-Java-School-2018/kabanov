package com.softwerke.salesregister.menu.checkout;


import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.person.Person;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;

public class OrderCheckoutMenu extends Menu {
    public OrderCheckoutMenu() {
        /* Order checkout menu actions */
        super("-- Order checkout menu --", new MenuItem[]{
                new MenuItem("Select the customer") {
                    @Override
                    public void runItem() {
                        internalData.currentPerson = Utils.selectPerson(internalData.database.getPersonStream());
                        if (internalData.currentPerson == null) internalData.currentPerson = Person.DELETED_PERSON;
                    }
                },

                new MenuItem("Add item") {
                    @Override
                    public void runItem() {
                        int deviceId = IOPipe.getIntegerByDialog("Enter the device ID to sell:");
                        for (InvoiceLine orderItem : internalData.orderItems)
                            if (orderItem.getDevice().getId() == deviceId) {
                                IOPipe.printLine("Device with this ID has already been added to shop list.");
                                return;
                            }
                        Device device;
                        try {
                            device = internalData.database.getDevice(deviceId);
                            if (device.getId() == -1) {
                                IOPipe.printLine(IOPipe.ENTRY_IS_DELETED);
                                return;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                            return;
                        }
                        int amount = IOPipe.getIntegerByDialog("Enter the device's amount to sell:");
                        if (amount < 1) {
                            IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                            return;
                        }
                        internalData.orderItems.add(new InvoiceLine(device, amount));
                        IOPipe.printLine(IOPipe.SUCCESSFUL);

                    }
                },

                new MenuItem("Print shop list") {
                    @Override
                    public void runItem() {
                        Utils.printShopList(internalData.orderItems);
                    }
                },

                new MenuItem("Select the sale date") {
                    @Override
                    public void runItem() {
                        internalData.invoiceDate = IOPipe.getLocalDateByDialog("Enter the sale date:");
                    }
                },

                new MenuItem("Remove item") {
                    @Override
                    public void runItem() {
                        if (internalData.orderItems.isEmpty()) {
                            IOPipe.printLine("Shop list is empty: nothing to remove.");
                            return;
                        }
                        int deviceId = IOPipe.getIntegerByDialog("Enter the device id for removing:");
                        internalData.orderItems.removeIf(x -> x.getDevice().getId() == deviceId);
                        IOPipe.printLine(IOPipe.SUCCESSFUL);

                    }
                },

                new MenuItem("Proceed") {
                    @Override
                    public void runItem() {
                        if (internalData.currentPerson.getId() == -1 || internalData.orderItems.isEmpty()) {
                            IOPipe.printLine("Customer isn't set or shop list is empty.");
                            return;
                        }
                        internalData.database.sell(internalData.currentPerson, internalData.orderItems, internalData.invoiceDate);
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                        incrementRollback();
                    }
                },
        });
    }
}