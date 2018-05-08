package com.softwerke.salesregister.tables.data.dao;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.tables.data.storage.ArrayListStorage;
import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.data.storage.StorageInitializer;
import com.softwerke.salesregister.tables.person.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        try {
            new StorageInitializer(daoPerson, daoDevice, daoInvoice);
        } catch (BuilderNotInitializedException e) {
            fail();
        }
    }

    @After
    public void clearStorage() {
        daoPerson.clearStorage();
        daoDevice.clearStorage();
        daoInvoice.clearStorage();
    }

    @Test
    public void testPersonUpdate() {
        Person arkadiy = daoPerson.getPerson(3);
        Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(arkadiy);

        Person anna = null;
        try {
            anna = builder.firstName("Anna").lastName("Kuznetsova").build();
        } catch (BuilderNotInitializedException e) {
            fail();
        }

        daoPerson.updatePerson(anna);

        Person arkadiyRenamed = daoPerson.getPerson(3);

        assertEquals(arkadiyRenamed.id,           arkadiyRenamed.id);
        assertEquals(arkadiyRenamed.birthDate,    arkadiyRenamed.birthDate);
        assertEquals(arkadiyRenamed.isDeleted,    arkadiyRenamed.isDeleted);

        assertEquals(arkadiyRenamed.firstName, "Anna");
        assertEquals(arkadiyRenamed.lastName, "Kuznetsova");
    }

    @Test
    public void testPersonDelete() {
        Person arkadiy = daoPerson.getPerson(3);
        Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(arkadiy);

        Person arkadiyDeleted = null;
        try {
            arkadiyDeleted = builder.isDeleted(true).build();
        } catch (BuilderNotInitializedException e) {
            fail();
        }

        daoPerson.updatePerson(arkadiyDeleted);

        arkadiyDeleted = daoPerson.getPerson(3);

        assertEquals(arkadiyDeleted.id,           arkadiy.id);
        assertEquals(arkadiyDeleted.birthDate,    arkadiy.birthDate);
        assertEquals(arkadiyDeleted.firstName,    arkadiy.firstName);
        assertEquals(arkadiyDeleted.lastName,     arkadiy.lastName);

        assertEquals(arkadiyDeleted.isDeleted, true);
    }
}
