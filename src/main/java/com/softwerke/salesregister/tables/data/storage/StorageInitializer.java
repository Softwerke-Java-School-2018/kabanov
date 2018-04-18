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
import java.util.Random;

public class StorageInitializer {
    public StorageInitializer(DaoPerson daoPerson, DaoDevice daoDevice, DaoInvoice daoInvoice) {
        Person.PersonBuilder pBuilder = new Person.PersonBuilder().isDeleted(false);
        int id = 0;
        pBuilder.firstName("Vasiliy").lastName("Petrov").birthDate(LocalDate.of(1971, 12, 5)).id(id++);
        daoPerson.addPerson(pBuilder.build());
        pBuilder.firstName("Peter").lastName("Zaycev").birthDate(LocalDate.of(1962, 7, 16)).id(id++);
        daoPerson.addPerson(pBuilder.build());
        pBuilder.firstName("Ivan").lastName("Smirnov").birthDate(LocalDate.of(1970, 2, 27)).id(id++);
        daoPerson.addPerson(pBuilder.build());
        pBuilder.firstName("Arkadiy").lastName("Zolotukhin").birthDate(LocalDate.of(1993, 9, 6)).id(id++);
        daoPerson.addPerson(pBuilder.build());
        pBuilder.firstName("Elisabeth").lastName("Bolshevikova").birthDate(LocalDate.of(1987, 4, 11)).id(id++);
        daoPerson.addPerson(pBuilder.build());
        pBuilder.firstName("Inna").lastName("Listkova").birthDate(LocalDate.of(1981, 11, 2)).id(id++);

        Device.DeviceBuilder dBuilder = new Device.DeviceBuilder().isDeleted(false);
        id = 0;
        dBuilder.model("Galaxy S6").vendor("Samsung").color(Color.WHITE).productionDate(LocalDate.of(2017, 11, 10)).type(DeviceType.PHONE).price(new BigDecimal("61990")).id(id++);
        daoDevice.addDevice(dBuilder.build());
        dBuilder.model("IPhone X").vendor("Apple").color(Color.CHAMPAGNE).productionDate(LocalDate.of(2017, 11, 10)).type(DeviceType.PHONE).price(new BigDecimal("61990")).id(id++);
        daoDevice.addDevice(dBuilder.build());
        dBuilder.model("G6").vendor("LG").color(Color.PURPLE).productionDate(LocalDate.of(2016, 5, 12)).type(DeviceType.PHONE).price(new BigDecimal("44990")).id(id++);
        daoDevice.addDevice(dBuilder.build());
        dBuilder.model("A1").vendor("Xiaomi").color(Color.RED).productionDate(LocalDate.of(2015, 10, 20)).type(DeviceType.PHONE).price(new BigDecimal("32990")).id(id++);
        daoDevice.addDevice(dBuilder.build());
        dBuilder.model("Walkman 1").vendor("Sony").color(Color.GRAY).productionDate(LocalDate.of(2009, 9, 14)).type(DeviceType.PLAYER).price(new BigDecimal("7990")).id(id++);
        daoDevice.addDevice(dBuilder.build());
        dBuilder.model("Pavillion G6").vendor("HP").color(Color.BLACK).productionDate(LocalDate.of(2015, 8, 21)).type(DeviceType.LAPTOP).price(new BigDecimal("45990")).id(id++);
        daoDevice.addDevice(dBuilder.build());
        dBuilder.model("SHIELD K1").vendor("NVIDIA").color(Color.BLACK).productionDate(LocalDate.of(2016, 3, 1)).type(DeviceType.TABLET).price(new BigDecimal("24990")).id(id++);
        daoDevice.addDevice(dBuilder.build());

        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            ArrayList<InvoiceLine> order = new ArrayList<>();
            for (int j = 0; j < rand.nextInt(5); j++)
                order.add(new InvoiceLine(daoDevice.getDevice(rand.nextInt(daoDevice.getSize())), rand.nextInt(3) + 1));
            if (!order.isEmpty())
                daoInvoice.sell(daoPerson.getPerson(rand.nextInt(daoPerson.getSize())), order, LocalDate.of(rand.nextInt(10) + 2000, rand.nextInt(11) + 1, rand.nextInt(27) + 1));
        }
    }
}
