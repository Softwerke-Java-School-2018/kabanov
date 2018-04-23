package com.softwerke.salesregister;

import com.softwerke.salesregister.console.ConsoleIOStream;
import com.softwerke.salesregister.console.IOStream;
import com.softwerke.salesregister.menu.InternalData;
import com.softwerke.salesregister.menu.MainMenu;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.tables.data.dao.DaoDevice;
import com.softwerke.salesregister.tables.data.dao.DaoInvoice;
import com.softwerke.salesregister.tables.data.dao.DaoPerson;
import com.softwerke.salesregister.tables.data.storage.ArrayListStorage;
import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.data.storage.StorageInitializer;


class Main {
    public static final String USERNAME = "%username%";

    public static void main(String[] args) {
        IOStream ioStream = new ConsoleIOStream(System.in, System.out);

        Storage storage = new ArrayListStorage();
        DaoPerson daoPerson = new DaoPerson(storage);
        DaoDevice daoDevice = new DaoDevice(storage);
        DaoInvoice daoInvoice = new DaoInvoice(storage);
        new StorageInitializer(daoPerson, daoDevice, daoInvoice);

        /* Setting the link for database */
        Menu.setInternalData(new InternalData(ioStream, daoPerson, daoDevice, daoInvoice));

        ioStream.printLine("Hello, " + USERNAME + ".");

        new MainMenu().execute();
    }
}
