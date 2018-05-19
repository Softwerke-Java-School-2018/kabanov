package com.softwerke.salesregister.utils;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.io.ConsoleIOStream;
import com.softwerke.salesregister.tables.data.dao.DaoDevice;
import com.softwerke.salesregister.tables.data.dao.DaoInvoice;
import com.softwerke.salesregister.tables.data.dao.DaoPerson;
import com.softwerke.salesregister.tables.data.storage.ArrayListStorage;
import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.data.storage.StorageInitializer;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.person.Person;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class UtilsTest {
    private static DaoPerson daoPerson;
    private static DaoDevice daoDevice;

    @Before
    public void initStorage() {
        Storage storage = new ArrayListStorage();
        daoPerson = new DaoPerson(storage);
        daoDevice = new DaoDevice(storage);
        DaoInvoice daoInvoice = new DaoInvoice(storage);
        try {
            new StorageInitializer(daoPerson, daoDevice, daoInvoice);
        } catch (BuilderNotInitializedException e) {
            fail();
        }
    }

    private static ConsoleIOStream getConsoleWithInputText(String input) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            return new ConsoleIOStream(IOUtils.toInputStream(input, "UTF-8"), outputStream);
        } catch (IOException e) {
            fail();
            return null;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseToEnums_InvalidString_IllegalArgumentException() {
        assertNull(Utils.parseToEnums("not_a_color", Color.class).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseToEnums_NullClass_IllegalArgumentException() {
        Utils.parseToEnums("red", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseToEnums_NullString_IllegalArgumentException() {
        Utils.parseToEnums(null, Color.class);
    }

    @Test
    public void parseToEnums_ValidString_Success() {
        Color[] colors = new Color[]{Color.RED, Color.RED, Color.BLACK, Color.CHAMPAGNE};

        Stream<Color> colours = Utils.parseToEnums("red, ReD, BLACK, champagne", Color.class);

        assertArrayEquals(colors, colours.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void centerIndexOf_NullString_NPE() {
        Utils.centerIndexOf(null, (char) 0);
    }

    @Test
    public void centerIndexOf_ValidValues_Success() {
        assertEquals(3, Utils.centerIndexOf("123%567", '%'));
        assertEquals(1, Utils.centerIndexOf("1 34567", ' '));
        assertEquals(6, Utils.centerIndexOf("123456S", 'S'));
        assertEquals(-1, Utils.centerIndexOf("1234567", '0'));
    }

    @Test
    public void parseStringToLocalDateTest() {
        assertEquals(LocalDate.of(2017, 2, 10), Utils.parseStringToLocalDate("10 2 2017"));
        assertEquals(LocalDate.of(2018, 3, 11), Utils.parseStringToLocalDate("11/3/2018"));
        assertEquals(LocalDate.of(2020, 1, 1), Utils.parseStringToLocalDate("01-01-2020"));
        assertEquals(LocalDate.of(2007, 12, 12), Utils.parseStringToLocalDate("12 - 12 /= 2007"));
    }

    @Test
    public void selectPersonTest() {
        initStorage();
        Person person;

        person = Utils.selectPerson(daoPerson.persons(), getConsoleWithInputText("not a person"));
        assertNull(person);

        person = Utils.selectPerson(daoPerson.persons(), getConsoleWithInputText("3"));
        assertNotNull(person);
        assertEquals(3, person.getId());

        person = Utils.selectPerson(daoPerson.persons(), getConsoleWithInputText("Zaycev"));
        assertNotNull(person);
        assertEquals("Zaycev", person.getLastName());

        person = Utils.selectPerson(daoPerson.persons(), getConsoleWithInputText("I\nInna"));
        assertNotNull(person);
        assertEquals("Inna", person.getFirstName());

        person = Utils.selectPerson(daoPerson.persons(), getConsoleWithInputText("V Petrov"));
        assertNotNull(person);
        assertEquals("Petrov", person.getLastName());
    }

    @Test
    public void selectDeviceTest() {
        initStorage();
        Device device;

        device = Utils.selectDevice(daoDevice.devices(), getConsoleWithInputText("not a device"));
        assertNull(device);

        device = Utils.selectDevice(daoDevice.devices(), getConsoleWithInputText("_xiaomi_"));
        assertNotNull(device);
        assertEquals("Xiaomi", device.getVendor());

        device = Utils.selectDevice(daoDevice.devices(), getConsoleWithInputText("g\ngalaxy"));
        assertNotNull(device);
        assertEquals("Galaxy S6", device.getModel());

        device = Utils.selectDevice(daoDevice.devices(), getConsoleWithInputText("apple X"));
        assertNotNull(device);
        assertEquals("IPhone X", device.getModel());

        device = Utils.selectDevice(daoDevice.devices(), getConsoleWithInputText("5"));
        assertNotNull(device);
        assertEquals(5, device.getId());
    }
}
