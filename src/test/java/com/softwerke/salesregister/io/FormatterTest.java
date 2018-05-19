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
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
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
    public void printFormatDevice_NullAndDeviceAndNull_OnlyOneDevicePrinted() throws BuilderNotInitializedException, UnsupportedEncodingException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);
        List<Device> deviceList = new ArrayList<>();

        deviceList.add(null);
        Device testDevice = new Device.DeviceBuilder()
                .color(Color.RED)
                .type(DeviceType.PHONE)
                .model(ConsoleIOStreamTest.STUB)
                .price(BigDecimal.TEN)
                .productionDate(LocalDate.of(1234, 12, 31))
                .vendor(ConsoleIOStreamTest.STUB)
                .id(125)
                .build();
        deviceList.add(testDevice);
        deviceList.add(null);

        Formatter.printFormatDevice(deviceList.stream(), console);

        assertEquals(System.lineSeparator() +
                " ID | Device vendor / model |   Color   |  Type  | Prod. date |   Price" + System.lineSeparator() +
                "-------------------------------------------------------------------------" + System.lineSeparator() +
                "125 |           stub! stub! |       RED |  PHONE | 1234-12-31 |     10.00" + System.lineSeparator() +
                StringLiterals.PRESS_ANYKEY_TEXT + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test(expected = NullPointerException.class)
    public void printFormatDevice_NullStream_NPE() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatDevice(null, console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatDevice_NullSink_NPE() {
        Formatter.printFormatDevice(Stream.empty(), null);
    }

    @Test
    public void printFormatDevice_EmptyStream_MessagePrinted() throws UnsupportedEncodingException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatDevice(Stream.empty(), console);

        assertEquals(System.lineSeparator() +
                        StringLiterals.LIST_IS_EMPTY_TEXT + System.lineSeparator()
                , outputStream.toString("UTF-8"));
    }

    @Test
    public void printFormatInvoice_NullAndInvoiceAndNull_OnlyOneInvoicePrinted() throws UnsupportedEncodingException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(null);
        Invoice testInvoice = new Invoice(Person.of(0, "Name", "Surname", LocalDate.MIN, false), new ArrayList<>(), LocalDate.of(1234, 10, 11), 0, false);
        invoiceList.add(testInvoice);
        invoiceList.add(null);

        Formatter.printFormatInvoice(invoiceList.stream(), console);
        assertEquals(System.lineSeparator() +
                " ID |   Customer name   |    Total    | Invoice date" + System.lineSeparator() +
                "-----------------------------------------------------" + System.lineSeparator() +
                "  0 |        Surname N. |           0 |  1234-10-11" + System.lineSeparator() +
                StringLiterals.PRESS_ANYKEY_TEXT + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test(expected = NullPointerException.class)
    public void printFormatInvoice_NullStream_NPE() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatInvoice(null, console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatInvoice_NullSink_NPE() {
        Formatter.printFormatInvoice(Stream.empty(), null);
    }

    @Test
    public void printFormatInvoice_EmptyStream_MessagePrinted() throws UnsupportedEncodingException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatInvoice(Stream.empty(), console);

        assertEquals(System.lineSeparator() +
                        StringLiterals.LIST_IS_EMPTY_TEXT + System.lineSeparator()
                , outputStream.toString("UTF-8"));
    }

    @Test
    public void printFormatPerson_NullAndPersonAndNull_OnlyOnePersonPrinted() throws UnsupportedEncodingException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        List<Person> personList = new ArrayList<>();
        personList.add(null);
        Person testPerson = Person.of(0, "Name", "Surname", LocalDate.of(1234, 2, 1), false);
        personList.add(testPerson);
        personList.add(null);

        Formatter.printFormatPerson(personList.stream(), console);
        assertEquals(System.lineSeparator() +
                " ID |            Name           | Birth date" + System.lineSeparator() +
                "--------------------------------------------" + System.lineSeparator() +
                "  0 |              Name Surname | 1234-02-01" + System.lineSeparator() +
                StringLiterals.PRESS_ANYKEY_TEXT + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test(expected = NullPointerException.class)
    public void printFormatPerson_NullStream_NPE() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatPerson(null, console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatPerson_NullSink_NPE() {
        Formatter.printFormatPerson(Stream.empty(), null);
    }

    @Test
    public void printFormatPerson_EmptyStream_MessagePrinted() throws UnsupportedEncodingException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printFormatPerson(Stream.empty(), console);

        assertEquals(System.lineSeparator() +
                        StringLiterals.LIST_IS_EMPTY_TEXT + System.lineSeparator()
                , outputStream.toString("UTF-8"));
    }

    @Test
    public void printShopList_NullAndInvoiceLineAndNull_OnlyOneInvoiceLinePrinted() throws BuilderNotInitializedException, UnsupportedEncodingException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        List<InvoiceLine> invoiceLineList = new ArrayList<>();
        invoiceLineList.add(null);
        Device testDevice = new Device.DeviceBuilder()
                .color(Color.RED)
                .type(DeviceType.PHONE)
                .model(ConsoleIOStreamTest.STUB)
                .price(BigDecimal.TEN)
                .productionDate(LocalDate.MIN)
                .vendor(ConsoleIOStreamTest.STUB)
                .id(12345)
                .build();
        InvoiceLine invoiceLine = new InvoiceLine(testDevice, 2);
        invoiceLineList.add(invoiceLine);
        invoiceLineList.add(null);

        Formatter.printShopList(invoiceLineList.stream(), console);
        assertEquals("            Items            | Amount |   Total" + System.lineSeparator() +
                "--------------------------------------------------" + System.lineSeparator() +
                "stub! stub! @ RED            |      2 |      20.00" + System.lineSeparator() +
                " Total:                                      20.00" + System.lineSeparator() +
                System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void printShopList_NullStream_NPE() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printShopList(null, console);
    }

    @Test(expected = IllegalArgumentException.class)
    public void printShopList_NullSink_NPE() {
        Formatter.printShopList(Stream.empty(), null);
    }

    @Test
    public void printShopList_EmptyStream_MessagePrinted() throws UnsupportedEncodingException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printShopList(Stream.empty(), console);

        assertEquals("Shop list is empty." + System.lineSeparator(), outputStream.toString("UTF-8"));
    }

    @Test
    public void printReceipt_OneInvoice_InvoicePrinted() throws UnsupportedEncodingException {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Invoice testInvoice = new Invoice(Person.of(0, "Name", "Surname", LocalDate.MIN, false), new ArrayList<>(), LocalDate.of(5678, 2, 1), 0, false);

        Formatter.printReceipt(testInvoice, console);
        assertEquals(" Shopping date: 5678-02-01" + System.lineSeparator() +
                        " Customer name: Surname N." + System.lineSeparator() +
                        "Shop list is empty." + System.lineSeparator(),
                outputStream.toString("UTF-8"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void printReceipt_NullInvoice_IllegalArgumentException() {
        initConsoleWithInputText(ConsoleIOStreamTest.STUB);

        Formatter.printReceipt(null, console);
    }

    @Test(expected = IllegalArgumentException.class)
    public void printReceipt_NullSink_IllegalArgumentException() {
        Invoice testInvoice = new Invoice(Person.of(0, "Name", "Surname", LocalDate.MIN, false), new ArrayList<>(), LocalDate.MAX, 0, false);

        Formatter.printReceipt(testInvoice, null);
    }
}
