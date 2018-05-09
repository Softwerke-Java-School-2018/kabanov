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

        Person anna = arkadiy.copyWithNewFirstName("Anna").copyWithNewLastName("Kuznetsova");

        daoPerson.updatePerson(anna);

        Person arkadiyRenamed = daoPerson.getPerson(3);

        assertEquals(arkadiyRenamed.getId(), arkadiyRenamed.getId());
        assertEquals(arkadiyRenamed.getBirthDate(), arkadiyRenamed.getBirthDate());
        assertEquals(arkadiyRenamed.isDeleted(), arkadiyRenamed.isDeleted());

        assertEquals(arkadiyRenamed.getFirstName(), "Anna");
        assertEquals(arkadiyRenamed.getLastName(), "Kuznetsova");
    }

    @Test
    public void testPersonDelete() {
        Person arkadiy = daoPerson.getPerson(3);

        Person arkadiyDeleted = arkadiy.copyWithNewIsDeleted(true);

        daoPerson.updatePerson(arkadiyDeleted);

        arkadiyDeleted = daoPerson.getPerson(3);

        assertEquals(arkadiyDeleted.getId(), arkadiy.getId());
        assertEquals(arkadiyDeleted.getBirthDate(), arkadiy.getBirthDate());
        assertEquals(arkadiyDeleted.getFirstName(), arkadiy.getFirstName());
        assertEquals(arkadiyDeleted.getLastName(), arkadiy.getLastName());

        assertEquals(arkadiyDeleted.isDeleted(), true);
    }
}
