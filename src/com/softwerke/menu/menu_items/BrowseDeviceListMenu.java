package com.softwerke.menu.menu_items;

import com.softwerke.Utils;
import com.softwerke.console.IOPipe;
import com.softwerke.list.DeviceList;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;
import com.softwerke.tables.Color;
import com.softwerke.tables.DeviceType;

import java.time.format.DateTimeParseException;

import static com.softwerke.StringPool.BROWSE_DEVICE_LIST_COMMANDS;
import static com.softwerke.StringPool.WRONG_DATA_TEXT;
import static com.softwerke.menu.menu_items.MenuInternalData.database;
import static com.softwerke.menu.menu_items.MenuInternalData.searchDeviceList;

class BrowseDeviceListMenu extends Menu {
    BrowseDeviceListMenu() {
        /* Browse device list */
        super(new MenuAction[]{
                /* Print current list */
                () -> searchDeviceList.print(),

                /* Apply filter to current list */
                () -> {
                    DeviceList oldDeviceList = searchDeviceList;
                    oldDeviceList.removeIf(x -> x.getId() == -1);
                    while (true) {
                        try {
                            /* Check the list size */
                            /* If list contains 0 or 1 element -> notify and stop filtering */
                            if (Utils.checkListSize(oldDeviceList.size())) break;

                            /* Filter by ID */
                            String[] idFromTo = Utils.readRangeFromConsole(
                                    "Enter ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                    "0",
                                    String.valueOf(oldDeviceList.size()));
                            oldDeviceList = oldDeviceList.maskByDeviceId(idFromTo[0], idFromTo[1]);
                            if (Utils.checkListSize(oldDeviceList.size())) break;           /* Check the list size */

                            /* Filter by production date */
                            String[] dateFromTo = Utils.readRangeFromConsole(
                                    "Enter production date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)",
                                    "01-01-0001",
                                    "31-12-9999");
                            oldDeviceList = oldDeviceList.maskByDeviceProductionDate(dateFromTo[0], dateFromTo[1]);
                            if (Utils.checkListSize(oldDeviceList.size())) break;           /* Check the list size */

                            /* Filter by vendor */
                            String vendorMask = IOPipe.getNotNullLineByDialog("Enter vendor name (or name part) to filter or \"*\" for any vendor.");
                            oldDeviceList = oldDeviceList.maskByDeviceVendor(vendorMask);
                            if (Utils.checkListSize(oldDeviceList.size())) break;           /* Check the list size */

                            /* Filter by model */
                            String modelMask = IOPipe.getNotNullLineByDialog("Enter model name (or name part) to filter or \"*\" for any model.");
                            oldDeviceList = oldDeviceList.maskByDeviceModel(modelMask);
                            if (Utils.checkListSize(oldDeviceList.size())) break;           /* Check the list size */

                            /* Filter by color */
                            Color[] preferredColors = Utils.getEnumArrayFromString(Color.class,
                                    "Enter preferred colors to filter or \"*\" for any color (colors should be separated by any non-character symbol[s]).")
                                    .toArray(new Color[]{});
                            oldDeviceList = oldDeviceList.maskByDeviceColor(preferredColors);
                            if (Utils.checkListSize(oldDeviceList.size())) break;           /* Check the list size */

                            /* Filter by device type */
                            DeviceType[] preferredTypes = Utils.getEnumArrayFromString(DeviceType.class,
                                    "Enter preferred device types to filter or \"*\" for any device type (device types should be separated by any non-character symbol[s]).")
                                    .toArray(new DeviceType[]{});
                            oldDeviceList = oldDeviceList.maskByDeviceType(preferredTypes);
                            if (Utils.checkListSize(oldDeviceList.size())) break;           /* Check the list size */

                            /* Filter by price */
                            String[] priceFromTo = Utils.readRangeFromConsole(
                                    "Enter price range to filter (\"X Y\", \"X\" or \"*\" for any price)",
                                    "0.00",
                                    "999999.99");
                            oldDeviceList = oldDeviceList.maskByDevicePrice(priceFromTo[0], priceFromTo[1]);
                            /* No need to check the list size */
                            break;
                        } catch (DateTimeParseException | NumberFormatException e) {
                            IOPipe.printLine(WRONG_DATA_TEXT);
                        }
                    }
                    /* Update value with a filtered one */
                    Utils.updateList(searchDeviceList, oldDeviceList);
                },

                /* Reset current list */
                () -> Utils.updateList(searchDeviceList, database.getDeviceList()),

                /* Sort current list */
                () -> new SortDeviceListMenu().execute(),
        }, BROWSE_DEVICE_LIST_COMMANDS);
    }
}
