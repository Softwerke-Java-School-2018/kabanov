package com.softwerke.salesregister.menu;

import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;
import com.softwerke.salesregister.tables.data.Database;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/* Singleton, connecting all the menus */
public class InternalData {
    public final Database database;

    public List<Person> personList;
    public Person currentPerson;

    public List<Device> deviceList;
    public Device currentDevice;

    public List<InvoiceLine> orderItems;
    public LocalDate invoiceDate;

    public List<Invoice> saleList;

    public InternalData(Database database) {
        this.database = database;
    }

    public void resetDeviceList() {
        deviceList = database.getDeviceStream().collect(Collectors.toList());
    }

    public void resetPersonList() {
        personList = database.getPersonStream().collect(Collectors.toList());
    }

    void resetInvoiceList() {
        saleList = database.getInvoiceStream().collect(Collectors.toList());
    }
}
