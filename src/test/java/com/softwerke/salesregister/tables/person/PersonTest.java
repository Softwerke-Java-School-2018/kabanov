package com.softwerke.salesregister.tables.person;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class PersonTest {
    @Test(expected = RuntimeException.class)
    public void testPersonBuilderEmpty() {
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
        Person person = builder.build();

        Assert.assertEquals(person.getId(), 2);
        Assert.assertEquals(person.getFirstName(), "Name");
        Assert.assertEquals(person.getLastName(), "Surname");
        Assert.assertEquals(person.getBirthDate(), LocalDate.parse("1981-10-01"));
        Assert.assertFalse(person.isDeleted());
    }
}
