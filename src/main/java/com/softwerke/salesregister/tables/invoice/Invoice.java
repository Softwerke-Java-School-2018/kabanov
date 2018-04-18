package com.softwerke.salesregister.tables.invoice;


import com.softwerke.salesregister.tables.person.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    public static final Invoice DELETED_INVOICE = new Invoice(Person.DELETED_PERSON,
            new ArrayList<>(0), LocalDate.now(), -1);
    private final int id;
    private final Person person;
    private final List<InvoiceLine> invoiceLine;
    private final LocalDate invoiceDate;
    private final BigDecimal totalSum;

    public Invoice(Person person, List<InvoiceLine> orderItems, LocalDate date, int id) {
        this.id = id;
        this.person = person;
        invoiceLine = orderItems;
        invoiceDate = date;
        BigDecimal sum = BigDecimal.ZERO;
        //orderItems.forEach(item -> sum = sum.add(item.getDevice().getPrice().multiply(BigDecimal.valueOf(item.getAmount()))));
        for (InvoiceLine devices : invoiceLine) sum = sum.add(devices.getInternalSum());
        totalSum = sum;
    }

    public int getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public List<InvoiceLine> getInvoiceLine() {
        return invoiceLine;
    }

    public LocalDate getDate() {
        return invoiceDate;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    @Override
    public String toString() {
        return person + " | " + invoiceDate + " | " + totalSum;
    }
}
