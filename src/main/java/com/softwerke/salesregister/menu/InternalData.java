package com.softwerke.salesregister.menu;

import com.softwerke.salesregister.tables.data.dao.DaoDevice;
import com.softwerke.salesregister.tables.data.dao.DaoInvoice;
import com.softwerke.salesregister.tables.data.dao.DaoPerson;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/* Singleton, connecting all the menus */
public class InternalData {
    public final DaoPerson daoPerson;
    public final DaoDevice daoDevice;
    public final DaoInvoice daoInvoice;

    public List<Person> personList;
    public Person currentPerson;

    public List<Device> deviceList;
    public Device currentDevice;

    public List<InvoiceLine> orderItems;
    public LocalDate invoiceDate;

    public List<Invoice> invoiceList;

    public InternalData(DaoPerson daoPerson, DaoDevice daoDevice, DaoInvoice daoInvoice) {
        this.daoPerson = daoPerson;
        this.daoDevice = daoDevice;
        this.daoInvoice = daoInvoice;
    }

    public void resetPersonList() {
        personList = daoPerson.getPersonStream().collect(Collectors.toList());
    }

    public void resetDeviceList() {
        deviceList = daoDevice.getDeviceStream().collect(Collectors.toList());
    }

    public void resetInvoiceList() {
        invoiceList = daoInvoice.getInvoiceStream().collect(Collectors.toList());
    }
}
