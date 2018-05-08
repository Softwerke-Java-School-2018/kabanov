package com.softwerke.salesregister.tables.data.dao;

import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DaoInvoice extends Dao {
    public DaoInvoice(Storage storage) {
        super(storage);
    }

    public void sell(Person person, List<InvoiceLine> invoiceItems, LocalDate date) {
        if (!ObjectUtils.allNotNull(person, invoiceItems, date, invoiceItems.toArray())) {
            throw new IllegalArgumentException("One or more arguments is null!");
        }
        List<Invoice> invoiceList = storage.getInvoiceList();
        invoiceList.add(new Invoice(person, invoiceItems, date, invoiceList.size(), false));
    }

    public void updateInvoice(Invoice invoice) {
        Objects.requireNonNull(invoice);
        storage.getInvoiceList().set(invoice.id, invoice);
    }

    public Invoice getInvoice(int id) {
        return storage.getInvoiceList().get(id);
    }

    public Stream<Invoice> invoices() {
        return storage.getInvoiceList().stream();
    }

    public void clearStorage() {
        storage.getInvoiceList().clear();
    }
}
