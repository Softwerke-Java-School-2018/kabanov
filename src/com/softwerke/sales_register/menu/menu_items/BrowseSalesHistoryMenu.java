package com.softwerke.menu.menu_items;

import com.softwerke.Utils;
import com.softwerke.console.Formatter;
import com.softwerke.console.IOPipe;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuItem;
import com.softwerke.tables.DeviceType;
import com.softwerke.tables.Sale;

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
                        Formatter.printFormatSale(internalData.saleList.stream().filter(x -> x.getId() != -1));
                    }
                },

                new MenuItem("Apply filter to current list") {
                    @Override
                    public void runItem() {
                        List<Sale> saleList = new ArrayList<>(internalData.saleList);
                        saleList.removeIf(sale -> sale.getId() == -1);
                        while (true) {
                            try {
                                /* Check the list size: if it contains 0 or 1 elements -> notify and stop filtering */
                                if (Utils.checkListSize(saleList.size())) break;

                                /* Filter by ID */
                                int[] idBounds = Utils.convertToInt(Utils.readRangeFromConsole(
                                        "Enter order ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                        "0",
                                        String.valueOf(Integer.MAX_VALUE)));
                                saleList.removeIf(sale ->
                                        !Utils.isBetween(idBounds[0], sale.getId(), idBounds[1]));
                                if (Utils.checkListSize(saleList.size())) break;         /* Check the list size */

                                /* Filter by sale date */
                                LocalDate[] dateBounds = Utils.convertToDate(Utils.readRangeFromConsole(
                                        "Enter order date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)",
                                        "01-01-0001",
                                        "31-12-9999"));
                                saleList.removeIf(sale ->
                                        !Utils.isBetween(dateBounds[0], sale.getSaleDate(), dateBounds[1]));
                                if (Utils.checkListSize(saleList.size())) break;         /* Check the list size */

                                /* Filter by total */
                                String[] priceBounds = Utils.readRangeFromConsole(
                                        "Enter price range to filter (\"X Y\", \"X\" or \"*\" for any price)",
                                        "0.00",
                                        "999999.99");
                                saleList.removeIf(sale -> !Utils.isBetween(new BigDecimal(priceBounds[0]),
                                        sale.getTotalSum(), new BigDecimal(priceBounds[1])));
                                if (Utils.checkListSize(saleList.size())) break;         /* Check the list size */

                                /* Filter by person ID */
                                int[] personIdBounds = Utils.convertToInt(Utils.readRangeFromConsole(
                                        "Enter person ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                        "0",
                                        String.valueOf(Integer.MAX_VALUE)));
                                saleList.removeIf(sale -> !Utils.isBetween(personIdBounds[0],
                                        sale.getPerson().getId(), personIdBounds[1]));
                                if (Utils.checkListSize(saleList.size())) break;         /* Check the list size */

                                /* Filter by device ID */
                                int[] deviceIdBounds = Utils.convertToInt(Utils.readRangeFromConsole(
                                        "Enter device ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                        "0",
                                        String.valueOf(Integer.MAX_VALUE)));
                                saleList.removeIf(sale -> sale.getSeveralDevices().stream()
                                        .noneMatch(device -> Utils.isBetween(deviceIdBounds[0],
                                                device.getDevice().getId(), deviceIdBounds[1])));
                                if (Utils.checkListSize(saleList.size())) break;         /* Check the list size */

                                /* Filter by device type */
                                ArrayList<DeviceType> preferredTypes = Utils.getEnumArrayFromString(DeviceType.class,
                                        "Enter preferred device types to filter or \"*\" for any device type " +
                                                "(device types should be separated by any non-character symbol[s], " +
                                                "returns all the sales containing at least one device of entered type).");
                                saleList.removeIf(sale -> sale.getSeveralDevices().stream()
                                        .noneMatch(devices -> preferredTypes.stream()
                                                .anyMatch(type -> type.equals(devices.getDevice().getDeviceType()))));
                                /* No need to check the list size */
                                break;
                            } catch (DateTimeParseException | NumberFormatException e) {
                                IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                            }
                        }
                        internalData.saleList = saleList;
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
                        int saleId = IOPipe.getIntegerByDialog("Enter sale ID for printing:");
                        Sale sale = internalData.database.getSale(saleId);
                        if (sale.getId() == -1) IOPipe.printLine(IOPipe.ENTRY_IS_DELETED);
                        else Utils.printReceipt(sale);
                    }
                }
        });
    }
}
