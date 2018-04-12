package com.softwerke.menu.menu_items;

import com.softwerke.list.DeviceList;
import com.softwerke.list.PersonList;
import com.softwerke.list.SaleList;
import com.softwerke.tables.Database;
import com.softwerke.tables.Device;
import com.softwerke.tables.Person;
import com.softwerke.tables.SeveralDevices;

import java.time.LocalDate;
import java.util.List;

/* Singleton, connecting all the menus */
class MenuInternalData {
    static Database database;

    static PersonList searchPersonList;
    static Person currentPerson;

    static DeviceList searchDeviceList;
    static Device currentDevice;

    static List<SeveralDevices> orderItems;
    static LocalDate saleDate;

    static SaleList searchSalesList;
}
