package com.softwerke.salesregister.menu.editlist;

import com.softwerke.salesregister.utils.Utils;
import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.io.Formatter;
import com.softwerke.salesregister.io.Logger;
import com.softwerke.salesregister.io.StringLiterals;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.menu.edititem.EditDeviceMenu;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


public class EditDeviceListMenu extends Menu {
    public EditDeviceListMenu() {
        super("-- Edit device list menu --",
                new MenuItem("Print device list",
                        () -> Formatter.printFormatDevice(internalData.daoDevice.devices()
                                .filter(device -> !device.isDeleted()), internalData.ioStream)),

                new MenuItem("Add device", () -> {
                    String vendor = internalData.ioStream.askNonEmptyString("Enter device manufacturer:");
                    String model = internalData.ioStream.askNonEmptyString("Enter device model:");
                    LocalDate productionDate = internalData.ioStream.askLocalDate("Enter production date (dd/mm/yyyy with any separator):");
                    String price = internalData.ioStream.askNonEmptyString("Enter device price:");
                    Color color;
                    DeviceType type;
                    try {
                        color = Color.valueOf(internalData.ioStream.askNonEmptyString("Enter device color:").toUpperCase());
                        type = DeviceType.valueOf(internalData.ioStream.askNonEmptyString("Enter device type:").toUpperCase());
                        Device.DeviceBuilder builder = new Device.DeviceBuilder()
                                .model(model)
                                .vendor(vendor)
                                .color(color)
                                .productionDate(productionDate)
                                .type(type)
                                .price(new BigDecimal(price))
                                .id(internalData.daoDevice.getSize())
                                .isDeleted(false);
                        internalData.daoDevice.addDevice(builder.build());
                        internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                    } catch (IllegalArgumentException e) {
                        internalData.ioStream.printLine(StringLiterals.WRONG_DATA_TEXT);
                    } catch (BuilderNotInitializedException e) {
                        Logger.error("Builder in is not initialized! [EditDeviceListMenu]" + e);
                        internalData.ioStream.printLine(StringLiterals.PROGRAM_ERROR);
                    }
                }),

                new MenuItem("Edit device", () -> {
                    internalData.currentDevice =
                            Utils.selectDevice(internalData.daoDevice.devices(), internalData.ioStream);
                    if (Objects.isNull(internalData.currentDevice)) {
                        return;
                    }
                    new EditDeviceMenu().execute();
                }));
    }
}