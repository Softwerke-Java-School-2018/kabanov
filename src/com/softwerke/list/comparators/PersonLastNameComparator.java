package com.softwerke.list.comparators;

import com.softwerke.tables.Person;

public class PersonLastNameComparator extends SortOrderComparator<Person> {

    public PersonLastNameComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Person person1, Person person2) {
        int comparisonResult = person1.getLastName().compareToIgnoreCase(person2.getLastName());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}