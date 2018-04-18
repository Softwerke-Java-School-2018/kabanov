package com.softwerke.salesregister.tables.data;

import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.person.Person;

import java.util.ArrayList;
import java.util.List;

public class ArrayListStorage implements Storage {
    private final List<Person> personList;
    private final List<Device> deviceList;
    private final List<Invoice> invoiceList;

    ArrayListStorage() {
        personList = new ArrayList<>();
        deviceList = new ArrayList<>();
        invoiceList = new ArrayList<>();
    }
}
