package com.softwerke.salesregister.tables.data.dao;

import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.person.Person;

import java.util.List;
import java.util.stream.Stream;

public class DaoPerson extends Dao {
    public DaoPerson(Storage storage) {
        super(storage);
    }

    public void addPerson(Person person) {
        List<Person> personList = storage.getPersonList();
        personList.add(person);
    }

    public void updatePerson(Person person) {
        storage.getPersonList().set(person.getId(), person);
    }

    public Person getPerson(int i) {
        return storage.getPersonList().get(i);
    }

    public int getSize() {
        return storage.getPersonList().size();
    }

    public Stream<Person> getPersonStream() {
        return storage.getPersonList().stream();
    }
}
