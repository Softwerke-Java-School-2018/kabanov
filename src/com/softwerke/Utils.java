package com.softwerke;

import com.softwerke.console.IOPipe;
import com.softwerke.list.PersonList;
import com.softwerke.tables.Person;
import com.softwerke.tables.SeveralDevices;

import java.math.BigDecimal;
import java.util.*;

import static com.softwerke.StringPool.*;
import static com.softwerke.console.IOPipe.getNotNullLineByDialog;

public class Utils {

    public static String[] readRangeFromConsole(String message, String defaultMinValue, String defaultMaxValue) {
        while (true) {
            String range = getNotNullLineByDialog(message);
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
                        IOPipe.printLine(WRONG_DATA_TEXT);
                }
            }
        }
    }

    public static <T extends Enum<T>> ArrayList<T> getEnumArrayFromString(Class<T> clazz, String message) {
        while (true) {
            /* Getting enum array */
            String enumList = getNotNullLineByDialog(message);
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
                    IOPipe.printLine(WRONG_DATA_TEXT);
                    // continue;
                }
            }
        }
    }

    public static boolean checkListSize(int size) {
        /* List contains a lot of elements -> keep filtering */
        if (size > 1) return false;

        /* List contains 0 or 1 element -> notify and stop filtering */
        IOPipe.printLine((size == 0) ? LIST_IS_EMPTY_TEXT : LIST_CONTAINS_ONE_ITEM_TEXT);
        return true;
    }

    public static <T> void updateList(ArrayList<T> oldList, ArrayList<T> newList) {
        oldList.clear();
        oldList.addAll(newList);
    }

    public static <T> void updateList(ArrayList<T> oldList, T value) {
        oldList.clear();
        oldList.add(value);
    }

    public static boolean isInteger(String s) {
        Scanner sc = new Scanner(s.trim());
        if (!sc.hasNextInt()) return false;
        // we know it starts with a valid character, now make sure
        // there's nothing left:
        sc.nextInt();
        return !sc.hasNext();
    }

    public static String leftPad(String text, int length) {
        return String.format("%1$" + length + "s", text);
    }

    public static String rightPad(String text, int length) {
        return String.format("%1$-" + length + "s", text);
    }

    public static <T> boolean inBetween(Comparable<T> leftLimit, T value, Comparable<T> rightLimit) {
        return rightLimit.compareTo(value) > -1 && leftLimit.compareTo(value) < 1;
    }

    public static Person selectPerson(PersonList personList) {
        while (true) {
            int personListSize = personList.getNotDeletedItemsNumber();
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
                    if (Utils.isInteger(personIdOrName)) {
                        try {
                            int readId = Integer.parseInt(personIdOrName);
                            Person person = personList.get(readId);
                            if (readId != person.getId()) {
                                /* ID belongs to the deleted person */
                                IOPipe.printLine(ENTRY_IS_DELETED);
                                return null;
                            }
                            Utils.updateList(personList, person);
                            continue;
                        } catch (IndexOutOfBoundsException e) {
                            IOPipe.printLine(WRONG_DATA_TEXT);
                            return null;
                        }
                    }
                    PersonList searchByFirstName = personList.clone().maskByPersonFirstName(personIdOrName);
                    PersonList searchByLastName = personList.clone().maskByPersonLastName(personIdOrName);
                    searchByFirstName.merge(searchByLastName);
                    Utils.updateList(personList, searchByFirstName);
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
}
