package com.softwerke.salesregister.tables.invoice;


import com.softwerke.salesregister.tables.person.Person;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Invoice {
    public final int id;
    public final Person person;
    public final LocalDate date;
    public final BigDecimal totalSum;
    public final boolean isDeleted;

    private final List<InvoiceLine> invoiceItems;

    public Invoice(Person person, List<InvoiceLine> invoiceItems, LocalDate date, int id, boolean isDeleted) {
        Objects.requireNonNull(invoiceItems);
        this.invoiceItems = invoiceItems.stream().filter(Objects::nonNull).collect(Collectors.toList());
        this.id = id;
        this.person = Objects.requireNonNull(person);
        this.date = Objects.requireNonNull(date);
        this.isDeleted = isDeleted;
        this.totalSum = invoiceItems.stream()
                .map(invoice -> invoice.internalSum.multiply(BigDecimal.valueOf(invoice.amount)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Stream<InvoiceLine> getInvoices() {
        return invoiceItems.stream();
    }

    public Invoice getDeletedCopy() {
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
