package com.softwerke.salesregister.tables;

import java.time.LocalDate;

public class Person {
    public static final Person DELETED_PERSON = new Person("N/A", "N/A", LocalDate.now(), -1);
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String firstNameLowerCase;
    private final String lastNameLowerCase;
    private final LocalDate birthDate;


    public Person(String firstName, String lastName, LocalDate birthDate, int id) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstNameLowerCase = firstName.toLowerCase();
        this.lastNameLowerCase = lastName.toLowerCase();
        this.birthDate = birthDate;
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

    public String getFirstNameLowerCase() {
        return firstNameLowerCase;
    }

    public String getLastNameLowerCase() {
        return lastNameLowerCase;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Person cloneWithNewFirstName(String newName) {
        return new Person(newName, lastName, birthDate, id);
    }

    public Person cloneWithNewLastName(String newName) {
        return new Person(firstName, newName, birthDate, id);
    }

    public Person cloneWithNewBirthDate(LocalDate newDateParsed) {
        return new Person(firstName, lastName, newDateParsed, id);
    }

    @Override
    public String toString() {
        return firstName.charAt(0) + ". " + lastName;
    }
}