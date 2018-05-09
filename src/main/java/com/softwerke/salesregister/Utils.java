package com.softwerke.salesregister;

import com.softwerke.salesregister.io.IOStream;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.person.Person;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    private Utils() {
    }

    /**
     * Splits given string in two, related by space.
     *
     * @param valueToSplit      string to be split, may be an {@code *} wildcard character
     * @param defaultLeftValue  default left-value in case of {@code *} input, nullable
     * @param defaultRightValue default right-value in case of {@code *} input, nullable
     * @return the array of two {@code String}'s get from split input value, or from
     * default values, or null if there was not two values separated by a space
     * @throws IllegalArgumentException input string is a null.
     */

    public static String[] splitInTwo(String valueToSplit, String defaultLeftValue, String defaultRightValue) {
        if (StringUtils.isBlank(valueToSplit)) {
            throw new IllegalArgumentException("Given value is null or empty!");
        }
        valueToSplit = StringUtils.strip(valueToSplit);                     /* Remove leading/trailing whitespace */
        if (valueToSplit.equals("*")) {
            return new String[]{defaultLeftValue, defaultRightValue};
        } else {
            String[] splitValue = valueToSplit.split("\\s+");
            switch (splitValue.length) {
                case 2:
                    /* Gained two values: from and to; all okay */
                    return splitValue;
                case 1:
                    /* Gained one value: from and to are the same */
                    return new String[]{splitValue[0], splitValue[0]};
                default:
                    /* Gained something else: input error */
                    return null;
            }
        }
    }

    /**
     * Splits given string in two, related by space.
     *
     * @param valueToConvert string to be split and casted, may be an {@code *} wildcard character
     * @param clazz          enum class to which words will be casted
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

    public static <T> boolean isBetween(Comparable<T> leftLimit, T value, Comparable<T> rightLimit) {
        if (!ObjectUtils.allNotNull(leftLimit, value, rightLimit)) {
            throw new IllegalArgumentException("One or more arguments is null!");
        }
        return rightLimit.compareTo(value) > -1 && leftLimit.compareTo(value) < 1;
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

    /**
     * Parses all the string arguments as {@code LocalDate} values and returns them as array.
     *
     * @param dates strings which will be translated with {@link LocalDate#parse(CharSequence, DateTimeFormatter) LocalDate.parse()}
     *              where each date should have the next representation: {@code d*MM*yyyy}, where {@code *} is any non-digit delimiter
     * @return the {@code LocalDate} array of given strings
     * @throws DateTimeParseException if any of strings is not a valid representation of a LocalDate.
     */
    public static LocalDate[] convertToLocalDate(String... dates) throws DateTimeParseException {
        if (Objects.isNull(dates) || !ObjectUtils.allNotNull((Object[]) dates)) {
            throw new IllegalArgumentException("Arguments is null!");
        }
        return Arrays.stream(dates).map(date -> LocalDate.parse(date.replaceAll("\\D+", "-"),
                DateTimeFormatter.ofPattern("d-MM-yyyy"))).toArray(LocalDate[]::new);
    }

    /**
     * Parses all the string arguments as {@code BigDecimal} values and returns them as array.
     *
     * @param values strings which will be translated with {@link BigDecimal#BigDecimal(String s) BigDecimal::new}
     * @return the {@code BigDecimal} array of given strings
     * @throws NumberFormatException if any of strings is not a valid representation of a BigDecimal.
     */
    public static BigDecimal[] convertToBigDecimal(String... values) throws NumberFormatException {
        if (Objects.isNull(values) || !ObjectUtils.allNotNull((Object[]) values)) {
            throw new IllegalArgumentException("Arguments is null!");
        }
        return Arrays.stream(values).map(BigDecimal::new).toArray(BigDecimal[]::new);
    }

    /**
     * Parses all the string arguments as a signed decimal integers and returns them as primitive array.
     *
     * @param strings strings which will be parsed with {@link Integer#parseInt(String s) Integer.parseInt()}
     * @return the integer array of given strings
     * @throws NumberFormatException if any of strings does not contain a parsable integer.
     */
    public static int[] convertToInt(String... strings) throws NumberFormatException {
        if (Objects.isNull(strings) || !ObjectUtils.allNotNull((Object[]) strings)) {
            throw new IllegalArgumentException("Arguments is null!");
        }
        return Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
    }
}
