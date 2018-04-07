package com.softwerke.tables;

import java.time.LocalDate;

import static com.softwerke.masklist.Utils.leftPad;

/* Immutable */
public class Person {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;

    public Person(String firstName, String lastName, LocalDate birthDate, int id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return firstName.charAt(0) + ". " + lastName;
    }

    public String toFormattedString() {
        return leftPad(String.valueOf(id), 3) + " | " +
                leftPad(firstName + " " + lastName, 25) + " | " +
                birthDate;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
