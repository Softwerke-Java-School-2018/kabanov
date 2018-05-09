package com.softwerke.salesregister.menu.filterlist;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.tables.data.dao.DaoDevice;
import com.softwerke.salesregister.tables.data.dao.DaoInvoice;
import com.softwerke.salesregister.tables.data.dao.DaoPerson;
import com.softwerke.salesregister.tables.data.storage.ArrayListStorage;
import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.data.storage.StorageInitializer;
import com.softwerke.salesregister.tables.device.DeviceType;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.utils.Interval;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class FilterInvoiceListMenuTest {
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
    public void invoiceIdRangeSearchTest() {
        Interval<Integer> interval = new Interval<>(1, 3);
        IntStream ids = daoInvoice.invoices()
                .filter(invoice -> interval.contains(invoice.getId()))
                .mapToInt(Invoice::getId);
        int[] idArray = ids.toArray();
        assertArrayEquals(new int[]{1, 2, 3}, idArray);
    }

    @Test
    public void invoiceDateRangeSearchTest() {
        Interval<ChronoLocalDate> interval = new Interval<>(
                LocalDate.of(2017, 1, 1),
                LocalDate.of(2017, 12, 31));
        Stream<LocalDate> dates = daoInvoice.invoices()
                .filter(invoice -> interval.contains(invoice.getDate()))
                .map(Invoice::getDate);
        LocalDate[] datesArray = dates.toArray(LocalDate[]::new);
        assertArrayEquals(new LocalDate[]{
                LocalDate.of(2017, 4, 19),
                LocalDate.of(2017, 8, 6),
                LocalDate.of(2017, 12, 24),}, datesArray);
    }

    @Test
    public void invoiceTotalRangeSearchTest() {
        Interval<BigDecimal> interval = new Interval<>(
                new BigDecimal(70000),
                new BigDecimal(300000));
        Stream<BigDecimal> dates = daoInvoice.invoices()
                .filter(invoice -> interval.contains(invoice.getTotalSum()))
                .map(Invoice::getTotalSum);
        BigDecimal[] datesArray = dates.toArray(BigDecimal[]::new);
        assertArrayEquals(new BigDecimal[]{
                new BigDecimal("93950.00"),
                new BigDecimal("296910.00"),
                new BigDecimal("142970.00"),}, datesArray);
    }

    @Test
    public void invoiceDeviceTypeSearchTest() {
        Stream<Invoice> invoices = daoInvoice.invoices().filter(
                invoice -> invoice.getInvoices().anyMatch(
                        devices -> Stream.of(DeviceType.LAPTOP, DeviceType.PLAYER).anyMatch(
                                type -> type.equals(devices.getDevice().getDeviceType()))));
        Invoice[] result = invoices.toArray(Invoice[]::new);
        assertArrayEquals(new Invoice[]{
                daoInvoice.getInvoice(0),
                daoInvoice.getInvoice(2),
                daoInvoice.getInvoice(3),
                daoInvoice.getInvoice(5),}, result);
    }
}
