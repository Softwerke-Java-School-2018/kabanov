package com.softwerke.salesregister.tables.invoice;


import com.softwerke.salesregister.tables.person.Person;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private final int id;
    private final Person person;
    private final List<InvoiceLine> invoiceItems;
    private final LocalDate date;
    private final BigDecimal totalSum;
    private final boolean isDeleted;

    public Invoice(Person person, List<InvoiceLine> invoiceItems, LocalDate date, int id, boolean isDeleted) {
        this.id = id;
        this.person = person;
        this.invoiceItems = new ArrayList<>();
        this.invoiceItems.addAll(invoiceItems);
        this.date = date;
        this.isDeleted = isDeleted;
        this.totalSum = invoiceItems.stream()
                .map(invoice -> invoice.getInternalSum().multiply(BigDecimal.valueOf(invoice.getAmount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public List<InvoiceLine> getInvoiceItems() {
        return invoiceItems;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public Invoice getDisabledCopy() {
        return new Invoice(person, invoiceItems, date, id, false);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("ID", id).
                append("person", person).
                append("date", date).
                append("totalSum", totalSum).
                toString();
    }
}
