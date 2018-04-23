package com.softwerke.salesregister.tables.data.dao;

import com.softwerke.salesregister.tables.data.storage.ArrayListStorage;
import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.data.storage.StorageInitializer;
import com.softwerke.salesregister.tables.person.Person;
import org.junit.*;

public class DaoPersonTest {
    private static DaoPerson daoPerson;
    private static DaoDevice daoDevice;
    private static DaoInvoice daoInvoice;

    @BeforeClass
    public static void initStorage() {
        Storage storage = new ArrayListStorage();
        daoPerson = new DaoPerson(storage);
        daoDevice = new DaoDevice(storage);
        daoInvoice = new DaoInvoice(storage);
    }

    @Before
    public void fillStorage() {
        new StorageInitializer(daoPerson, daoDevice, daoInvoice);
    }

    @After
    public void clearStorage() {
        daoPerson.clearStorage();
        daoDevice.clearStorage();
        daoInvoice.clearStorage();
    }

    @Test
    public void testPersonUpdate() {
        Person arkadiyZolotukhin = daoPerson.getPerson(3);
        Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(arkadiyZolotukhin);

        Person annaKuznetsova = builder.firstName("Anna").lastName("Kuznetsova").build();

        daoPerson.updatePerson(annaKuznetsova);

        Person arkadiyZolotukhinRenamed = daoPerson.getPerson(3);

        Assert.assertEquals(arkadiyZolotukhinRenamed.getId(),           arkadiyZolotukhinRenamed.getId());
        Assert.assertEquals(arkadiyZolotukhinRenamed.getBirthDate(),    arkadiyZolotukhinRenamed.getBirthDate());
        Assert.assertEquals(arkadiyZolotukhinRenamed.isDeleted(),       arkadiyZolotukhinRenamed.isDeleted());

        Assert.assertEquals(arkadiyZolotukhinRenamed.getFirstName(), "Anna");
        Assert.assertEquals(arkadiyZolotukhinRenamed.getLastName(), "Kuznetsova");
    }

    @Test
    public void testPersonDelete() {
        Person arkadiyZolotukhin = daoPerson.getPerson(3);
        Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(arkadiyZolotukhin);

        Person arkadiyZolotukhinDeleted = builder.isDeleted(true).build();

        daoPerson.updatePerson(arkadiyZolotukhinDeleted);

        arkadiyZolotukhinDeleted = daoPerson.getPerson(3);

        Assert.assertEquals(arkadiyZolotukhinDeleted.getId(),           arkadiyZolotukhin.getId());
        Assert.assertEquals(arkadiyZolotukhinDeleted.getBirthDate(),    arkadiyZolotukhin.getBirthDate());
        Assert.assertEquals(arkadiyZolotukhinDeleted.getFirstName(),    arkadiyZolotukhin.getFirstName());
        Assert.assertEquals(arkadiyZolotukhinDeleted.getLastName(),     arkadiyZolotukhin.getLastName());

        Assert.assertEquals(arkadiyZolotukhinDeleted.isDeleted(), true);
    }
}
