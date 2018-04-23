package com.softwerke.salesregister.tables.data.storage;

import com.softwerke.salesregister.tables.data.dao.DaoDevice;
import com.softwerke.salesregister.tables.data.dao.DaoInvoice;
import com.softwerke.salesregister.tables.data.dao.DaoPerson;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StorageInitializer {
    public StorageInitializer(DaoPerson daoPerson, DaoDevice daoDevice, DaoInvoice daoInvoice) {
        Person.PersonBuilder pBuilder = new Person.PersonBuilder().isDeleted(false);
        pBuilder.firstName("Vasiliy")
                .lastName("Petrov")
                .birthDate(LocalDate.of(1971, 12, 5))
                .id(0);
        daoPerson.addPerson(pBuilder.build());

        pBuilder.firstName("Peter")
                .lastName("Zaycev")
                .birthDate(LocalDate.of(1962, 7, 16))
                .id(1);
        daoPerson.addPerson(pBuilder.build());

        pBuilder.firstName("Ivan")
                .lastName("Smirnov")
                .birthDate(LocalDate.of(1970, 2, 27))
                .id(2);
        daoPerson.addPerson(pBuilder.build());

        pBuilder.firstName("Arkadiy")
                .lastName("Zolotukhin")
                .birthDate(LocalDate.of(1993, 9, 6))
                .id(3);
        daoPerson.addPerson(pBuilder.build());

        pBuilder.firstName("Elisabeth")
                .lastName("Bolshevikova")
                .birthDate(LocalDate.of(1987, 4, 11))
                .id(4);
        daoPerson.addPerson(pBuilder.build());

        pBuilder.firstName("Inna")
                .lastName("Listkova")
                .birthDate(LocalDate.of(1981, 11, 2))
                .id(5);
        daoPerson.addPerson(pBuilder.build());

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

        Random randDays = new Random(System.currentTimeMillis());
        List<InvoiceLine> order;

        order = new ArrayList<>();
        order.add(new InvoiceLine(daoDevice.getDevice(1), 1));
        order.add(new InvoiceLine(daoDevice.getDevice(4), 2));
        daoInvoice.sell(daoPerson.getPerson(3), order, LocalDate.of(2018,2,10));

        order = new ArrayList<>();
        order.add(new InvoiceLine(daoDevice.getDevice(3), 3));
        daoInvoice.sell(daoPerson.getPerson(2), order, LocalDate.of(2017,4,19));

        order = new ArrayList<>();
        order.add(new InvoiceLine(daoDevice.getDevice(0), 1));
        order.add(new InvoiceLine(daoDevice.getDevice(2), 1));
        order.add(new InvoiceLine(daoDevice.getDevice(5), 1));
        daoInvoice.sell(daoPerson.getPerson(4), order, LocalDate.of(2018,6,28));

        order = new ArrayList<>();
        order.add(new InvoiceLine(daoDevice.getDevice(5), 4));
        order.add(new InvoiceLine(daoDevice.getDevice(6), 1));
        daoInvoice.sell(daoPerson.getPerson(0), order, LocalDate.of(2017,8,6));

        order = new ArrayList<>();
        order.add(new InvoiceLine(daoDevice.getDevice(1), 2));
        order.add(new InvoiceLine(daoDevice.getDevice(2), 2));
        daoInvoice.sell(daoPerson.getPerson(1), order, LocalDate.of(2018,10,15));

        order = new ArrayList<>();
        order.add(new InvoiceLine(daoDevice.getDevice(4), 1));
        order.add(new InvoiceLine(daoDevice.getDevice(5), 1));
        daoInvoice.sell(daoPerson.getPerson(3), order, LocalDate.of(2017,12,24));
    }
}