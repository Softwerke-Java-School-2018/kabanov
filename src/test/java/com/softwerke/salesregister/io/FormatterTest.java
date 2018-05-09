package com.softwerke.salesregister.io;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.fail;

public class FormatterTest {
    private static ConsoleIOStream console;
    private static ByteArrayOutputStream outputStream;

    private static void initConsoleWithInputText(String input) {
        try {
            outputStream = new ByteArrayOutputStream();
            console = new ConsoleIOStream(IOUtils.toInputStream(input, "UTF-8"), outputStream);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void printFormatDeviceTest() throws BuilderNotInitializedException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);
        List<Device> deviceList = new ArrayList<>();
        deviceList.add(null);
        Device testDevice = new Device.DeviceBuilder()
                .color(Color.RED)
                .type(DeviceType.PHONE)
                .model(ConsoleIOStreamTest.STUB)
                .price(BigDecimal.ZERO)
                .productionDate(LocalDate.MIN)
                .vendor(ConsoleIOStreamTest.STUB)
                .id(12345)
                .build();
        deviceList.add(testDevice);
        deviceList.add(null);

        Formatter.printFormatDevice(deviceList.stream(), console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatDeviceTestNull0() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatDevice(null, console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatDeviceTestNull1() {
        Formatter.printFormatDevice(Stream.empty(), null);
    }

    @Test
    public void printFormatDeviceTestEmpty() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatDevice(Stream.empty(), console);
    }

    @Test
    public void printFormatInvoiceTest() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(null);
        Invoice testInvoice = new Invoice(Person.of(0, "Name", "Surname", LocalDate.MIN, false), new ArrayList<>(), LocalDate.MAX, 0, false);
        invoiceList.add(testInvoice);
        invoiceList.add(null);

        Formatter.printFormatInvoice(invoiceList.stream(), console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatInvoiceTestNull0() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatInvoice(null, console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatInvoiceTestNull1() {
        Formatter.printFormatInvoice(Stream.empty(), null);
    }

    @Test
    public void printFormatInvoiceTestEmpty() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatInvoice(Stream.empty(), console);
    }

    @Test
    public void printFormatPersonTest() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);
        List<Person> personList = new ArrayList<>();
        personList.add(null);
        Person testPerson = Person.of(0, "Name", "Surname", LocalDate.MIN, false);
        personList.add(testPerson);
        personList.add(null);

        Formatter.printFormatPerson(personList.stream(), console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatPersonTestNull0() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatPerson(null, console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatPersonTestNull1() {
        Formatter.printFormatPerson(Stream.empty(), null);
    }

    @Test
    public void printFormatPersonTestEmpty() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatPerson(Stream.empty(), console);
    }

    @Test
    public void printShopListTest() throws BuilderNotInitializedException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);
        List<InvoiceLine> invoiceLineList = new ArrayList<>();
        invoiceLineList.add(null);
        Device testDevice = new Device.DeviceBuilder()
                .color(Color.RED)
                .type(DeviceType.PHONE)
                .model(ConsoleIOStreamTest.STUB)
                .price(BigDecimal.ZERO)
                .productionDate(LocalDate.MIN)
                .vendor(ConsoleIOStreamTest.STUB)
                .id(12345)
                .build();
        InvoiceLine invoiceLine = new InvoiceLine(testDevice, 2);
        invoiceLineList.add(invoiceLine);
        invoiceLineList.add(null);

        Formatter.printShopList(invoiceLineList.stream(), console);
    }

    @Test(expected = IllegalArgumentException.class)
    public void printShopListTestNull0() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printShopList(null, console);
    }

    @Test(expected = IllegalArgumentException.class)
    public void printShopListTestNull1() {
        Formatter.printShopList(Stream.empty(), null);
    }

    @Test
    public void printShopListTestEmpty() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printShopList(Stream.empty(), console);
    }

    @Test
    public void printReceiptTest()  {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Invoice testInvoice = new Invoice(Person.of(0, "Name", "Surname", LocalDate.MIN, false), new ArrayList<>(), LocalDate.MAX, 0, false);

        Formatter.printReceipt(testInvoice, console);
    }

    @Test(expected = IllegalArgumentException.class)
    public void printReceiptTestNull0() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printReceipt(null, console);
    }

    @Test(expected = IllegalArgumentException.class)
    public void printReceiptTestNull1() {
        Invoice testInvoice = new Invoice(Person.of(0, "Name", "Surname", LocalDate.MIN, false), new ArrayList<>(), LocalDate.MAX, 0, false);

        Formatter.printReceipt(testInvoice, null);
    }
}
