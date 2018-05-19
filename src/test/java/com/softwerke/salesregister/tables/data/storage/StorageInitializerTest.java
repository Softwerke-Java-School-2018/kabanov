package com.softwerke.salesregister.tables.data.storage;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.tables.data.dao.DaoDevice;
import com.softwerke.salesregister.tables.data.dao.DaoInvoice;
import com.softwerke.salesregister.tables.data.dao.DaoPerson;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageInitializerTest {
    private static DaoPerson daoPerson;
    private static DaoDevice daoDevice;
    private static DaoInvoice daoInvoice;

    @BeforeClass
    public static void initStorage() {
        Storage storage = new ArrayListStorage();
        daoPerson = new DaoPerson(storage);
        daoDevice = new DaoDevice(storage);
        daoInvoice = new DaoInvoice(storage);
    }

    @Test(expected = IllegalArgumentException.class)
    public void StorageInitializer_Nulls_IllegalArgumentException() throws BuilderNotInitializedException {
        new StorageInitializer(null, null, null);
    }

    @Test
    public void StorageInitializer_ValidDAOs_Success() throws BuilderNotInitializedException {
        new StorageInitializer(daoPerson, daoDevice, daoInvoice);
    }
}
