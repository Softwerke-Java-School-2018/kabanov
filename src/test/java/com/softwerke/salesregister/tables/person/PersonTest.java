package com.softwerke.salesregister.tables.person;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PersonTest {
    @Test(expected = IllegalArgumentException.class)
    public void of_NegativeId_IllegalArgumentException() {
        Person.of(-1, "Name", "Surname", LocalDate.of(1981, 10, 1), false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void of_NullFirstName_IllegalArgumentException() {
        Person.of(2, null, "Surname", LocalDate.of(1981, 10, 1), false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void of_NullLastName_IllegalArgumentException() {
        Person.of(2, "Name", null, LocalDate.of(1981, 10, 1), false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void of_NullBirthDate_IllegalArgumentException() {
        Person.of(2, "Name", "Surname", null, false);
    }

    @Test
    public void of_ValidValues_Success() {
        Person person = Person.of(2, "Name", "Surname", LocalDate.of(1981, 10, 1), false);

        assertEquals(2, person.getId());
        assertEquals("Name", person.getFirstName());
        assertEquals("Surname", person.getLastName());
        assertEquals(LocalDate.of(1981,10, 1), person.getBirthDate());
        assertFalse(person.isDeleted());
    }
}
