package com.softwerke.salesregister.menu;

import com.softwerke.salesregister.io.IOStream;
import com.softwerke.salesregister.tables.data.dao.DaoDevice;
import com.softwerke.salesregister.tables.data.dao.DaoInvoice;
import com.softwerke.salesregister.tables.data.dao.DaoPerson;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* Singleton, connecting all the menus */
public class InternalData {
    public final IOStream ioStream;

    public final DaoPerson daoPerson;
    public final DaoDevice daoDevice;
    public final DaoInvoice daoInvoice;

    public List<Person> personList;
    public Stream<Person> persons;
    public Person currentPerson;

    public List<Device> deviceList;
    public Stream<Device> devices;
    public Device currentDevice;

    public List<InvoiceLine> orderItems;
    public LocalDate invoiceDate;

    public List<Invoice> invoiceList;
    public Stream<Invoice> invoices;

    public InternalData(IOStream ioStream, DaoPerson daoPerson, DaoDevice daoDevice, DaoInvoice daoInvoice) {
        this.ioStream = Objects.requireNonNull(ioStream);
        this.daoPerson = Objects.requireNonNull(daoPerson);
        this.daoDevice = Objects.requireNonNull(daoDevice);
        this.daoInvoice = Objects.requireNonNull(daoInvoice);
    }

    public void resetPersonList() {
        personList = daoPerson.persons().collect(Collectors.toList());
    }

    public void resetDeviceList() {
        deviceList = daoDevice.devices().collect(Collectors.toList());
    }

    public void resetInvoiceList() {
        invoiceList = daoInvoice.invoices().collect(Collectors.toList());
    }
}
