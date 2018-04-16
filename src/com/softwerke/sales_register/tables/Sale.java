package com.softwerke.tables;

import com.softwerke.Utils;
import com.softwerke.console.IOPipe;
import com.softwerke.list.HasId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.softwerke.Utils.leftPad;

/* Immutable */
public class Sale extends HasId {
    public static final Sale DELETED_SALE = new Sale(Person.DELETED_PERSON,
            new ArrayList<>(0), LocalDate.now(), -1);
    private final Person person;
    private final List<SeveralDevices> severalDevices;
    private final LocalDate saleDate;
    private final BigDecimal totalSum;

    public Sale(Person person, List<SeveralDevices> orderItems, LocalDate date, int id) {
        super(id);
        this.person = person;
        severalDevices = orderItems;
        saleDate = date;
        BigDecimal sum = BigDecimal.ZERO;
        for (SeveralDevices devices : severalDevices) sum = sum.add(devices.getInternalSum());
        totalSum = sum;
    }

    @Override
    public String toString() {
        return person + " | " + saleDate + " | " + totalSum;
    }


    public String toFormattedString() {
        return leftPad(String.valueOf(id), 3) + " | " + leftPad(person.toString(), 17) + " | " +
                leftPad(totalSum.toString(), 11) + " |  " + saleDate;
    }

    public void printReceipt() {
        IOPipe.printLine(" Shopping date: " + saleDate);
        IOPipe.printLine(" Customer name: " + person);
        Utils.printShopList(severalDevices);
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
}
