package com.softwerke.salesregister.menu.editlist;

import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.console.Formatter;
import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.menu.edititem.EditDeviceMenu;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;

import java.math.BigDecimal;
import java.time.LocalDate;


public class EditDeviceListMenu extends Menu {
    public EditDeviceListMenu() {
        /* Edit device list menu */
        super("-- Edit device list menu --", new MenuItem[]{
                new MenuItem("Print device list") {
                    @Override
                    public void runItem() {
                        Formatter.printFormatDevice(internalData.daoDevice.getDeviceStream()
                                .filter(device -> !device.isDeleted()));
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
                            Device.DeviceBuilder builder = new Device.DeviceBuilder()
                                    .model(model)
                                    .vendor(vendor)
                                    .color(color)
                                    .productionDate(productionDate)
                                    .type(type)
                                    .price(new BigDecimal(price))
                                    .isDeleted(false);
                            internalData.daoDevice.addDevice(builder.build());
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
                        internalData.currentDevice = Utils.selectDevice(internalData.daoDevice.getDeviceStream());
                        if (internalData.currentDevice == null) return;
                        new EditDeviceMenu().execute();
                    }
                },
        });
    }
}