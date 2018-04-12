package com.softwerke.list.comparators;

import com.softwerke.tables.Person;

public class PersonFirstNameComparator extends SortOrderComparator<Person> {

    public PersonFirstNameComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Person person1, Person person2) {
        int comparisonResult = person1.getFirstName().compareToIgnoreCase(person2.getFirstName());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}