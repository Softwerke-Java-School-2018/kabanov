package com.softwerke.salesregister.menu.filterlist;

import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.device.DeviceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

public class FilterInvoiceListMenu extends Menu {
    public FilterInvoiceListMenu() {
        super("-- Filter invoice history menu --",
                new MenuItem("Filter by ID", () -> {
                    String answer = internalData.ioStream.ask("Enter invoice ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)");
                    String[] splitAnswer = Utils.splitInTwo(answer, "0", String.valueOf(Integer.MAX_VALUE));
                    int[] bounds = Utils.convertToInt(splitAnswer);
                    internalData.invoices = internalData.invoices.filter(
                            invoice -> Utils.isBetween(bounds[0], invoice.getId(), bounds[1]));
                }),

                new MenuItem("Filter by invoice date", () -> {
                    String answer = internalData.ioStream.ask("Enter invoice date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)");
                    String[] splitAnswer = Utils.splitInTwo(answer, "01-01-0001", "31-12-9999");
                    LocalDate[] bounds = Utils.convertToLocalDate(splitAnswer);
                    internalData.invoices = internalData.invoices.filter(
                            invoice -> Utils.isBetween(bounds[0], invoice.getDate(), bounds[1]));
                }),

                new MenuItem("Filter by total", () -> {
                    String answer = internalData.ioStream.ask("Enter total range to filter (\"X Y\", \"X\" or \"*\" for any total)");
                    String[] splitAnswer = Utils.splitInTwo(answer, "0.00", "99999999.99");
                    BigDecimal[] bounds = Utils.convertToBigDecimal(splitAnswer);
                    internalData.invoices = internalData.invoices.filter(
                            invoice -> Utils.isBetween(bounds[0], invoice.getTotalSum(), bounds[1]));
                }),

                new MenuItem("Filter by person ID", () -> {
                    String answer = internalData.ioStream.ask("Enter person ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)");
                    String[] splitAnswer = Utils.splitInTwo(answer, "0", String.valueOf(Integer.MAX_VALUE));
                    int[] bounds = Utils.convertToInt(splitAnswer);
                    internalData.invoices = internalData.invoices.filter(
                            invoice -> Utils.isBetween(bounds[0], invoice.getPerson().getId(), bounds[1]));
                }),

                new MenuItem("Filter by device ID", () -> {
                    String answer = internalData.ioStream.ask("Enter device ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)");
                    String[] splitAnswer = Utils.splitInTwo(answer, "0", String.valueOf(Integer.MAX_VALUE));
                    int[] bounds = Utils.convertToInt(splitAnswer);
                    internalData.invoices = internalData.invoices.filter(invoice -> invoice.getInvoices().anyMatch(
                            device -> Utils.isBetween(bounds[0], device.getDevice().getId(), bounds[1])));
                }),

                new MenuItem("Filter by device type", () -> {
                    String answer = internalData.ioStream.ask(
                            "Enter preferred device types to filter or \"*\" for any device type " +
                                    "(device types should be separated by any non-character symbol[s], " +
                                    "returns all the sales containing at least one device of entered type).");
                    Stream<DeviceType> preferredTypes = Utils.convertToEnumInstances(answer, DeviceType.class);
                    internalData.invoices = internalData.invoices.filter(
                            invoice -> invoice.getInvoices().anyMatch(
                                    devices -> preferredTypes.anyMatch(
                                            type -> type.equals(devices.getDevice().getDeviceType()))));
                }));
    }
}
