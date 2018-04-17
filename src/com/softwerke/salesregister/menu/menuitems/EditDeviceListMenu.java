package com.softwerke.salesregister.menu.menuitems;

import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.console.Formatter;
import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.Menu;
import com.softwerke.salesregister.menu.MenuItem;
import com.softwerke.salesregister.tables.Color;
import com.softwerke.salesregister.tables.DeviceType;

import java.time.LocalDate;


class EditDeviceListMenu extends Menu {
    EditDeviceListMenu() {
        /* Edit device list menu */
        super("-- Edit device list menu --", new MenuItem[]{
                new MenuItem("Print device list") {
                    @Override
                    public void runItem() {
                        Formatter.printFormatDevice(
                                internalData.database.getDeviceStream().filter(x -> x.getId() != -1));
                    }
                },

                new MenuItem("Add device") {
                    @Override
                    public void runItem() {
                        String vendor = IOPipe.getNotNullLineByDialog("Enter device manufacturer:");
                        String model = IOPipe.getNotNullLineByDialog("Enter device model:");
                        LocalDate productionDate = IOPipe.getLocalDateByDialog("Enter production date (dd/mm/yyyy with any separator):");
                        String price = IOPipe.getNotNullLineByDialog("Enter device price:");
                        Color color;
                        DeviceType type;
                        try {
                            color = Color.valueOf(IOPipe.getNotNullLineByDialog("Enter device color:").toUpperCase());
                            type = DeviceType.valueOf(IOPipe.getNotNullLineByDialog("Enter device type:").toUpperCase());
                            internalData.database.addDevice(model, vendor, color, productionDate, type, price);
                        } catch (IllegalArgumentException e) {
                            IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                            return;
                        }
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Edit device") {
                    @Override
                    public void runItem() {
                        internalData.currentDevice = Utils.selectDevice(internalData.database.getDeviceStream());
                        if (internalData.currentDevice == null) return;
                        new EditDeviceMenu().execute();
                    }
                },
        });
    }
}