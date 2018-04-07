package com.softwerke.tables;

import com.softwerke.masklist.DeviceList;
import com.softwerke.masklist.PersonList;
import com.softwerke.masklist.SaleList;

import java.time.LocalDate;

public class Database {
    private final PersonList personList;
    private final DeviceList deviceList;
    private final SaleList salesHistory;

    public Database() {
        personList = new PersonList();
        deviceList = new DeviceList();
        salesHistory = new SaleList();

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
    }

    public void addPerson(String firstName, String lastName, LocalDate birthDate) {
        personList.add(new Person(firstName, lastName, birthDate, personList.size()));
    }

    public void addDevice(String model, String vendor, Color color, LocalDate productionDate,
                          DeviceType deviceType, String price) {
        deviceList.add(new Device(model, vendor, color, productionDate, deviceType, price, deviceList.size()));
    }

    void sell(int personID, SeveralDevices[] devicesToSell, LocalDate saleDate) {
        salesHistory.add(new Sale(personID, devicesToSell, saleDate, deviceList));
    }

    public DeviceList getDeviceList() {
        return deviceList.clone();
    }

    public PersonList getPersonList() {
        return personList.clone();
    }

    public void updatePerson(int id, Person person) {
        personList.set(id, person);
    }
}
