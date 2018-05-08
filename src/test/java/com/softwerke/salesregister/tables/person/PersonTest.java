package com.softwerke.salesregister.tables.person;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PersonTest {
    @Test(expected = BuilderNotInitializedException.class)
    public void testPersonBuilderEmpty() throws BuilderNotInitializedException {
        Person.PersonBuilder builder = new Person.PersonBuilder();
        builder.id(2);
        builder.build();
    }

    @Test
    public void testPersonBuilder() {
        Person.PersonBuilder builder = new Person.PersonBuilder();
        builder.id(2)
                .firstName("Name")
                .lastName("Surname")
                .birthDate(LocalDate.of(1981, 10, 1))
                .isDeleted(false);

        Person person = null;
        try {
            person = builder.build();
        } catch (BuilderNotInitializedException e) {
            fail();
        }

        assertEquals(person.id, 2);
        assertEquals(person.firstName, "Name");
        assertEquals(person.lastName, "Surname");
        assertEquals(person.birthDate, LocalDate.parse("1981-10-01"));
        assertFalse(person.isDeleted);
    }
}
