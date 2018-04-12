package com.softwerke;

public class StringPool {
    public static final String WRONG_COMMAND_TEXT = "Read command is wrong. Retry input.";
    public static final String WRONG_DATA_TEXT = "Read value is illegal.";
    public static final String ENTRY_IS_DELETED = "Entry with this ID has been deleted.";
    public static final String LIST_IS_EMPTY_TEXT = "List is empty. There's no items to process.";
    public static final String LIST_CONTAINS_ONE_ITEM_TEXT = "List already contains one item.";
    public static final String PRESS_ANYKEY_TEXT = "Enter something to continue...";
    public static final String ENTER_SORT_ORDER_TEXT = "Enter \"Y\" to set ascending order, otherwise - descending.";
    public static final String SUCCESSFUL = "Operation successful.";
    public static final String[] MAIN_COMMANDS = {
            "-- Main menu --",
            "Select the command:",
            " 1) Sell device",
            " 2) Edit device list",
            " 3) Edit person list",
            " 4) Delete the sell record from history",
            " 5) Browse device list",
            " 6) Browse person list",
            " 7) Browse sales history",
            " 0) Exit"};
    public static final String[] ORDER_CHECKOUT_COMMANDS = {
            "-- Order checkout menu --",
            "Select the command:",
            " 1) Select the customer",
            " 2) Add item",
            " 3) Print shop list",
            " 4) Set the sale date (default is today)",
            " 5) Remove item",
            " 6) Proceed",
            " 0) Cancel"};
    public static final String[] EDIT_DEVICE_LIST_COMMANDS = {
            "-- Edit device list menu --",
            " 1) Print device list",
            " 2) Add device",
            " 3) Edit device",
            " 0) Return"};
    public static final String[] EDIT_DEVICE_COMMANDS = {
            "-- Edit device menu --",
            " 1) Update device vendor",
            " 2) Update device model name",
            " 3) Update device color",
            " 4) Update device type",
            " 5) Update device price",
            " 6) Update device production date",
            " 7) Delete device",
            " 0) Return"};
    public static final String[] EDIT_PERSON_COMMANDS = {
            "-- Edit person menu --",
            " 1) Update first name",
            " 2) Update last name",
            " 3) Update birth date",
            " 4) Delete person",
            " 0) Return"};
    public static final String[] EDIT_PERSON_LIST_COMMANDS = {
            "-- Edit person list menu --",
            " 1) Print person list",
            " 2) Add person",
            " 3) Edit person",
            " 0) Return"};
    public static final String[] BROWSE_DEVICE_LIST_COMMANDS = {
            "-- Browse and search in device list menu --",
            " 1) Print current list",
            " 2) Apply filter to current list",
            " 3) Reset current list",
            " 4) Sort list",
            " 0) Return"};
    public static final String[] BROWSE_PERSON_LIST_COMMANDS = {
            "-- Browse and search in person list menu --",
            " 1) Print current list",
            " 2) Apply filter to current list",
            " 3) Reset current list",
            " 4) Sort list",
            " 0) Return"};
    public static final String[] BROWSE_SALES_HISTORY_COMMANDS = {
            "-- Browse and search in sales history menu --",
            " 1) Print current list",
            " 2) Apply filter to current list",
            " 3) Reset current list",
            " 4) Sort list",
            " 5) Print sale details",
            " 0) Return"};
    public static final String[] SORT_DEVICE_LIST_COMMANDS = {
            "-- Sort device list menu --",
            " 1) Sort by ID",
            " 2) Sort by production date",
            " 3) Sort by manufacturer",
            " 4) Sort by model",
            " 5) Sort by color",
            " 6) Sort by device type",
            " 7) Sort by price",
            " 0) Return"};
    public static final String[] SORT_PERSON_LIST_COMMANDS = {
            "-- Sort person list menu --",
            " 1) Sort by ID",
            " 2) Sort by first name",
            " 3) Sort by last name",
            " 4) Sort by birth date",
            " 0) Return"};
    public static final String[] SORT_SALES_HISTORY_COMMANDS = {
            "-- Sort sales history menu --",
            " 1) Sort by ID",
            " 2) Sort by customer last name",
            " 3) Sort by sale date",
            " 4) Sort by total sum",
            " 0) Return"};
    public static final String USERNAME = "%username%";
}
