package com.softwerke.salesregister.menu.menuitems;

import com.softwerke.salesregister.tables.*;

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

    List<InvoiceLine> orderItems;
    LocalDate invoiceDate;

    List<Invoice> saleList;

    public InternalData(Database database) {
        this.database = database;
    }

    void resetDeviceList() {
        deviceList = database.getDeviceStream().collect(Collectors.toList());
    }

    void resetPersonList() {
        personList = database.getPersonStream().collect(Collectors.toList());
    }

    void resetInvoiceList() {
        saleList = database.getInvoiceStream().collect(Collectors.toList());
    }
}
