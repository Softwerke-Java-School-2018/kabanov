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

import static org.junit.Assert.*;

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
    public void updatePerson_ValidValue_Success() {
        Person arkadiy = daoPerson.getPerson(3);

        Person anna = arkadiy.copyWithNewFirstName("Anna").copyWithNewLastName("Kuznetsova");

        daoPerson.updatePerson(anna);

        Person arkadiyRenamed = daoPerson.getPerson(3);

        assertEquals(anna.getId(), arkadiyRenamed.getId());
        assertEquals(anna.getBirthDate(), arkadiyRenamed.getBirthDate());
        assertEquals(anna.isDeleted(), arkadiyRenamed.isDeleted());

        assertEquals("Anna", arkadiyRenamed.getFirstName());
        assertEquals("Kuznetsova", arkadiyRenamed.getLastName());
    }

    @Test(expected = NullPointerException.class)
    public void updatePerson_Null_NPE() {
        daoPerson.updatePerson(null);
    }

    @Test
    public void updatePerson_DeletedPerson_DeletionSuccessful() {
        Person arkadiy = daoPerson.getPerson(3);

        Person arkadiyDeleted = arkadiy.copyWithNewIsDeleted(true);

        daoPerson.updatePerson(arkadiyDeleted);

        arkadiyDeleted = daoPerson.getPerson(3);

        assertEquals(arkadiy.getId(), arkadiyDeleted.getId());
        assertEquals(arkadiy.getBirthDate(), arkadiyDeleted.getBirthDate());
        assertEquals(arkadiy.getFirstName(), arkadiyDeleted.getFirstName());
        assertEquals(arkadiy.getLastName(), arkadiyDeleted.getLastName());

        assertTrue(arkadiyDeleted.isDeleted());
    }
}
