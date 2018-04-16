package com.softwerke.tables;

import com.softwerke.list.HasId;

import java.time.LocalDate;

import static com.softwerke.Utils.leftPad;

/* Immutable */
public class Person extends HasId {
    public static final Person DELETED_PERSON = new Person("N/A", "N/A", LocalDate.now(), -1);
    private final String firstName;
    private final String lastName;
    private final String firstNameLowerCase;
    private final String lastNameLowerCase;
    private final LocalDate birthDate;

    public Person(String firstName, String lastName, LocalDate birthDate, int id) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstNameLowerCase = firstName.toLowerCase();
        this.lastNameLowerCase = lastName.toLowerCase();
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstNameLowerCase() {
        return firstNameLowerCase;
    }

    public String getLastNameLowerCase() {
        return lastNameLowerCase;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Person setFirstName(String newName) {
        return new Person(newName, lastName, birthDate, id);
    }

    public Person setLastName(String newName) {
        return new Person(firstName, newName, birthDate, id);
    }

    public Person setBirthDate(LocalDate newDateParsed) {
        return new Person(firstName, lastName, newDateParsed, id);
    }
}
