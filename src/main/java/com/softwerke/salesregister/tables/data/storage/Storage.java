package com.softwerke.salesregister.tables.data.storage;

import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.person.Person;

import java.util.List;

public interface Storage {
    List<Device> getDeviceList();

    List<Person> getPersonList();

    List<Invoice> getInvoiceList();
}
