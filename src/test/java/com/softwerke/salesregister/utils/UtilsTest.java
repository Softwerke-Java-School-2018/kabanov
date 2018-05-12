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
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class UtilsTest {
    private static DaoPerson daoPerson;
    private static DaoDevice daoDevice;
    private static ConsoleIOStream console;

    private static void initStorage() {
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

    private static void initConsoleWithInputText(String input) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            console = new ConsoleIOStream(IOUtils.toInputStream(input, "UTF-8"), outputStream);
        } catch (IOException e) {
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseToEnumsException() {
        assertNull(Utils.parseToEnums("not_a_color", Color.class).toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseToEnumsNull0() {
        Utils.parseToEnums("red", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseToEnumsNull1() {
        Utils.parseToEnums(null, Color.class);
    }

    @Test
    public void testConvertToEnumInstancesPass() {
        Color[] colors = new Color[]{Color.RED, Color.RED, Color.BLACK, Color.CHAMPAGNE};

        Stream<Color> colours = Utils.parseToEnums("red, ReD, BLACK, champagne", Color.class);

        assertArrayEquals(colors, colours.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void centerIndexOfTestNull() {
        Utils.centerIndexOf(null, (char) 0);
    }

    @Test
    public void centerIndexOfTest() {
        assertEquals(3, Utils.centerIndexOf("123%567", '%'));
        assertEquals(1, Utils.centerIndexOf("1 34567", ' '));
        assertEquals(6, Utils.centerIndexOf("123456S", 'S'));
        assertEquals(-1, Utils.centerIndexOf("1234567", '0'));
    }

    @Test
    public void parseStringToLocalDateTest() {
        assertEquals(LocalDate.of(2017,2,10), Utils.parseStringToLocalDate("10 2 2017"));
        assertEquals(LocalDate.of(2018,3,11), Utils.parseStringToLocalDate("11/3/2018"));
        assertEquals(LocalDate.of(2020,1,1), Utils.parseStringToLocalDate("01-01-2020"));
        assertEquals(LocalDate.of(2007,12,12), Utils.parseStringToLocalDate("12 - 12 /= 2007"));
    }

    @Test
    public void selectPersonTest() {
        initStorage();
        Person person;

        initConsoleWithInputText("not a person");
        person = Utils.selectPerson(daoPerson.persons(), console);
        assertEquals(null, person);

        initConsoleWithInputText("3");
        person = Utils.selectPerson(daoPerson.persons(), console);
        assertNotNull(person);
        assertEquals(3, person.getId());

        initConsoleWithInputText("Zaycev");
        person = Utils.selectPerson(daoPerson.persons(), console);
        assertNotNull(person);
        assertEquals("Zaycev", person.getLastName());

        initConsoleWithInputText("I\nInna");
        person = Utils.selectPerson(daoPerson.persons(), console);
        assertNotNull(person);
        assertEquals("Inna", person.getFirstName());

        initConsoleWithInputText("V Petrov");
        person = Utils.selectPerson(daoPerson.persons(), console);
        assertNotNull(person);
        assertEquals("Petrov", person.getLastName());
    }

    @Test
    public void selectDeviceTest() {
        initStorage();
        Device device;

        initConsoleWithInputText("not a device");
        device = Utils.selectDevice(daoDevice.devices(), console);
        assertEquals(null, device);

        initConsoleWithInputText("_xiaomi_");
        device = Utils.selectDevice(daoDevice.devices(), console);
        assertNotNull(device);
        assertEquals("Xiaomi", device.getVendor());

        initConsoleWithInputText("g\ngalaxy");
        device = Utils.selectDevice(daoDevice.devices(), console);
        assertNotNull(device);
        assertEquals("Galaxy S6", device.getModel());

        initConsoleWithInputText("apple X");
        device = Utils.selectDevice(daoDevice.devices(), console);
        assertNotNull(device);
        assertEquals("IPhone X", device.getModel());

        initConsoleWithInputText("5");
        device = Utils.selectDevice(daoDevice.devices(), console);
        assertNotNull(device);
        assertEquals(5, device.getId());
    }
}
