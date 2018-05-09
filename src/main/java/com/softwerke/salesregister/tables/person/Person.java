package com.softwerke.salesregister.tables.person;

import com.softwerke.salesregister.io.Logger;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;

public class Person {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String firstNameLowerCase;
    private final String lastNameLowerCase;
    private final LocalDate birthDate;
    private final boolean isDeleted;


    private Person(String firstName, String lastName, LocalDate birthDate, int id, boolean isDeleted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstNameLowerCase = firstName.toLowerCase();
        this.lastNameLowerCase = lastName.toLowerCase();
        this.birthDate = birthDate;
        this.isDeleted = isDeleted;
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

    public String getFullName() {
        return firstName + " " + lastName;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public Person copyWithNewId(int id) {
        return of(id, this.firstName, this.lastName, this.birthDate, this.isDeleted);
    }

    public Person copyWithNewFirstName(String firstName) {
        return of(this.id, firstName, this.lastName, this.birthDate, this.isDeleted);
    }

    public Person copyWithNewLastName(String lastName) {
        return of(this.id, this.firstName, lastName, this.birthDate, this.isDeleted);
    }

    public Person copyWithNewBirthDate(LocalDate birthDate) {
        return of(this.id, this.firstName, this.lastName, birthDate, this.isDeleted);
    }

    public Person copyWithNewIsDeleted(boolean isDeleted) {
        return of(this.id, this.firstName, this.lastName, this.birthDate, isDeleted);
    }

    @Override
    public String toString() {
        return lastName + " " + firstName.charAt(0) + ".";
    }

    public static Person of(int id, String firstName, String lastName, LocalDate birthDate, boolean isDeleted) {
        if (id == -1 || !ObjectUtils.allNotNull(firstName, lastName, birthDate)) {
            Logger.fatal("One or more arguments are invalid! [Person factory]");
            throw new IllegalArgumentException("One or more arguments are invalid!");
        }
        return new Person(firstName, lastName, birthDate, id, isDeleted);
    }
}