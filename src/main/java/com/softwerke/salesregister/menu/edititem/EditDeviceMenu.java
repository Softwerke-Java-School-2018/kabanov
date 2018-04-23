package com.softwerke.salesregister.menu.edititem;

import com.softwerke.salesregister.console.ConsoleIOStream;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EditDeviceMenu extends Menu {
    public EditDeviceMenu() {
        super("-- Edit device menu --",
                new MenuItem("Update vendor name", () -> {
                    String newName = internalData.ioStream.askNonEmptyString("Enter the new device manufacturer name:");
                    Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                    internalData.daoDevice.updateDevice(builder.vendor(newName).build());
                    internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                }),

                new MenuItem("Update model name", () -> {
                    String newName = internalData.ioStream.askNonEmptyString("Enter the new device model name:");
                    Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                    internalData.daoDevice.updateDevice(builder.model(newName).build());
                    internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                }),

                new MenuItem("Update color", () -> {
                    String newColor = internalData.ioStream.askNonEmptyString("Enter the new device color:").toUpperCase();
                    try {
                        Device.DeviceBuilder builder =
                                Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                        internalData.daoDevice.updateDevice(builder.color(Color.valueOf(newColor)).build());
                        internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                    } catch (IllegalArgumentException e) {
                        internalData.ioStream.printLine(ConsoleIOStream.WRONG_DATA_TEXT);
                    }
                }),

                new MenuItem("Update device type", () -> {
                    String newType = internalData.ioStream.askNonEmptyString("Enter the new device type:").toUpperCase();
                    try {
                        Device.DeviceBuilder builder =
                                Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                        internalData.daoDevice.updateDevice(builder.type(DeviceType.valueOf(newType)).build());
                        internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                    } catch (IllegalArgumentException e) {
                        internalData.ioStream.printLine(ConsoleIOStream.WRONG_DATA_TEXT);
                    }
                }),

                new MenuItem("Update price", () -> {
                    String newPrice = internalData.ioStream.askNonEmptyString("Enter the new device price:");
                    try {
                        Device.DeviceBuilder builder =
                                Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                        internalData.daoDevice.updateDevice(builder.price(new BigDecimal(newPrice)).build());
                        internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                    } catch (NumberFormatException e) {
                        internalData.ioStream.printLine(ConsoleIOStream.WRONG_DATA_TEXT);
                    }
                }),

                new MenuItem("Update production date", () -> {
                    LocalDate newDate = internalData.ioStream.askLocalDate("Enter the new device production date (dd-mm-yyyy with any separator):");
                    Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                    internalData.daoDevice.updateDevice(builder.productionDate(newDate).build());
                    internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                }),

                new MenuItem("Delete device", () -> {
                    Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                    internalData.daoDevice.updateDevice(builder.isDeleted(true).build());
                    internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                    Menu.incrementRollback();
                }));
    }
}
