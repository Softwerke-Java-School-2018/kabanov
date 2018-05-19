package com.softwerke.salesregister.menu.edititem;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.io.StringLiterals;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EditDeviceMenu extends Menu {
    private static final Logger logger = LogManager.getLogger(EditDeviceMenu.class);

    public EditDeviceMenu() {
        super("-- Edit device menu --",
                new MenuItem("Update vendor name", () -> {
                    String newName = internalData.ioStream.askNonEmptyString("Enter the new device manufacturer name:");
                    Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                    try {
                        internalData.daoDevice.updateDevice(builder.vendor(newName).build());
                        internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                    } catch (BuilderNotInitializedException e) {
                        logger.error("Builder in is not initialized! [EditDeviceMenu - vendor]" + e);
                        internalData.ioStream.printLine(StringLiterals.PROGRAM_ERROR);
                    }
                }),

                new MenuItem("Update model name", () -> {
                    String newName = internalData.ioStream.askNonEmptyString("Enter the new device model name:");
                    Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                    try {
                        internalData.daoDevice.updateDevice(builder.model(newName).build());
                        internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                    } catch (BuilderNotInitializedException e) {
                        logger.error("Builder in is not initialized! [EditDeviceMenu - model]" + e);
                        internalData.ioStream.printLine(StringLiterals.PROGRAM_ERROR);
                    }
                }),

                new MenuItem("Update color", () -> {
                    String newColor = internalData.ioStream.askNonEmptyString("Enter the new device color:").toUpperCase();
                    try {
                        Device.DeviceBuilder builder =
                                Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                        internalData.daoDevice.updateDevice(builder.color(Color.valueOf(newColor)).build());
                        internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                    } catch (IllegalArgumentException e) {
                        internalData.ioStream.printLine(StringLiterals.WRONG_DATA_TEXT);
                    } catch (BuilderNotInitializedException e) {
                        logger.error("Builder in is not initialized! [EditDeviceMenu - color]" + e);
                        internalData.ioStream.printLine(StringLiterals.PROGRAM_ERROR);
                    }
                }),

                new MenuItem("Update device type", () -> {
                    String newType = internalData.ioStream.askNonEmptyString("Enter the new device type:").toUpperCase();
                    try {
                        Device.DeviceBuilder builder =
                                Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                        internalData.daoDevice.updateDevice(builder.type(DeviceType.valueOf(newType)).build());
                        internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                    } catch (IllegalArgumentException e) {
                        internalData.ioStream.printLine(StringLiterals.WRONG_DATA_TEXT);
                    } catch (BuilderNotInitializedException e) {
                        logger.error("Builder in is not initialized! [EditDeviceMenu - device type]" + e);
                        internalData.ioStream.printLine(StringLiterals.PROGRAM_ERROR);
                    }
                }),

                new MenuItem("Update price", () -> {
                    String newPrice = internalData.ioStream.askNonEmptyString("Enter the new device price:");
                    try {
                        Device.DeviceBuilder builder =
                                Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                        internalData.daoDevice.updateDevice(builder.price(new BigDecimal(newPrice)).build());
                        internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                    } catch (NumberFormatException e) {
                        internalData.ioStream.printLine(StringLiterals.WRONG_DATA_TEXT);
                    } catch (BuilderNotInitializedException e) {
                        logger.error("Builder in is not initialized! [EditDeviceMenu - price]" + e);
                        internalData.ioStream.printLine(StringLiterals.PROGRAM_ERROR);
                    }
                }),

                new MenuItem("Update production date", () -> {
                    LocalDate newDate = internalData.ioStream.askLocalDate("Enter the new device production date (dd-mm-yyyy with any separator):");
                    Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                    try {
                        internalData.daoDevice.updateDevice(builder.productionDate(newDate).build());
                        internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                    } catch (BuilderNotInitializedException e) {
                        logger.error("Builder in is not initialized! [EditDeviceMenu - production date]" + e);
                        internalData.ioStream.printLine(StringLiterals.PROGRAM_ERROR);
                    }
                }),

                new MenuItem("Delete device", () -> {
                    Device.DeviceBuilder builder = Device.DeviceBuilder.setupFromDevice(internalData.currentDevice);
                    try {
                        internalData.daoDevice.updateDevice(builder.isDeleted(true).build());
                        internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                        Menu.incrementRollback();
                    } catch (BuilderNotInitializedException e) {
                        logger.error("Builder in is not initialized! [EditDeviceMenu - delete]" + e);
                        internalData.ioStream.printLine(StringLiterals.PROGRAM_ERROR);
                    }
                }));
    }
}
