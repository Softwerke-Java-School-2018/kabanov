package com.softwerke.salesregister.tables.data.dao;

import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.person.Person;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DaoPerson extends Dao {
    public DaoPerson(Storage storage) {
        super(storage);
    }

    public void addPerson(Person person) {
        List<Person> personList = storage.getPersonList();
        personList.add(Objects.requireNonNull(person));
    }

    public void updatePerson(Person person) {
        Objects.requireNonNull(person);
        storage.getPersonList().set(person.id, person);
    }

    public Person getPerson(int i) {
        return storage.getPersonList().get(i);
    }

    public int getSize() {
        return storage.getPersonList().size();
    }

    public Stream<Person> persons() {
        return storage.getPersonList().stream();
    }

    public void clearStorage() {
        storage.getPersonList().clear();
    }
}
