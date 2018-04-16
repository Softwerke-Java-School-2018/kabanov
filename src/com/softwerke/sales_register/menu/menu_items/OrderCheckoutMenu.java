package com.softwerke.menu.menu_items;


import com.softwerke.Utils;
import com.softwerke.console.IOPipe;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuItem;
import com.softwerke.tables.Device;
import com.softwerke.tables.Person;
import com.softwerke.tables.SeveralDevices;

class OrderCheckoutMenu extends Menu {
    OrderCheckoutMenu() {
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
                        int deviceId = IOPipe.getIntegerByDialog("Enter the device ID for sale:");
                        for (SeveralDevices orderItem : internalData.orderItems)
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
                        int amount = IOPipe.getIntegerByDialog("Enter the device's amount for sale:");
                        if (amount < 1) {
                            IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                            return;
                        }
                        internalData.orderItems.add(new SeveralDevices(device, amount));
                        IOPipe.printLine(IOPipe.SUCCESSFUL);

                    }
                },

                new MenuItem("Print shop list") {
                    @Override
                    public void runItem() {
                        Utils.printShopList(internalData.orderItems);
                    }
                },

                new MenuItem("Select sale date") {
                    @Override
                    public void runItem() {
                        internalData.saleDate = IOPipe.getLocalDateByDialog("Enter the sale date:");
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
                        internalData.database.sell(internalData.currentPerson, internalData.orderItems, internalData.saleDate);
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                        incrementRollback();
                    }
                },
        });
    }
}