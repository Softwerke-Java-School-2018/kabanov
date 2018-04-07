package com.softwerke.masklist;

import com.softwerke.console.ConsolePipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.softwerke.console.ConsolePipe.*;

public class Utils {

    public static String[] readRangeFromConsole(String message, String defaultMinValue, String defaultMaxValue) {
        while (true) {
            String range = getNotNullLineByDialog(message);
            if (range.trim().equals("*")) return new String[]{defaultMinValue, defaultMaxValue};
            else {
                String[] splitRange = range.split("\\s+");
                switch (splitRange.length) {
                    case 2:
                        /* Gained two int: from and to; all okay */
                        return splitRange;
                    case 1:
                        /* Gained one int: from and to are the same */
                        return new String[]{splitRange[0], splitRange[0]};
                    default:
                        /* Gained something else: input error */
                        ConsolePipe.printWithDelay(WRONG_DATA_TEXT);
                }
            }
        }
    }

    public static <T extends Enum<T>> ArrayList<T> getEnumArrayFromString(Class<T> clazz, String message) {
        while (true) {
            /* Getting enum array */
            String enumList = getNotNullLineByDialog(message);
            /* If read asterisk wildcard, return every enumerator */
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
                    ConsolePipe.printWithDelay(WRONG_DATA_TEXT);
                    continue;
                }
            }
        }
    }

    public static boolean checkListSize(int size) {
        /* List contains a lot of elements -> keep filtering */
        if (size > 1) return false;

        /* List contains 0 or 1 element -> notify and stop filtering */
        System.out.println((size == 0) ? LIST_IS_EMPTY_TEXT : LIST_CONTAINS_ONE_ITEM_TEXT);
        return true;
    }

    public static <T> void updateList(ArrayList<T> oldList, ArrayList<T> newList) {
        oldList.clear();
        oldList.addAll(newList);
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
}
