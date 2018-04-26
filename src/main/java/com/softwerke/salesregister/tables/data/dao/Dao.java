package com.softwerke.salesregister.tables.data.dao;

import com.softwerke.salesregister.tables.data.storage.Storage;

import java.util.Objects;

abstract class Dao {
    final Storage storage;

    Dao(Storage storage) {
        this.storage = Objects.requireNonNull(storage);
    }
}
