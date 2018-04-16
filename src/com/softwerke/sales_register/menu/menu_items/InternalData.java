package com.softwerke.menu.menu_items;

import com.softwerke.tables.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/* Singleton, connecting all the menus */
public class InternalData {
    final Database database;

    List<Person> personList;
    Person currentPerson;

    List<Device> deviceList;
    Device currentDevice;

    List<SeveralDevices> orderItems;
    LocalDate saleDate;

    List<Sale> saleList;

    public InternalData(Database database) {
        this.database = database;
    }

    void resetDeviceList() {
        deviceList = database.getDeviceStream().collect(Collectors.toList());
    }

    void resetPersonList() {
        personList = database.getPersonStream().collect(Collectors.toList());
    }

    void resetSaleList() {
        saleList = database.getSaleStream().collect(Collectors.toList());
    }
}
