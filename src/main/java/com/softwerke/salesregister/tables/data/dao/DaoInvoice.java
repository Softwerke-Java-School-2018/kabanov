package com.softwerke.salesregister.tables.data.dao;

import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class DaoInvoice extends Dao {
    public DaoInvoice(Storage storage) {
        super(storage);
    }

    public void sell(Person person, List<InvoiceLine> orderItems, LocalDate date) {
        List<Invoice> invoiceList = storage.getInvoiceList();
        invoiceList.add(new Invoice(person, orderItems, date, invoiceList.size(), false));
    }

    public void updateInvoice(Invoice invoice) {
        storage.getInvoiceList().set(invoice.getId(), invoice);
    }

    public Invoice getInvoice(int id) {
        return storage.getInvoiceList().get(id);
    }

    public Stream<Invoice> getInvoiceStream() {
        return storage.getInvoiceList().stream();
    }
}
