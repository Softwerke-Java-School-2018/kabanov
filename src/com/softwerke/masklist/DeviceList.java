package com.softwerke.masklist;

import com.softwerke.menu.InputFormatException;
import com.softwerke.tables.Color;
import com.softwerke.tables.Device;
import com.softwerke.tables.DeviceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.softwerke.console.ConsolePipe.PRESS_ANYKEY_TEXT;
import static com.softwerke.console.ConsolePipe.getLineByDialog;

public class DeviceList extends ArrayList<Device> {
    public DeviceList maskByDeviceId(String from, String to) {
        int fromId, toId;
        try {
            fromId = Integer.parseInt(from);
            toId = Integer.parseInt(to);
        } catch (InputFormatException e) {
            throw new InputFormatException();
        }
        DeviceList returning = new DeviceList();
        for (Device device : this)
            if (device.getId() >= fromId && device.getId() <= toId)
                returning.add(device);
        return returning;
    }

    public DeviceList maskByDeviceVendor(String nameToMask) {
        nameToMask = nameToMask.toLowerCase();
        if (nameToMask.trim().equals("*")) return this;
        DeviceList returning = new DeviceList();
        for (Device device : this) {
            String deviceVendorName = device.getVendor().toLowerCase();
            if (deviceVendorName.contains(nameToMask))
                returning.add(device);
        }
        return returning;
    }

    public DeviceList maskByDeviceModel(String nameToMask) {
        nameToMask = nameToMask.toLowerCase();
        if (nameToMask.trim().equals("*")) return this;
        DeviceList returning = new DeviceList();
        for (Device device : this) {
            String deviceVendorName = device.getModel().toLowerCase();
            if (deviceVendorName.contains(nameToMask))
                returning.add(device);
        }
        return returning;
    }

    public DeviceList maskByDeviceProductionDate(String from, String to) {
        LocalDate fromDate, toDate;
        try {
            fromDate = LocalDate.parse(from, DateTimeFormatter.ofPattern("d-MM-yyyy")).minusDays(1);
            toDate = LocalDate.parse(to, DateTimeFormatter.ofPattern("d-MM-yyyy")).plusDays(1);
        } catch (InputFormatException e) {
            throw new InputFormatException();
        }
        DeviceList returning = new DeviceList();
        for (Device device : this)
            if (device.getProductionDate().isAfter(fromDate) && device.getProductionDate().isBefore(toDate))
                returning.add(device);
        return returning;
    }

    public DeviceList maskByDeviceColor(Color[] colors) {
        DeviceList returning = new DeviceList();
        for (Device device : this)
            for (Color color : colors)
                if (device.getColor() == color) {
                    returning.add(device);
                    break;
                }
        return returning;
    }

    public DeviceList maskByDeviceType(DeviceType[] deviceTypes) {
        DeviceList returning = new DeviceList();
        for (Device device : this)
            for (DeviceType deviceType : deviceTypes)
                if (device.getDeviceType() == deviceType) {
                    returning.add(device);
                    break;
                }
        return returning;
    }

    public DeviceList maskByDevicePrice(String from, String to) {
        BigDecimal fromValue, toValue;
        try {
            /* Argument cast with added epsilon (to perform >= and <= actions) */
            fromValue = new BigDecimal(from).setScale(2, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("-0.01"));
            toValue = new BigDecimal(to).setScale(2, BigDecimal.ROUND_HALF_UP).add(new BigDecimal("0.01"));
        } catch (InputFormatException e) {
            throw new InputFormatException();
        }
        DeviceList returning = new DeviceList();
        for (Device device : this)
            /* If both compareTo() return greater (1 + 1 = 2), add item */
            if (device.getPrice().compareTo(fromValue) + toValue.compareTo(device.getPrice()) == 2)
                returning.add(device);
        return returning;
    }

    public void print() {
        if(isEmpty()) {
            System.out.println();           /* Separating blank line */
            System.out.println("List is empty.");
            return;
        }
        System.out.println();           /* Separating blank line */
        System.out.println(" ID | Device vendor / model |   Color   |  Type  | Prod. date |   Price");
        System.out.println("-------------------------------------------------------------------------");
        for (Device device : this)
            System.out.println(device.toFormattedString());
        getLineByDialog(PRESS_ANYKEY_TEXT);
    }

    @Override
    public DeviceList clone() {
        DeviceList returning = new DeviceList();
        returning.addAll(this);
        return returning;
    }
}
