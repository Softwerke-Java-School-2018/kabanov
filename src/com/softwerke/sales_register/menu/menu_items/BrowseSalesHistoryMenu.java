package com.softwerke.menu.menu_items;

import com.softwerke.Utils;
import com.softwerke.console.IOPipe;
import com.softwerke.list.SaleList;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;
import com.softwerke.tables.DeviceType;
import com.softwerke.tables.Sale;

import java.time.format.DateTimeParseException;

import static com.softwerke.StringPool.BROWSE_SALES_HISTORY_COMMANDS;
import static com.softwerke.StringPool.ENTRY_IS_DELETED;
import static com.softwerke.StringPool.WRONG_DATA_TEXT;
import static com.softwerke.menu.menu_items.MenuInternalData.database;
import static com.softwerke.menu.menu_items.MenuInternalData.searchSalesList;

class BrowseSalesHistoryMenu extends Menu {
    BrowseSalesHistoryMenu() {
        /* Browse device list */
        super(new MenuAction[]{
                /* Print current list */
                () -> searchSalesList.print(),

                /* Apply filter to current list */
                () -> {
                    SaleList oldSaleList = searchSalesList;
                    oldSaleList.removeIf(x -> x.getId() == -1);
                    while (true) {
                        try {
                            /* Check the list size: if it contains 0 or 1 elements -> notify and stop filtering */
                            if (Utils.checkListSize(oldSaleList.size())) break;

                            /* Filter by ID */
                            String[] idFromTo = Utils.readRangeFromConsole(
                                    "Enter order ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                    "0",
                                    String.valueOf(oldSaleList.size()));
                            oldSaleList = oldSaleList.maskBySaleId(idFromTo[0], idFromTo[1]);
                            if (Utils.checkListSize(oldSaleList.size())) break;         /* Check the list size */

                            /* Filter by sale date */
                            String[] dateFromTo = Utils.readRangeFromConsole(
                                    "Enter order date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)",
                                    "01-01-0001",
                                    "31-12-9999");
                            oldSaleList = oldSaleList.maskBySaleDate(dateFromTo[0], dateFromTo[1]);
                            if (Utils.checkListSize(oldSaleList.size())) break;         /* Check the list size */

                            /* Filter by total */
                            String[] priceFromTo = Utils.readRangeFromConsole(
                                    "Enter price range to filter (\"X Y\", \"X\" or \"*\" for any price)",
                                    "0.00",
                                    "999999.99");
                            oldSaleList = oldSaleList.maskByTotalSum(priceFromTo[0], priceFromTo[1]);
                            if (Utils.checkListSize(oldSaleList.size())) break;         /* Check the list size */

                            /* Filter by person ID */
                            String[] idFromToPerson = Utils.readRangeFromConsole(
                                    "Enter person ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                    "0",
                                    String.valueOf(oldSaleList.size()));
                            oldSaleList = oldSaleList.maskByPersonId(idFromToPerson[0], idFromToPerson[1]);
                            if (Utils.checkListSize(oldSaleList.size())) break;         /* Check the list size */

                            /* Filter by device ID */
                            String[] idFromToDevice = Utils.readRangeFromConsole(
                                    "Enter device ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                    "0",
                                    String.valueOf(oldSaleList.size()));
                            oldSaleList = oldSaleList.maskByDeviceId(idFromToDevice[0], idFromToDevice[1]);
                            if (Utils.checkListSize(oldSaleList.size())) break;         /* Check the list size */

                            /* Filter by device type */
                            DeviceType[] preferredTypes = Utils.getEnumArrayFromString(DeviceType.class,
                                    "Enter preferred device types to filter or \"*\" for any device type " +
                                            "(device types should be separated by any non-character symbol[s], " +
                                            "returns all the sales containing at least one device of entered type).")
                                    .toArray(new DeviceType[]{});
                            oldSaleList = oldSaleList.maskByDeviceType(preferredTypes);
                            /* No need to check the list size */
                            break;
                        } catch (DateTimeParseException | NumberFormatException e) {
                            IOPipe.printLine(WRONG_DATA_TEXT);
                        }
                    }
                    Utils.updateList(searchSalesList, oldSaleList);                     /* Update value with a filtered one */
                },

                /* Reset current list */
                () -> Utils.updateList(searchSalesList, database.getSalesHistory()),

                /* Sort current list */
                () -> new SortSalesHistoryMenu().execute(),

                /* Print sale details */
                () -> {
                    int saleId = IOPipe.getIntegerByDialog("Enter sale ID for printing:");
                    Sale sale = database.getSalesHistory().get(saleId);
                    if (sale.getId() == -1) IOPipe.printLine(ENTRY_IS_DELETED);
                    else sale.printReceipt();
                },
        }, BROWSE_SALES_HISTORY_COMMANDS);
    }
}