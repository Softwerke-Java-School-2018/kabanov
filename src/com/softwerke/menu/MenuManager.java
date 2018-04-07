package com.softwerke.menu;

import com.softwerke.console.ConsolePipe;
import com.softwerke.masklist.DeviceList;
import com.softwerke.masklist.PersonList;
import com.softwerke.masklist.SortingOrder;
import com.softwerke.masklist.Utils;
import com.softwerke.masklist.comparators.*;
import com.softwerke.tables.Color;
import com.softwerke.tables.Database;
import com.softwerke.tables.DeviceType;
import com.softwerke.tables.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.softwerke.console.ConsolePipe.*;
import static com.softwerke.masklist.SortingOrder.ASCENDING;
import static com.softwerke.masklist.SortingOrder.DESCENDING;
import static com.softwerke.masklist.Utils.isInteger;
import static com.softwerke.masklist.Utils.updateList;

public class MenuManager {

    private Database database;
    private final String[] mainCommands = {
            "-- Main menu --",
            "Select the command:",
            " 1) Sell device [stub]",
            " 2) Edit device list",
            " 3) Edit person list",
            " 4) Delete sell from history [stub]",
            " 5) Browse device list",
            " 6) Browse person list [stub]",
            " 7) Browse sales history [stub]",
            " 0) Exit"};
    private final String[] editDeviceListCommands = {
            "-- Edit device list menu --",
            " 1) Add device [stub]",
            " 2) Edit device [stub]",
            " 3) Delete device [stub]",
            " 0) Return"};
    private final String[] editPersonCommands = {
            "-- Edit person menu --",
            " 1) Update first name",
            " 2) Update last name",
            " 3) Update birth date",
            " 0) Return"};
    private final String[] editPersonListCommands = {
            "-- Edit person list menu --",
            " 1) Print person list",
            " 2) Add person",
            " 3) Edit person",
            " 4) Delete person [stub]",
            " 0) Return"};
    private final String[] browseDeviceListCommands = {
            "-- Browse and search in device list menu --",
            " 1) Print current list",
            " 2) Apply filter to current list",
            " 3) Reset current list",
            " 4) Sort list",
            " 0) Return"};
    private final String[] sortDeviceListCommands = {
            "-- Sort device list menu --",
            " 1) Sort by ID",
            " 2) Sort by production date",
            " 3) Sort by manufacturer",
            " 4) Sort by model",
            " 5) Sort by color",
            " 6) Sort by device type",
            " 7) Sort by price",
            " 0) Return"};

