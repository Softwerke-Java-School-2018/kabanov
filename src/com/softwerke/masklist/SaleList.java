package com.softwerke.masklist;

import com.softwerke.tables.Color;
import com.softwerke.tables.DeviceType;
import com.softwerke.tables.Sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class SaleList extends ArrayList<Sale> {

    SaleList maskBySaleTotalSum(String from, String to) {
        return null;
    }

    SaleList maskBySaleDate(String fromDate, String toDate) {
        return null;
    }

    SaleList maskByPersonFirstName(String name) {
        return null;
    }

    SaleList maskByPersonSecondName(String name) {
        return null;
    }

    SaleList maskByPersonBirthDate(String fromDate, String toDate) {
        return null;
    }

    SaleList maskByDeviceVendor(String nameToMask) {
        return null;
    }

    SaleList maskByDeviceModel(String nameToMask) {
        return null;
    }

    SaleList maskByDeviceProductionDate(String from, String to) {
        return null;
    }

    SaleList maskByDeviceColor(Color[] color) {
        return null;
    }

    SaleList maskByDeviceType(DeviceType[] deviceType) {
        return null;
    }

    SaleList maskByDevicePrice(String from, String to) {
        return null;
    }
}
