package com.softwerke.salesregister.utils;

import com.softwerke.salesregister.io.IOStream;
import com.softwerke.salesregister.io.StringLiterals;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.person.Person;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    private Utils() {
    }

    public static int centerIndexOf(String string, char ch) {
        if (Objects.isNull(string) || string.length() == 0) {
            throw new IllegalArgumentException(StringLiterals.NULL_ARG_EXC);
        }
        int centerIndex = (string.length() - 1) / 2;
        if (string.charAt(centerIndex) == ch) {
            return centerIndex;
        }
        for (int i = 1; i + centerIndex < string.length(); i++) {
            if (string.charAt(centerIndex + i) == ch) {
                return centerIndex + i;
            }
            if (centerIndex - i >= 0 && string.charAt(centerIndex - i) == ch) {
                return centerIndex - i;
            }
        }
        return -1;
    }



    /**
     * Converts given string to stream of instances of {@code clazz} enum.
     *
     * @param valueToConvert string to be split and casted, may be an {@code *} wildcard character
     * @param clazz          enum class to which names will be casted
     * @return stream of enum instances of class {@code clazz}
     * @throws IllegalArgumentException input string is a null.
     */
    public static <T extends Enum<T>> Stream<T> parseToEnums(String valueToConvert, Class<T> clazz) {
        if (StringUtils.isBlank(valueToConvert) || Objects.isNull(clazz)) {
            throw new IllegalArgumentException("Given value is null or empty!");
        }
        valueToConvert = StringUtils.strip(valueToConvert);                 /* Remove leading/trailing whitespace */
        /* If line is an asterisk wildcard, return all the enumerators */
        if (valueToConvert.equals("*")) {
            return Arrays.stream(clazz.getEnumConstants());
        } else {
            /* Otherwise - split and cast */
            String[] splitEnumList = valueToConvert.toUpperCase().split("[^A-Z]+");
            return Arrays.stream(splitEnumList).map(enumName -> T.valueOf(clazz, enumName));
        }
    }

    /**
     * Filters given {@code Stream} of {@code Person}'s by read
     * parameters from IO until there is one or zero elements left.
     *
     * @param personStream stream of elements, which will be cured inside as {@code List}
     * @return the {@code Person} matching required name/ID or null if there is no such element
     */
    public static Person selectPerson(Stream<Person> personStream, IOStream source) {
        if (!ObjectUtils.allNotNull(personStream, source)) {
            throw new IllegalArgumentException("One or more arguments is null!");
        }
        List<Person> personList = personStream.filter(person -> !person.isDeleted()).collect(Collectors.toList());
        while (true) {
            int personListSize = personList.size();
            switch (personListSize) {
                case 0:
                    source.printLine("Person list is empty. Nothing to choose from.");
                    return null;
                case 1:
                    Person tempPerson = personList.get(0);
                    source.printLine("Found one person: " + tempPerson);
                    return tempPerson;
                default:
                    source.printLine("Found " + personList.size() + " persons.");
                    String personIdOrName = source.askNonEmptyString("Enter person ID or name part:");
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

    /**
     * Filters given {@code Stream} of {@code Device}'s by read
     * parameters from IO until there is one or zero elements left.
     *
     * @param deviceStream stream of elements, which will be cured inside as {@code List}
     * @return the {@code Device} matching required name/ID or null if there is no such element
     */

    public static Device selectDevice(Stream<Device> deviceStream, IOStream source) {
        if (!ObjectUtils.allNotNull(deviceStream, source)) {
            throw new IllegalArgumentException("One or more arguments is null!");
        }
        List<Device> deviceList = deviceStream.filter(device -> !device.isDeleted())
                .collect(Collectors.toList());
        while (true) {
            int personListSize = deviceList.size();
            switch (personListSize) {
                case 0:
                    source.printLine("Person list is empty. Nothing to choose from.");
                    return null;
                case 1:
                    Device tempDevice = deviceList.get(0);
                    source.printLine("Found one person: " + tempDevice);
                    return tempDevice;
                default:
                    source.printLine("Found " + deviceList.size() + " persons.");
                    String deviceIdOrName = source.askNonEmptyString("Enter device ID or vendor / model name part:");
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
}
