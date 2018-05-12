package com.softwerke.salesregister.menu.filterlist;

import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.device.DeviceType;
import com.softwerke.salesregister.utils.Interval;
import com.softwerke.salesregister.utils.Utils;

import java.math.BigDecimal;
import java.time.chrono.ChronoLocalDate;
import java.util.stream.Stream;

public class FilterInvoiceListMenu extends Menu {
    public FilterInvoiceListMenu() {
        super("-- Filter invoice history menu --",
                new MenuItem("Add filter by ID", () -> {
                    String answer = internalData.ioStream.ask("Enter invoice ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)").trim();
                    if (!"*".equals(answer)) {
                        Interval<Integer> interval = new Interval<Integer>(answer, Integer::new);
                        internalData.invoices = internalData.invoices
                                .filter(invoice -> interval.contains(invoice.getId()));
                    }
                }),

                new MenuItem("Add filter by invoice date", () -> {
                    String answer = internalData.ioStream.ask("Enter invoice date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)").trim();
                    if (!"*".equals(answer)) {
                        Interval<ChronoLocalDate> interval = new Interval<ChronoLocalDate>(answer,
                                Utils::parseStringToLocalDate);
                        internalData.invoices = internalData.invoices
                                .filter(invoice -> interval.contains(invoice.getDate()));
                    }
                }),

                new MenuItem("Add filter by total", () -> {
                    String answer = internalData.ioStream.ask("Enter total range to filter (\"X Y\", \"X\" or \"*\" for any total)").trim();
                    if (!"*".equals(answer)) {
                        Interval<BigDecimal> interval = new Interval<BigDecimal>(answer, BigDecimal::new);
                        internalData.invoices = internalData.invoices
                                .filter(invoice -> interval.contains(invoice.getTotalSum()));
                    }
                }),

                new MenuItem("Add filter by person ID", () -> {
                    String answer = internalData.ioStream.ask("Enter person ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)").trim();
                    if (!"*".equals(answer)) {
                        Interval<Integer> interval = new Interval<Integer>(answer, Integer::new);
                        internalData.invoices = internalData.invoices
                                .filter(invoice -> interval.contains(invoice.getPerson().getId()));
                    }
                }),

                new MenuItem("Add filter by device ID", () -> {
                    String answer = internalData.ioStream.ask("Enter device ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)").trim();
                    if (!"*".equals(answer)) {
                        Interval<Integer> interval = new Interval<Integer>(answer, Integer::new);
                        internalData.invoices = internalData.invoices.filter(invoice -> invoice.getInvoices()
                                .anyMatch(device -> interval.contains(device.getDevice().getId())));
                    }
                }),

                new MenuItem("Add filter by device type", () -> {
                    String answer = internalData.ioStream.ask(
                            "Enter preferred device types to filter or \"*\" for any device type " +
                                    "(device types should be separated by any non-character symbol[s], " +
                                    "returns all the sales containing at least one device of entered type).").trim();
                    Stream<DeviceType> preferredTypes = Utils.parseToEnums(answer, DeviceType.class);
                    internalData.invoices = internalData.invoices.filter(
                            invoice -> invoice.getInvoices().anyMatch(
                                    devices -> preferredTypes.anyMatch(
                                            type -> type.equals(devices.getDevice().getDeviceType()))));
                }));
    }
}
