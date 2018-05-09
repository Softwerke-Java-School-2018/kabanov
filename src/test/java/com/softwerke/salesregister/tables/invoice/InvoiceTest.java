package com.softwerke.salesregister.tables.invoice;

import com.softwerke.salesregister.tables.person.Person;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class InvoiceTest {
    @Test
    public void invoiceTest() {
        Person person = Person.of(0, "Name", "Surname", LocalDate.MIN, false);
        Invoice testInvoice = new Invoice(person, new ArrayList<>(), LocalDate.MAX, 0, false);

        assertEquals(LocalDate.MAX, testInvoice.getDate());
        assertEquals(person, testInvoice.getPerson());
        assertEquals(person.toString(), testInvoice.getPersonName());
        assertEquals(new ArrayList<>(), testInvoice.getInvoices().collect(Collectors.toList()));
        assertEquals(0, testInvoice.getId());
        assertFalse(testInvoice.isDeleted());
    }

    @Test
    public void invoiceDisabledCopyTest() {
        Person person = Person.of(0, "Name", "Surname", LocalDate.MIN, false);
        Invoice testInvoice = new Invoice(person, new ArrayList<>(), LocalDate.MAX, 0, false);
        testInvoice = testInvoice.getDisabledCopy();

        assertEquals(LocalDate.MAX, testInvoice.getDate());
        assertEquals(person, testInvoice.getPerson());
        assertEquals(person.toString(), testInvoice.getPersonName());
        assertEquals(new ArrayList<>(), testInvoice.getInvoices().collect(Collectors.toList()));
        assertEquals(0, testInvoice.getId());
        assertTrue(testInvoice.isDeleted());
    }
}