    public void start() {
        database = new Database();
        System.out.println("Hello, %username%.");

        /* Edit device list menu */
        MenuAction[] editDeviceListMenuActions = new MenuAction[]{
                /* Add device */                                            // TODO
                new MenuAction() {
                    public void runItem() {
                        System.out.println("Stub!");
                    }
                },

                /* Edit device */                                           // TODO
                new MenuAction() {
                    public void runItem() {
                        System.out.println("Stub!");
                    }
                },

                /* Delete device */                                         // TODO
                new MenuAction() {
                    public void runItem() {
                        System.out.println("Stub!");
                    }
                },
        };
        Menu editDeviceListMenu = new Menu(editDeviceListMenuActions, editDeviceListCommands);

        /* Person list copy to find the editing one */
        PersonList personList = new PersonList();

        /* Edit person list menu */
        MenuAction[] editPersonMenuActions = new MenuAction[]{
                /* Update first name */
                new MenuAction() {
                    public void runItem() {
                        Person person = personList.get(0);
                        String newName = getNotNullLineByDialog("Enter person's new first name:");
                        database.updatePerson(person.getId(),
                                new Person(newName, person.getLastName(), person.getBirthDate(), person.getId()));
                        System.out.println("Done.");
                    }
                },

                /* Update last name */
                new MenuAction() {
                    public void runItem() {
                        Person person = personList.get(0);
                        String newName = getNotNullLineByDialog("Enter person's new last name:");
                        database.updatePerson(person.getId(),
                                new Person(person.getFirstName(), newName, person.getBirthDate(), person.getId()));
                        System.out.println("Done.");
                    }
                },

                /* Update birth date */
                new MenuAction() {
                    public void runItem() {
                        Person person = personList.get(0);
                        String newDate = getNotNullLineByDialog("Enter person's new birth date (dd-mm-yyyy):");
                        LocalDate newDateParsed;
                        try {
                            newDateParsed = LocalDate.parse(newDate, DateTimeFormatter.ofPattern("d-MM-yyyy"));
                        } catch (DateTimeParseException e) {
                            printWithDelay(WRONG_DATA_TEXT);
                            return;
                        }
                        database.updatePerson(person.getId(),
                                new Person(person.getFirstName(), person.getLastName(), newDateParsed, person.getId()));
                        System.out.println("Done.");
                    }
                },
        };
        Menu editPersonMenu = new Menu(editPersonMenuActions, editPersonCommands);

        /* Edit person list menu */
        MenuAction[] editPersonListMenuActions = new MenuAction[]{
                /* Print person list */
                new MenuAction() {
                    public void runItem() {
                        database.getPersonList().print();
                    }
                },

                /* Add person */
                new MenuAction() {
                    public void runItem() {
                        String firstName = getNotNullLineByDialog("Enter person's first name:");
                        String lastName = getNotNullLineByDialog("Enter person's last name:");
                        while (true) {
                            String birthDateRaw = getNotNullLineByDialog("Enter person's birth date (dd/mm/yyyy with any separator):");
                            String birthDateFormatted = birthDateRaw.replaceAll("\\D", "-");
                            try {
                                database.addPerson(firstName, lastName, LocalDate.parse(birthDateFormatted,
                                        DateTimeFormatter.ofPattern("d-MM-yyyy")));
                                System.out.println("Operation successful.");
                                return;
                            } catch (DateTimeParseException exception) {
                                printWithDelay(WRONG_DATA_TEXT);
                            }
                        }
                    }
                },

                /* Edit person */
                new MenuAction() {
                    public void runItem() {
                        updateList(personList, database.getPersonList());
                        exitWhileLoop:
                        while (true) {
                            switch (personList.size()) {
                                case 0:
                                    System.out.println("Person list is empty. Nothing to edit.");
                                    return;
                                case 1:
                                    System.out.println("Found one person to edit: " + personList.get(0));
                                    break exitWhileLoop;
                                default:
                                    System.out.println("Found " + personList.size() + " persons.");
                                    String personIdOrName = getNotNullLineByDialog("Enter person ID or name part:");
                                    if (isInteger(personIdOrName)) {
                                        PersonList newPersonList = new PersonList();
                                        newPersonList.add(personList.get(Integer.parseInt(personIdOrName)));
                                        updateList(personList, newPersonList);
                                        continue;
                                    }
                                    PersonList searchByFirstName = personList.clone().maskByPersonFirstName(personIdOrName);
                                    PersonList searchByLastName = personList.clone().maskByPersonLastName(personIdOrName);
                                    searchByFirstName.merge(searchByLastName);
                                    updateList(personList, searchByFirstName);
                                    continue;
                            }
                        }
                        editPersonMenu.executeCommand();
                    }
                },

                /* Delete person */                                         // TODO
                new MenuAction() {
                    public void runItem() {
                        System.out.println("Stub!");
                    }
                },
        };
        Menu editPersonListMenu = new Menu(editPersonListMenuActions, editPersonListCommands);

        /* Device list copy to sort and filter */
        DeviceList deviceList = database.getDeviceList();
        /* Sort device list menu */
        MenuAction[] sortDeviceListMenuActions = new MenuAction[]{
                /* Sort by ID */
                new MenuAction() {
                    public void runItem() {
                        SortingOrder order = getBooleanByDialog(ENTER_SORT_ORDER_TEXT)
                                ? ASCENDING
                                : DESCENDING;
                        deviceList.sort(new DeviceIdComparator(order));
                    }
                },

                /* Sort by production date */
                new MenuAction() {
                    public void runItem() {
                        SortingOrder order = getBooleanByDialog(ENTER_SORT_ORDER_TEXT)
                                ? ASCENDING
                                : DESCENDING;
                        deviceList.sort(new DeviceProductionDateComparator(order));
                    }
                },

                /* Sort by vendor name */
                new MenuAction() {
                    public void runItem() {
                        SortingOrder order = getBooleanByDialog(ENTER_SORT_ORDER_TEXT)
                                ? ASCENDING
                                : DESCENDING;
                        deviceList.sort(new DeviceVendorComparator(order));
                    }
                },

                /* Sort by model name */
                new MenuAction() {
                    public void runItem() {
                        SortingOrder order = getBooleanByDialog(ENTER_SORT_ORDER_TEXT)
                                ? ASCENDING
                                : DESCENDING;
                        deviceList.sort(new DeviceModelComparator(order));
                    }
                },

                /* Sort by color */
                new MenuAction() {
                    public void runItem() {
                        SortingOrder order = getBooleanByDialog(ENTER_SORT_ORDER_TEXT)
                                ? ASCENDING
                                : DESCENDING;
                        deviceList.sort(new DeviceColorComparator(order));
                    }
                },

                /* Sort by device type */
                new MenuAction() {
                    public void runItem() {
                        SortingOrder order = getBooleanByDialog(ENTER_SORT_ORDER_TEXT)
                                ? ASCENDING
                                : DESCENDING;
                        deviceList.sort(new DeviceTypeComparator(order));
                    }
                },

                /* Sort by price */
                new MenuAction() {
                    public void runItem() {
                        SortingOrder order = getBooleanByDialog(ENTER_SORT_ORDER_TEXT)
                                ? ASCENDING
                                : DESCENDING;
                        deviceList.sort(new DevicePriceComparator(order));
                    }
                },
        };
        Menu sortDeviceListMenu = new Menu(sortDeviceListMenuActions, sortDeviceListCommands);

        /* Browse device list menu */
        MenuAction[] browseDeviceListMenuActions = new MenuAction[]{
                /* Print current list */
                new MenuAction() {
                    public void runItem() {
                        deviceList.print();
                    }
                },

                /* Apply filter to current list */
                new MenuAction() {
                    public void runItem() {
                        DeviceList oldDeviceList = deviceList;
                        while (true) {
                            try {
                                /* Check list size */
                                /* If list contains 0 or 1 element -> notify and stop filtering */
                                if (Utils.checkListSize(oldDeviceList.size())) break;

                                /* Filter by ID */
                                String[] idFromTo = Utils.readRangeFromConsole(
                                        "Enter ID range to filter (\"X Y\", \"X\" or \"*\" for any ID)",
                                        "0",
                                        String.valueOf(oldDeviceList.size()));
                                oldDeviceList = oldDeviceList.maskByDeviceId(idFromTo[0], idFromTo[1]);
                                /* Check list size */
                                if (Utils.checkListSize(oldDeviceList.size())) break;

                                /* Filter by production date */
                                String[] dateFromTo = Utils.readRangeFromConsole(
                                        "Enter production date range to filter (\"X Y\", \"X\" or \"*\" for any date, format: dd-mm-yyyy)",
                                        "01-01-0001",
                                        "31-12-9999");
                                oldDeviceList = oldDeviceList.maskByDeviceProductionDate(dateFromTo[0], dateFromTo[1]);
                                /* Check list size */
                                if (Utils.checkListSize(oldDeviceList.size())) break;

                                /* Filter by vendor */
                                String vendorMask = ConsolePipe.getNotNullLineByDialog("Enter vendor name (or name part) to filter or \"*\" for any vendor.");
                                oldDeviceList = oldDeviceList.maskByDeviceVendor(vendorMask);
                                /* Check list size */
                                if (Utils.checkListSize(oldDeviceList.size())) break;

                                /* Filter by model */
                                String modelMask = ConsolePipe.getNotNullLineByDialog("Enter model name (or name part) to filter or \"*\" for any model.");
                                oldDeviceList = oldDeviceList.maskByDeviceModel(modelMask);
                                /* Check list size */
                                if (Utils.checkListSize(oldDeviceList.size())) break;

                                /* Filter by color */
                                Color[] preferredColors = Utils.getEnumArrayFromString(Color.class, "Enter preferred colors to filter or \"*\" for any color (colors have to be separated by any non-character symbol[s]).").toArray(new Color[]{});
                                oldDeviceList = oldDeviceList.maskByDeviceColor(preferredColors);
                                /* Check list size */
                                if (Utils.checkListSize(oldDeviceList.size())) break;

                                /* Filter by device type */
                                DeviceType[] preferredTypes = Utils.getEnumArrayFromString(DeviceType.class, "Enter preferred device types to filter or \"*\" for any device type (device types have to be separated by any non-character symbol[s]).").toArray(new DeviceType[]{});
                                oldDeviceList = oldDeviceList.maskByDeviceType(preferredTypes);
                                /* Check list size */
                                if (Utils.checkListSize(oldDeviceList.size())) break;

                                /* Filter by price */
                                String[] priceFromTo = Utils.readRangeFromConsole(
                                        "Enter price range to filter (\"X Y\", \"X\" or \"*\" for any price)",
                                        "0.00",
                                        "999999.99");
                                oldDeviceList = oldDeviceList.maskByDevicePrice(priceFromTo[0], priceFromTo[1]);

                                /* No need to check list size */
                                break;
                            } catch (InputFormatException e) {
                                ConsolePipe.printWithDelay(WRONG_DATA_TEXT);
                                continue;
                            }
                        }
                        /* Update value with a filtered one */
                        updateList(deviceList, oldDeviceList);
                    }
                },

                /* Reset current list */
                new MenuAction() {
                    public void runItem() {
                        /* Update value with a fresh one */
                        updateList(deviceList, database.getDeviceList());
                    }
                },

                /* Sort current list */
                new MenuAction() {
                    public void runItem() {
                        sortDeviceListMenu.executeCommand();
                    }
                },
        };
        Menu browseDeviceListMenu = new Menu(browseDeviceListMenuActions, browseDeviceListCommands);

        /* Main menu */
        MenuAction[] mainMenuActions = new MenuAction[]{
                /* Sell device */                                           // TODO
                new MenuAction() {
                    public void runItem() {
                        System.out.println("Stub!");
                    }
                },

                /* Edit device list */
                new MenuAction() {
                    public void runItem() {
                        editDeviceListMenu.executeCommand();
                    }
                },

                /* Edit person list */
                new MenuAction() {
                    public void runItem() {
                        editPersonListMenu.executeCommand();
                    }
                },

                /* Delete sell from history */                              // TODO
                new MenuAction() {
                    public void runItem() {
                        System.out.println("Stub!");
                    }
                },

                /* Browse device list */
                new MenuAction() {
                    public void runItem() {
                        browseDeviceListMenu.executeCommand();
                    }
                },

                /* Browse person list */                                    // TODO
                new MenuAction() {
                    public void runItem() {
                        System.out.println("Stub!");
                    }
                },

                /* Browse sales history */                                  // TODO
                new MenuAction() {
                    public void runItem() {
                        System.out.println("Stub!");
                    }
                },
        };
        Menu mainMenu = new Menu(mainMenuActions, mainCommands);

        mainMenu.executeCommand();
    }
}
