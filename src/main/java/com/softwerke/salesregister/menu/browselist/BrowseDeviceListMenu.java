package com.softwerke.salesregister.menu.browselist;

import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.console.Formatter;
import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.menu.sortlist.SortDeviceListMenu;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class BrowseDeviceListMenu extends Menu {
    public BrowseDeviceListMenu() {
        /* Browse device list */
        super("-- Browse and search in device list menu --", new MenuItem[]{
                new MenuItem("Print current list") {
                    @Override
                    public void runItem() {
                        Formatter.printFormatDevice(internalData.deviceList.stream().filter(x -> x.getId() != -1));
                    }
                },

                new MenuItem("Apply filter to current list") {
                    @Override
                    public void runItem() {
                        List<Device> deviceList = new ArrayList<>(internalData.deviceList);
                        deviceList.removeIf(device -> device.getId() == -1);
                        while (true) {
                            try {
                                /* Check the list size: if it contains 0 or 1 elements -> notify and stop filtering */
                                if (Utils.checkListSize(deviceList.size())) break;

                                /* Filter by ID */
                                int[] idBounds = Utils.convertToInt(Utils.readRangeFromConsole(
                                        "Enter ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                        "0",
                                        String.valueOf(Integer.MAX_VALUE)));
                                deviceList.removeIf(device ->
                                        !Utils.isBetween(idBounds[0], device.getId(), idBounds[1]));
                                if (Utils.checkListSize(deviceList.size())) break;       /* Check the list size */

                                /* Filter by production date */
                                LocalDate[] dateBounds = Utils.convertToDate(Utils.readRangeFromConsole(
                                        "Enter production date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)",
                                        "01-01-0001",
                                        "31-12-9999"));
                                deviceList.removeIf(device ->
                                        !Utils.isBetween(dateBounds[0], device.getProductionDate(), dateBounds[1]));
                                if (Utils.checkListSize(deviceList.size())) break;       /* Check the list size */

                                /* Filter by vendor */
                                String vendorMask = IOPipe.getNotNullLineByDialog("Enter vendor name (or name part) to filter or \"*\" for any vendor.")
                                        .toLowerCase();
                                deviceList.removeIf(device ->
                                        !("*".equals(vendorMask) || device.getVendorLowerCase().contains(vendorMask)));
                                if (Utils.checkListSize(deviceList.size())) break;       /* Check the list size */

                                /* Filter by model */
                                String modelMask = IOPipe.getNotNullLineByDialog("Enter model name (or name part) to filter or \"*\" for any model.")
                                        .toLowerCase();
                                deviceList.removeIf(device ->
                                        !("*".equals(modelMask) || device.getModelLowerCase().contains(modelMask)));
                                if (Utils.checkListSize(deviceList.size())) break;       /* Check the list size */

                                /* Filter by color */
                                ArrayList<Color> preferredColors = Utils.getEnumArrayFromString(Color.class,
                                        "Enter preferred colors to filter or \"*\" for any color (colors should be separated by any non-character symbol[s]).");
                                deviceList.removeIf(device -> preferredColors.stream()
                                        .noneMatch(color -> color.equals(device.getColor())));
                                if (Utils.checkListSize(deviceList.size())) break;       /* Check the list size */

                                /* Filter by device type */
                                ArrayList<DeviceType> preferredTypes = Utils.getEnumArrayFromString(DeviceType.class,
                                        "Enter preferred device types to filter or \"*\" for any device type (device types should be separated by any non-character symbol[s]).");
                                deviceList.removeIf(device -> preferredTypes.stream()
                                        .noneMatch(color -> color.equals(device.getDeviceType())));
                                if (Utils.checkListSize(deviceList.size())) break;       /* Check the list size */

                                /* Filter by price */
                                BigDecimal[] priceBounds = Utils.convertToBigDecimal(Utils.readRangeFromConsole(
                                        "Enter price range to filter (\"X Y\", \"X\" or \"*\" for any price)",
                                        "0.00",
                                        "99999999.99"));
                                deviceList.removeIf(device ->
                                        !Utils.isBetween(priceBounds[0], device.getPrice(), priceBounds[1]));
                                /* No need to check the list size */
                                break;
                            } catch (DateTimeParseException | NumberFormatException e) {
                                IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                            }
                        }
                        internalData.deviceList = deviceList;
                    }
                },

                new MenuItem("Reset current list") {
                    @Override
                    public void runItem() {
                        internalData.resetDeviceList();
                    }
                },

                new MenuItem("Sort current list") {
                    @Override
                    public void runItem() {
                        new SortDeviceListMenu().execute();
                    }
                },
        });
    }
}
