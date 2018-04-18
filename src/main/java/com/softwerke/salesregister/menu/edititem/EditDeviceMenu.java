package com.softwerke.salesregister.menu.edititem;

import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EditDeviceMenu extends Menu {
    public EditDeviceMenu() {
        super("-- Edit device menu --", new MenuItem[]{
                new MenuItem("Update vendor name") {
                    @Override
                    public void runItem() {
                        String newName = IOPipe.getNotNullLineByDialog("Enter the new device manufacturer name:");
                        Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                        internalData.daoDevice.updateDevice(builder.vendor(newName).build());
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Update model name") {
                    @Override
                    public void runItem() {
                        String newName = IOPipe.getNotNullLineByDialog("Enter the new device model name:");
                        Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                        internalData.daoDevice.updateDevice(builder.model(newName).build());
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Update color") {
                    @Override
                    public void runItem() {
                        String newColor = IOPipe.getNotNullLineByDialog("Enter the new device color:").toUpperCase();
                        try {
                            Device.DeviceBuilder builder =
                                    Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                            internalData.daoDevice.updateDevice(builder.color(Color.valueOf(newColor)).build());
                            IOPipe.printLine(IOPipe.SUCCESSFUL);
                        } catch (IllegalArgumentException e) {
                            IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                        }
                    }
                },

                new MenuItem("Update device type") {
                    @Override
                    public void runItem() {
                        String newType = IOPipe.getNotNullLineByDialog("Enter the new device type:").toUpperCase();
                        try {
                            Device.DeviceBuilder builder =
                                    Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                            internalData.daoDevice.updateDevice(builder.type(DeviceType.valueOf(newType)).build());
                            IOPipe.printLine(IOPipe.SUCCESSFUL);
                        } catch (IllegalArgumentException e) {
                            IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                        }
                    }
                },

                new MenuItem("Update price") {
                    @Override
                    public void runItem() {
                        String newPrice = IOPipe.getNotNullLineByDialog("Enter the new device price:");
                        try {
                            Device.DeviceBuilder builder =
                                    Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                            internalData.daoDevice.updateDevice(builder.price(new BigDecimal(newPrice)).build());
                            IOPipe.printLine(IOPipe.SUCCESSFUL);
                        } catch (NumberFormatException e) {
                            IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                        }
                    }
                },

                new MenuItem("Update production date") {
                    @Override
                    public void runItem() {
                        LocalDate newDate = IOPipe.getLocalDateByDialog("Enter the new device production date (dd-mm-yyyy with any separator):");
                        Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                        internalData.daoDevice.updateDevice(builder.productionDate(newDate).build());
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Delete device") {
                    @Override
                    public void runItem() {
                        Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                        internalData.daoDevice.updateDevice(builder.isDeleted(true).build());
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                        Menu.incrementRollback();
                    }
                },
        });
    }
}