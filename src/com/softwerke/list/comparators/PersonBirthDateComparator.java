package com.softwerke.list.comparators;

import com.softwerke.tables.Person;

public class PersonBirthDateComparator extends SortOrderComparator<Person> {

    public PersonBirthDateComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Person person1, Person person2) {
        int comparisonResult = person1.getBirthDate().compareTo(person2.getBirthDate());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}