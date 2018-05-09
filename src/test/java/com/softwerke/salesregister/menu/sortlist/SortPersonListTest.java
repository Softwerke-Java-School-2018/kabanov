package com.softwerke.salesregister.menu.sortlist;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.tables.data.dao.DaoDevice;
import com.softwerke.salesregister.tables.data.dao.DaoInvoice;
import com.softwerke.salesregister.tables.data.dao.DaoPerson;
import com.softwerke.salesregister.tables.data.storage.ArrayListStorage;
import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.data.storage.StorageInitializer;
import com.softwerke.salesregister.tables.person.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Comparator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class SortPersonListTest {
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
    public void personIdSortTest() {
        int[] personIdArray = daoPerson.persons()
                .sorted(Comparator.comparingInt(Person::getId).reversed())
                .mapToInt(Person::getId)
                .toArray();
        assertArrayEquals(new int[]{5, 4, 3, 2, 1, 0}, personIdArray);
    }

    @Test
    public void personBirthDateSortTest() {
        LocalDate[] personDateArray = daoPerson.persons()
                .sorted(Comparator.comparing(Person::getBirthDate))
                .map(Person::getBirthDate)
                .toArray(LocalDate[]::new);
        assertArrayEquals(new LocalDate[]{
                LocalDate.parse("1962-07-16"),
                LocalDate.parse("1970-02-27"),
                LocalDate.parse("1971-12-05"),
                LocalDate.parse("1981-11-02"),
                LocalDate.parse("1987-04-11"),
                LocalDate.parse("1993-09-06"),}, personDateArray);
    }
}
