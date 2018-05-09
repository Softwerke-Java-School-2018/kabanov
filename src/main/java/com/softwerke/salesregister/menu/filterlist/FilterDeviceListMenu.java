package com.softwerke.salesregister.menu.filterlist;

import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.DeviceType;
import com.softwerke.salesregister.utils.Interval;
import com.softwerke.salesregister.utils.Utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class FilterDeviceListMenu extends Menu {
    public FilterDeviceListMenu() {
        super("-- Filter device list menu --",
                new MenuItem("Add filter by ID", () -> {
                    String answer = internalData.ioStream.ask("Enter device ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)").trim();
                    if (!"*".equals(answer)) {
                        Interval<Integer> interval = new Interval<Integer>(answer, Integer::new);
                        internalData.devices = internalData.devices
                                .filter(device -> interval.contains(device.getId()));
                    }
                }),

                new MenuItem("Add filter by production date", () -> {
                    String answer = internalData.ioStream.ask("Enter production date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)").trim();
                    if (!"*".equals(answer)) {
                        Interval<ChronoLocalDate> interval = new Interval<ChronoLocalDate>(answer,
                                string -> LocalDate.parse(string.replaceAll("\\D+", "-"),
                                        DateTimeFormatter.ofPattern("d-MM-yyyy")));
                        internalData.devices = internalData.devices
                                .filter(device -> interval.contains(device.getProductionDate()));
                    }
                }),

                new MenuItem("Add filter by vendor", () -> {
                    String vendorMask = internalData.ioStream.askNonEmptyString("Enter vendor name (or name part) to filter or \"*\" for any vendor.")
                            .trim().toLowerCase();
                    internalData.devices = internalData.devices.filter(
                            device -> ("*".equals(vendorMask) || device.getVendorLowerCase().contains(vendorMask)));
                }),

                new MenuItem("Add filter by model", () -> {
                    String modelMask = internalData.ioStream.askNonEmptyString("Enter model name (or name part) to filter or \"*\" for any model.")
                            .trim().toLowerCase();
                    internalData.devices = internalData.devices.filter(
                            device -> ("*".equals(modelMask) || device.getVendorLowerCase().contains(modelMask)));
                }),

                new MenuItem("Add filter by price", () -> {
                    String answer = internalData.ioStream.ask("Enter price range to filter (\"X Y\", \"X\" or \"*\" for any price)").trim();
                    if (!"*".equals(answer)) {
                        Interval<BigDecimal> interval = new Interval<BigDecimal>(answer, BigDecimal::new);
                        internalData.devices = internalData.devices
                                .filter(device -> interval.contains(device.getPrice()));
                    }
                }),

                new MenuItem("Add filter by color", () -> {
                    String answer = internalData.ioStream.ask(
                            "Enter preferred colors to filter or \"*\" for any color " +
                                    "(colors should be separated by any non-character symbol[s].");
                    Stream<Color> preferredColors = Utils.parseToEnums(answer, Color.class);
                    internalData.devices = internalData.devices.filter(
                            device -> preferredColors.anyMatch(color -> color.equals(device.getColor())));
                }),

                new MenuItem("Add filter by type", () -> {
                    String answer = internalData.ioStream.ask(
                            "Enter preferred device types to filter or \"*\" for any device type " +
                                    "(device types should be separated by any non-character symbol[s].");
                    Stream<DeviceType> preferredTypes = Utils.parseToEnums(answer, DeviceType.class);
                    internalData.devices = internalData.devices.filter(
                            device -> preferredTypes.anyMatch(type -> type.equals(device.getDeviceType())));
                }));
    }
}
