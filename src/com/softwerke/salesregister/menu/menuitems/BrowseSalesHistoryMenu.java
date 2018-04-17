package com.softwerke.salesregister.menu.menuitems;

import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.console.Formatter;
import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.Menu;
import com.softwerke.salesregister.menu.MenuItem;
import com.softwerke.salesregister.tables.DeviceType;
import com.softwerke.salesregister.tables.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

class BrowseSalesHistoryMenu extends Menu {
    BrowseSalesHistoryMenu() {
        /* Browse device list */
        super("-- Browse and search in sales history menu --", new MenuItem[]{
                new MenuItem("Print current list") {
                    @Override
                    public void runItem() {
                        Formatter.printFormatInvoice(internalData.saleList.stream().filter(x -> x.getId() != -1));
                    }
                },

                new MenuItem("Apply filter to current list") {
                    @Override
                    public void runItem() {
                        List<Invoice> invoiceList = new ArrayList<>(internalData.saleList);
                        invoiceList.removeIf(invoice -> invoice.getId() == -1);
                        while (true) {
                            try {
                                /* Check the list size: if it contains 0 or 1 elements -> notify and stop filtering */
                                if (Utils.checkListSize(invoiceList.size())) break;

                                /* Filter by ID */
                                int[] idBounds = Utils.convertToInt(Utils.readRangeFromConsole(
                                        "Enter order ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                        "0",
                                        String.valueOf(Integer.MAX_VALUE)));
                                invoiceList.removeIf(invoice ->
                                        !Utils.isBetween(idBounds[0], invoice.getId(), idBounds[1]));
                                if (Utils.checkListSize(invoiceList.size())) break;         /* Check the list size */

                                /* Filter by invoice date */
                                LocalDate[] dateBounds = Utils.convertToDate(Utils.readRangeFromConsole(
                                        "Enter order date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)",
                                        "01-01-0001",
                                        "31-12-9999"));
                                invoiceList.removeIf(invoice ->
                                        !Utils.isBetween(dateBounds[0], invoice.getDate(), dateBounds[1]));
                                if (Utils.checkListSize(invoiceList.size())) break;         /* Check the list size */

                                /* Filter by total */
                                String[] priceBounds = Utils.readRangeFromConsole(
                                        "Enter price range to filter (\"X Y\", \"X\" or \"*\" for any price)",
                                        "0.00",
                                        "999999.99");
                                invoiceList.removeIf(invoice -> !Utils.isBetween(new BigDecimal(priceBounds[0]),
                                        invoice.getTotalSum(), new BigDecimal(priceBounds[1])));
                                if (Utils.checkListSize(invoiceList.size())) break;         /* Check the list size */

                                /* Filter by person ID */
                                int[] personIdBounds = Utils.convertToInt(Utils.readRangeFromConsole(
                                        "Enter person ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                        "0",
                                        String.valueOf(Integer.MAX_VALUE)));
                                invoiceList.removeIf(invoice -> !Utils.isBetween(personIdBounds[0],
                                        invoice.getPerson().getId(), personIdBounds[1]));
                                if (Utils.checkListSize(invoiceList.size())) break;         /* Check the list size */

                                /* Filter by device ID */
                                int[] deviceIdBounds = Utils.convertToInt(Utils.readRangeFromConsole(
                                        "Enter device ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                        "0",
                                        String.valueOf(Integer.MAX_VALUE)));
                                invoiceList.removeIf(invoice -> invoice.getInvoiceLine().stream()
                                        .noneMatch(device -> Utils.isBetween(deviceIdBounds[0],
                                                device.getDevice().getId(), deviceIdBounds[1])));
                                if (Utils.checkListSize(invoiceList.size())) break;         /* Check the list size */

                                /* Filter by device type */
                                ArrayList<DeviceType> preferredTypes = Utils.getEnumArrayFromString(DeviceType.class,
                                        "Enter preferred device types to filter or \"*\" for any device type " +
                                                "(device types should be separated by any non-character symbol[s], " +
                                                "returns all the sales containing at least one device of entered type).");
                                invoiceList.removeIf(invoice -> invoice.getInvoiceLine().stream()
                                        .noneMatch(devices -> preferredTypes.stream()
                                                .anyMatch(type -> type.equals(devices.getDevice().getDeviceType()))));
                                /* No need to check the list size */
                                break;
                            } catch (DateTimeParseException | NumberFormatException e) {
                                IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                            }
                        }
                        internalData.saleList = invoiceList;
                    }
                },

                new MenuItem("Reset current list") {
                    @Override
                    public void runItem() {
                        internalData.resetPersonList();
                    }
                },

                new MenuItem("Sort current list") {
                    @Override
                    public void runItem() {
                        new SortSalesHistoryMenu().execute();
                    }
                },

                new MenuItem("Print sale details") {
                    @Override
                    public void runItem() {
                        int saleId = IOPipe.getIntegerByDialog("Enter invoice ID for printing:");
                        Invoice invoice = internalData.database.getInvoice(saleId);
                        if (invoice.getId() == -1) IOPipe.printLine(IOPipe.ENTRY_IS_DELETED);
                        else Utils.printReceipt(invoice);
                    }
                }
        });
    }
}
