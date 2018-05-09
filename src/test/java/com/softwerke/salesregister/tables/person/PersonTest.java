package com.softwerke.salesregister.tables.person;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PersonTest {
    @Test(expected = IllegalArgumentException.class)
    public void testPersonOfIllegalArgs0() {
        Person.of(-1, "Name", "Surname", LocalDate.of(1981, 10, 1), false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPersonOfIllegalArgs1() {
        Person.of(2, null, "Surname", LocalDate.of(1981, 10, 1), false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPersonOfIllegalArgs2() {
        Person.of(2, "Name", null, LocalDate.of(1981, 10, 1), false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPersonOfIllegalArgs3() {
        Person.of(2, "Name", "Surname", null, false);
    }

    @Test
    public void testPersonOf() {
        Person person = Person.of(2, "Name", "Surname", LocalDate.of(1981, 10, 1), false);

        assertEquals(2, person.getId());
        assertEquals("Name", person.getFirstName());
        assertEquals("Surname", person.getLastName());
        assertEquals(LocalDate.parse("1981-10-01"), person.getBirthDate());
        assertFalse(person.isDeleted());
    }
}
