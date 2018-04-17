package com.softwerke.tables;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    public static final Sale DELETED_SALE = new Sale(Person.DELETED_PERSON,
            new ArrayList<>(0), LocalDate.now(), -1);
    private final int id;
    private final Person person;
    private final List<SeveralDevices> severalDevices;
    private final LocalDate saleDate;
    private final BigDecimal totalSum;

    public Sale(Person person, List<SeveralDevices> orderItems, LocalDate date, int id) {
        this.id = id;
        this.person = person;
        severalDevices = orderItems;
        saleDate = date;
        BigDecimal sum = BigDecimal.ZERO;
        for (SeveralDevices devices : severalDevices) sum = sum.add(devices.getInternalSum());
        totalSum = sum;
    }

    public int getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public List<SeveralDevices> getSeveralDevices() {
        return severalDevices;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    @Override
    public String toString() {
        return person + " | " + saleDate + " | " + totalSum;
    }
}
