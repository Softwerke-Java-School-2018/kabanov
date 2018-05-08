package com.softwerke.salesregister.tables.person;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;

public class Person {
    public final int id;
    public final String firstName;
    public final String lastName;
    public final String firstNameLowerCase;
    public final String lastNameLowerCase;
    public final LocalDate birthDate;
    public final boolean isDeleted;


    private Person(String firstName, String lastName, LocalDate birthDate, int id, boolean isDeleted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstNameLowerCase = firstName.toLowerCase();
        this.lastNameLowerCase = lastName.toLowerCase();
        this.birthDate = birthDate;
        this.isDeleted = isDeleted;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getShortName() {
        return lastName + " " + firstName.charAt(0) + ".";
    }

    public static class PersonBuilder {
        static int id;
        static String firstName;
        static String lastName;
        static LocalDate birthDate;
        static boolean isDeleted;

        public PersonBuilder() {
            id = -1;
            firstName = null;
            lastName = null;
            birthDate = null;
            isDeleted = true;
        }

        public static PersonBuilder setupFromPerson(Person person) {
            PersonBuilder personBuilder = new PersonBuilder();
            return personBuilder
                    .id(person.id)
                    .firstName(person.firstName)
                    .lastName(person.lastName)
                    .birthDate(person.birthDate)
                    .isDeleted(person.isDeleted);
        }

        public PersonBuilder id(int id) {
            PersonBuilder.id = id;
            return this;
        }

        public PersonBuilder firstName(String firstName) {
            PersonBuilder.firstName = firstName;
            return this;
        }

        public PersonBuilder lastName(String lastName) {
            PersonBuilder.lastName = lastName;
            return this;
        }

        public PersonBuilder birthDate(LocalDate birthDate) {
            PersonBuilder.birthDate = birthDate;
            return this;
        }

        public PersonBuilder isDeleted(boolean isDeleted) {
            PersonBuilder.isDeleted = isDeleted;
            return this;
        }

        public Person build() throws BuilderNotInitializedException {
            if (id == -1 || !ObjectUtils.allNotNull(firstName, lastName, birthDate)) {
                throw new BuilderNotInitializedException("Builder isn't filled!");
            }
            return new Person(firstName, lastName, birthDate, id, isDeleted);
        }
    }
}