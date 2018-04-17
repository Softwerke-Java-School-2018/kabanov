package com.softwerke.salesregister.tables;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Database {
    private final List<Person> personList;
    private final List<Device> deviceList;
    private final List<Invoice> invoiceList;

    public Database() {
        personList = new ArrayList<>();
        deviceList = new ArrayList<>();
        invoiceList = new ArrayList<>();


        /* DEBUG CODE */
        addPerson("Vasiliy", "Petrov", LocalDate.of(1971, 12, 5));
        addPerson("Peter", "Zaycev", LocalDate.of(1962, 7, 16));
        addPerson("Ivan", "Smirnov", LocalDate.of(1970, 2, 27));
        addPerson("Arkadiy", "Zolotukhin", LocalDate.of(1993, 9, 6));
        addPerson("Elisabeth", "Bolshevikova", LocalDate.of(1987, 4, 11));
        addPerson("Inna", "Listkova", LocalDate.of(1981, 11, 2));

        addDevice("Galaxy S6", "Samsung", Color.WHITE, LocalDate.of(2017, 11, 10), DeviceType.PHONE, "61990");
        addDevice("IPhone X", "Apple", Color.CHAMPAGNE, LocalDate.of(2017, 11, 14), DeviceType.PHONE, "105990");
        addDevice("G6", "LG", Color.PURPLE, LocalDate.of(2016, 5, 12), DeviceType.PHONE, "44990");
        addDevice("A1", "Xiaomi", Color.RED, LocalDate.of(2015, 10, 20), DeviceType.PHONE, "32990");
        addDevice("Walkman 1", "Sony", Color.GRAY, LocalDate.of(2009, 9, 14), DeviceType.PLAYER, "7990");
        addDevice("Pavillion G6", "HP", Color.BLACK, LocalDate.of(2015, 8, 21), DeviceType.LAPTOP, "45990");
        addDevice("SHIELD K1", "NVIDIA", Color.BLACK, LocalDate.of(2016, 3, 1), DeviceType.TABLET, "24990");

        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            ArrayList<InvoiceLine> order = new ArrayList<>();
            for (int j = 0; j < rand.nextInt(5); j++)
                order.add(new InvoiceLine(deviceList.get(rand.nextInt(deviceList.size())), rand.nextInt(3) + 1));
            if (!order.isEmpty())
                sell(personList.get(rand.nextInt(personList.size())), order, LocalDate.of(rand.nextInt(10) + 2000, rand.nextInt(11) + 1, rand.nextInt(27) + 1));
        }
        /* END OF DEBUG CODE */
    }

    public void addPerson(String firstName, String lastName, LocalDate birthDate) {
        personList.add(new Person(firstName, lastName, birthDate, personList.size()));
    }

    public void addDevice(String model, String vendor, Color color, LocalDate productionDate,
                          DeviceType deviceType, String price) {
        deviceList.add(new Device(model, vendor, color, productionDate, deviceType, price, deviceList.size()));
    }

    public void sell(Person person, List<InvoiceLine> orderItems, LocalDate date) {
        invoiceList.add(new Invoice(person, orderItems, date, invoiceList.size()));
    }

    public Invoice getInvoice(int id) {
        return invoiceList.get(id);
    }

    public Device getDevice(int id) {
        return deviceList.get(id);
    }

    public void updatePerson(Person person) {
        updatePerson(person.getId(), person);
    }

    public void updatePerson(int id, Person person) {
        personList.set(id, person);
    }

    public void updateDevice(Device device) {
        updateDevice(device.getId(), device);
    }

    public void updateDevice(int id, Device device) {
        deviceList.set(id, device);
    }

    public void updateInvoice(int id, Invoice invoice) {
        invoiceList.set(id, invoice);
    }

    public Stream<Device> getDeviceStream() {
        return deviceList.stream();
    }

    public Stream<Person> getPersonStream() {
        return personList.stream();
    }

    public Stream<Invoice> getInvoiceStream() {
        return invoiceList.stream();
    }
}