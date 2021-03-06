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
    private final int id;
    private final Person person;
    private final List<InvoiceLine> invoiceItems;
    private final LocalDate date;
    private final BigDecimal totalSum;
    private final boolean isDeleted;

    public Invoice(Person person, List<InvoiceLine> invoiceItems, LocalDate date, int id, boolean isDeleted) {
        Objects.requireNonNull(invoiceItems);
        this.invoiceItems = invoiceItems.stream().filter(Objects::nonNull).collect(Collectors.toList());
        this.id = id;
        this.person = Objects.requireNonNull(person);
        this.date = Objects.requireNonNull(date);
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

    public String getPersonName() {
        return person.toString();
    }

    public Stream<InvoiceLine> getInvoices() {
        return invoiceItems.stream().unordered();
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

    public Invoice getDeletedCopy() {
        return new Invoice(person, invoiceItems, date, id, true);
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
