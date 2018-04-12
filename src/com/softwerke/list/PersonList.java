package com.softwerke.list;

import com.softwerke.Utils;
import com.softwerke.console.IOPipe;
import com.softwerke.tables.Person;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.softwerke.StringPool.LIST_IS_EMPTY_TEXT;
import static com.softwerke.StringPool.PRESS_ANYKEY_TEXT;

public class PersonList extends ListWithGaps<Person> {
    public PersonList maskByPersonId(String from, String to) {
        int fromId = Integer.parseInt(from);
        int toId = Integer.parseInt(to);
        PersonList returning = this.clone();
        returning.removeIf(x -> !Utils.inBetween(fromId, x.getId(), toId));
        return returning;
    }

    public PersonList maskByPersonFirstName(String nameToMask) {
        if (nameToMask.trim().equals("*")) return this;
        String nameLowerCase = nameToMask.toLowerCase();
        PersonList returning = this.clone();
        returning.removeIf(x -> !x.getFirstNameLowerCase().contains(nameLowerCase));
        return returning;
    }

    public PersonList maskByPersonLastName(String nameToMask) {
        if (nameToMask.trim().equals("*")) return this;
        String nameLowerCase = nameToMask.toLowerCase();
        PersonList returning = this.clone();
        returning.removeIf(x -> !x.getLastNameLowerCase().contains(nameLowerCase));
        return returning;
    }

    public PersonList maskByPersonBirthDate(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from.replaceAll("\\D+", "-"),
                DateTimeFormatter.ofPattern("d-MM-yyyy"));
        LocalDate toDate = LocalDate.parse(to.replaceAll("\\D+", "-"),
                DateTimeFormatter.ofPattern("d-MM-yyyy"));
        PersonList returning = this.clone();
        returning.removeIf(x -> !Utils.inBetween(fromDate, x.getBirthDate(), toDate));
        return returning;
    }

    public void merge(PersonList mergeList) {
        for (Person person : mergeList)
            /* If merging list contains persons with ID this list missing - add them */
            if (getById(person.getId()) == null)
                add(person);
    }

    public void print() {
        removeIf(x -> x.getId() == -1);
        if (isEmpty()) {
            IOPipe.printLine();                         /* Separating blank line */
            IOPipe.printLine(LIST_IS_EMPTY_TEXT);
            return;
        }
        IOPipe.printLine();                             /* Separating blank line */
        IOPipe.printLine(" ID |            Name           | Birth date");
        IOPipe.printLine("--------------------------------------------");
        for (Person person : this)
                IOPipe.printLine(person.toFormattedString());
        IOPipe.getLineByDialog(PRESS_ANYKEY_TEXT);
    }

    private Person getById(int id) {
        for (Person person : this)
            if (person.getId() == id) return person;
        return null;
    }

    @Override
    public PersonList clone() {
        PersonList returning = new PersonList();
        returning.addAll(this);
        return returning;
    }
}
