package com.softwerke.salesregister.tables.data.dao;

import com.softwerke.salesregister.tables.data.storage.Storage;

abstract class Dao {
    final Storage storage;

    Dao(Storage storage) {
        this.storage = storage;
    }
}
