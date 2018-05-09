package com.softwerke.salesregister.tables.data.storage;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.tables.data.dao.DaoDevice;
import com.softwerke.salesregister.tables.data.dao.DaoInvoice;
import com.softwerke.salesregister.tables.data.dao.DaoPerson;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class StorageInitializer {
    public StorageInitializer(DaoPerson daoPerson, DaoDevice daoDevice, DaoInvoice daoInvoice)
            throws BuilderNotInitializedException {
        if (!ObjectUtils.allNotNull(daoDevice, daoInvoice, daoPerson)) {
            throw new IllegalArgumentException("One or more arguments is null!");
        }

        daoPerson.addPerson(Person.of(0, "Vasiliy", "Petrov", LocalDate.of(1971, 12, 5), false));
        daoPerson.addPerson(Person.of(1, "Peter", "Zaycev", LocalDate.of(1962, 7, 16), false));
        daoPerson.addPerson(Person.of(2, "Ivan", "Smirnov", LocalDate.of(1970, 2, 27), false));
        daoPerson.addPerson(Person.of(3, "Arkadiy", "Zolotukhin", LocalDate.of(1993, 9, 6), false));
        daoPerson.addPerson(Person.of(4, "Elisabeth", "Bolshevikova", LocalDate.of(1987, 4, 11), false));
        daoPerson.addPerson(Person.of(5, "Inna", "Listkova", LocalDate.of(1981, 11, 2), false));

        Device.DeviceBuilder dBuilder = new Device.DeviceBuilder().isDeleted(false);
        dBuilder.model("Galaxy S6")
                .vendor("Samsung")
                .color(Color.WHITE)
                .productionDate(LocalDate.of(2017, 11, 10))
                .type(DeviceType.PHONE)
                .price(new BigDecimal("51990"))
                .id(0);
        daoDevice.addDevice(dBuilder.build());

        dBuilder.model("IPhone X")
                .vendor("Apple")
                .color(Color.CHAMPAGNE)
                .productionDate(LocalDate.of(2017, 11, 10))
                .type(DeviceType.PHONE)
                .price(new BigDecimal("61990"))
                .id(1);
        daoDevice.addDevice(dBuilder.build());

        dBuilder.model("G6")
                .vendor("LG")
                .color(Color.PURPLE)
                .productionDate(LocalDate.of(2016, 5, 12))
                .type(DeviceType.PHONE)
                .price(new BigDecimal("44990"))
                .id(2);
        daoDevice.addDevice(dBuilder.build());

        dBuilder.model("A1")
                .vendor("Xiaomi")
                .color(Color.RED)
                .productionDate(LocalDate.of(2015, 10, 20))
                .type(DeviceType.PHONE)
                .price(new BigDecimal("32990"))
                .id(3);
        daoDevice.addDevice(dBuilder.build());

        dBuilder.model("Walkman 1")
                .vendor("Sony")
                .color(Color.GRAY)
                .productionDate(LocalDate.of(2009, 9, 14))
                .type(DeviceType.PLAYER)
                .price(new BigDecimal("7990"))
                .id(4);
        daoDevice.addDevice(dBuilder.build());

        dBuilder.model("Pavillion G6")
                .vendor("HP")
                .color(Color.BLACK)
                .productionDate(LocalDate.of(2015, 8, 21))
                .type(DeviceType.LAPTOP)
                .price(new BigDecimal("45990"))
                .id(5);
        daoDevice.addDevice(dBuilder.build());

        dBuilder.model("SHIELD K1")
                .vendor("NVIDIA")
                .color(Color.BLACK)
                .productionDate(LocalDate.of(2016, 3, 1))
                .type(DeviceType.TABLET)
                .price(new BigDecimal("24990"))
                .id(6);
        daoDevice.addDevice(dBuilder.build());

        quickSell(daoInvoice, daoPerson.getPerson(3), LocalDate.of(2018, 2, 10),
                invoiceLineFactory(daoDevice, 1, 1),
                invoiceLineFactory(daoDevice, 4, 2));

        quickSell(daoInvoice, daoPerson.getPerson(2), LocalDate.of(2017, 4, 19),
                invoiceLineFactory(daoDevice, 3, 3));

        quickSell(daoInvoice, daoPerson.getPerson(4), LocalDate.of(2018, 6, 28),
                invoiceLineFactory(daoDevice, 0, 1),
                invoiceLineFactory(daoDevice, 2, 1),
                invoiceLineFactory(daoDevice, 5, 1));

        quickSell(daoInvoice, daoPerson.getPerson(0), LocalDate.of(2017, 8, 6),
                invoiceLineFactory(daoDevice, 5, 4),
                invoiceLineFactory(daoDevice, 6, 1));

        quickSell(daoInvoice, daoPerson.getPerson(1), LocalDate.of(2018, 10, 15),
                invoiceLineFactory(daoDevice, 1, 2),
                invoiceLineFactory(daoDevice, 2, 2));

        quickSell(daoInvoice, daoPerson.getPerson(3), LocalDate.of(2017, 12, 24),
                invoiceLineFactory(daoDevice, 4, 1),
                invoiceLineFactory(daoDevice, 5, 1));
    }

    private InvoiceLine invoiceLineFactory(DaoDevice daoDevice, int deviceId, int deviceAmount) {
        return new InvoiceLine(daoDevice.getDevice(deviceId), deviceAmount);
    }

    private void quickSell(DaoInvoice daoInvoice, Person person, LocalDate date, InvoiceLine... lines) {
        daoInvoice.sell(person, Arrays.asList(lines), date);
    }
}