package com.softwerke.masklist;

import com.softwerke.tables.Person;

import java.util.ArrayList;

import static com.softwerke.console.ConsolePipe.PRESS_ANYKEY_TEXT;
import static com.softwerke.console.ConsolePipe.getLineByDialog;

public class PersonList extends ArrayList<Person> {
    public PersonList maskByPersonFirstName(String nameToMask) {
        nameToMask = nameToMask.toLowerCase();
        if (nameToMask.trim().equals("*")) return this;
        PersonList returning = new PersonList();
        for (Person person : this) {
            String personFirstName = person.getFirstName().toLowerCase();
            if (personFirstName.contains(nameToMask))
                returning.add(person);
        }
        return returning;
    }

    public PersonList maskByPersonLastName(String nameToMask) {
        nameToMask = nameToMask.toLowerCase();
        if (nameToMask.trim().equals("*")) return this;
        PersonList returning = new PersonList();
        for (Person person : this) {
            String personLastName = person.getLastName().toLowerCase();
            if (personLastName.contains(nameToMask))
                returning.add(person);
        }
        return returning;
    }

    PersonList maskByPersonBirthDate(String from, String to) {
        return null;
    }

    public void merge(PersonList mergeList) {
        for (Person person : mergeList)
            /* If merging list contains persons with ID this list missing - add them */
            if (getById(person.getId()) == null)
                add(person);
    }

    public void print() {
        if (isEmpty()) {
            System.out.println();           /* Separating blank line */
            System.out.println("List is empty.");
            return;
        }
        System.out.println();           /* Separating blank line */
        System.out.println(" ID |            Name           | Birth date");
        System.out.println("--------------------------------------------");
        for (Person person : this)
            System.out.println(person.toFormattedString());
        getLineByDialog(PRESS_ANYKEY_TEXT);
    }

    @Override
    public PersonList clone() {
        PersonList returning = new PersonList();
        returning.addAll(this);
        return returning;
    }

    private Person getById(int id) {
        for (Person person : this)
            if (person.getId() == id) return person;
        return null;
    }
}
