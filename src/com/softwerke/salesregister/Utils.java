package com.softwerke;

import com.softwerke.console.IOPipe;
import com.softwerke.tables.Device;
import com.softwerke.tables.Person;
import com.softwerke.tables.Sale;
import com.softwerke.tables.SeveralDevices;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static String[] readRangeFromConsole(String message, String defaultMinValue, String defaultMaxValue) {
        while (true) {
            String range = IOPipe.getNotNullLineByDialog(message);
            if (range.trim().equals("*")) return new String[]{defaultMinValue, defaultMaxValue};
            else {
                String[] splitRange = range.split("\\s+");
                switch (splitRange.length) {
                    case 2:
                        /* Gained two values: from and to; all okay */
                        return splitRange;
                    case 1:
                        /* Gained one value: from and to are the same */
                        return new String[]{splitRange[0], splitRange[0]};
                    default:
                        /* Gained something else: input error */
                        IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                }
            }
        }
    }

    public static <T extends Enum<T>> ArrayList<T> getEnumArrayFromString(Class<T> clazz, String message) {
        while (true) {
            /* Getting enum array */
            String enumList = IOPipe.getNotNullLineByDialog(message);
            /* If read line is an asterisk wildcard, return all the enumerators */
            if (enumList.trim().equals("*")) return new ArrayList<>(Arrays.asList(clazz.getEnumConstants()));
            else {
                /* Otherwise - split and cast */
                String[] splitEnumList = enumList.toUpperCase().split("[^A-Z]+");
                ArrayList<T> ret = new ArrayList<>();

                try {
                    for (String enumName : splitEnumList)
                        ret.add(T.valueOf(clazz, enumName));
                    return ret;
                } catch (IllegalArgumentException e) {
                    IOPipe.printLine(IOPipe.WRONG_DATA_TEXT);
                    // continue;
                }
            }
        }
    }

    public static boolean checkListSize(long size) {
        /* List contains a lot of elements -> keep filtering */
        if (size > 1) return false;

        /* List contains 0 or 1 element -> notify and stop filtering */
        IOPipe.printLine((size == 0) ? IOPipe.LIST_IS_EMPTY_TEXT : IOPipe.LIST_CONTAINS_ONE_ITEM_TEXT);
        return true;
    }

    public static String leftPad(String text, int length) {
        return String.format("%1$" + length + "s", text);
    }

    public static String rightPad(String text, int length) {
        return String.format("%1$-" + length + "s", text);
    }

    public static <T> boolean isBetween(Comparable<T> leftLimit, T value, Comparable<T> rightLimit) {
        return rightLimit.compareTo(value) > -1 && leftLimit.compareTo(value) < 1;
    }

    public static Person selectPerson(Stream<Person> personStream) {
        ArrayList<Person> personList = personStream.filter(person -> person.getId() != -1)
                .collect(Collectors.toCollection(ArrayList::new));
        while (true) {
            int personListSize = personList.size();
            switch (personListSize) {
                case 0:
                    IOPipe.printLine("Person list is empty. Nothing to choose from.");
                    return null;
                case 1:
                    Person tempPerson = personList.get(0);
                    IOPipe.printLine("Found one person: " + tempPerson);
                    return tempPerson;
                default:
                    IOPipe.printLine("Found " + personList.size() + " persons.");
                    String personIdOrName = IOPipe.getNotNullLineByDialog("Enter person ID or name part:");
                    try {
                        personList.removeIf(person -> person.getId() != Integer.parseInt(personIdOrName));
                    } catch (NumberFormatException e) {
                        personList.removeIf(person ->
                                !person.getFirstNameLowerCase().contains(personIdOrName) &&
                                        !person.getLastNameLowerCase().contains(personIdOrName));
                    }
            }
        }
    }

    public static Device selectDevice(Stream<Device> deviceStream) {
        ArrayList<Device> deviceList = deviceStream.filter(device -> device.getId() != -1)
                .collect(Collectors.toCollection(ArrayList::new));
        while (true) {
            int personListSize = deviceList.size();
            switch (personListSize) {
                case 0:
                    IOPipe.printLine("Person list is empty. Nothing to choose from.");
                    return null;
                case 1:
                    Device tempDevice = deviceList.get(0);
                    IOPipe.printLine("Found one person: " + tempDevice);
                    return tempDevice;
                default:
                    IOPipe.printLine("Found " + deviceList.size() + " persons.");
                    String deviceIdOrName = IOPipe.getNotNullLineByDialog("Enter device ID or vendor / model name part:");
                    try {
                        deviceList.removeIf(person -> person.getId() != Integer.parseInt(deviceIdOrName));
                    } catch (NumberFormatException e) {
                        deviceList.removeIf(device ->
                                !device.getModelLowerCase().contains(deviceIdOrName) &&
                                        !device.getVendorLowerCase().contains(deviceIdOrName));
                    }
            }
        }
    }

    public static void printShopList(Collection<SeveralDevices> orderItems) {
        if (orderItems.isEmpty()) {
            IOPipe.printLine("Shop list is empty.");
            return;
        }
        IOPipe.printLine("            Items            | Amount |   Total");
        IOPipe.printLine("--------------------------------------------------");
        BigDecimal total = BigDecimal.ZERO;
        for (SeveralDevices severalDevices : orderItems) {
            total = total.add(severalDevices.getInternalSum());
            String formattedName = Utils.rightPad(severalDevices.getDevice().toString(), 29);
            String formattedAmount = Utils.leftPad(String.valueOf(severalDevices.getAmount()), 7);
            String formattedInternalSum = Utils.leftPad(severalDevices.getInternalSum().toString(), 11);
            IOPipe.printLine(formattedName + "|" + formattedAmount + " |" + formattedInternalSum);
        }
        String formattedTotal = Utils.leftPad(total.toString(), 43);
        IOPipe.printLine(" Total:" + formattedTotal);
        IOPipe.printLine();
    }

    public static LocalDate[] convertToDate(String... dates) {
        LocalDate[] returning = new LocalDate[dates.length];
        for (int i = 0; i < dates.length; i++)
            returning[i] = LocalDate.parse(dates[i].replaceAll("\\D+", "-"),
                    DateTimeFormatter.ofPattern("d-MM-yyyy"));
        return returning;
    }

    public static int[] convertToInt(String... ints) {
        int[] returning = new int[ints.length];
        for (int i = 0; i < ints.length; i++)
            returning[i] = Integer.parseInt(ints[i]);
        return returning;
    }

    public static BigDecimal[] convertToBigDecimal(String... bigDecimals) {
        BigDecimal[] returning = new BigDecimal[bigDecimals.length];
        for (int i = 0; i < bigDecimals.length; i++)
            returning[i] = new BigDecimal(bigDecimals[i]);
        return returning;
    }

    public static void printReceipt(Sale sale) {
        IOPipe.printLine(" Shopping date: " + sale.getSaleDate());
        IOPipe.printLine(" Customer name: " + sale.getPerson());
        Utils.printShopList(sale.getSeveralDevices());
    }
}
