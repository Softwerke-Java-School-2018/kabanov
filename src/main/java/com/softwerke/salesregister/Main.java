package com.softwerke.salesregister;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.io.ConsoleIOStream;
import com.softwerke.salesregister.io.IOStream;
import com.softwerke.salesregister.io.Logger;
import com.softwerke.salesregister.io.StringLiterals;
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
    public static void main(String[] args) {
        IOStream ioStream = new ConsoleIOStream(System.in, System.out);

        Storage storage = new ArrayListStorage();
        DaoPerson daoPerson = new DaoPerson(storage);
        DaoDevice daoDevice = new DaoDevice(storage);
        DaoInvoice daoInvoice = new DaoInvoice(storage);
        try {
            new StorageInitializer(daoPerson, daoDevice, daoInvoice);
        } catch (BuilderNotInitializedException e) {
            Logger.fatal("Builder in is not initialized! [main - StorageInitializer]" + e);
            ioStream.printLine(StringLiterals.PROGRAM_ERROR);
            return;
        }

        /* Setting the link for database */
        Menu.setInternalData(new InternalData(ioStream, daoPerson, daoDevice, daoInvoice));

        ioStream.printLine("Hello, " + StringLiterals.USERNAME + ".");

        new MainMenu().execute();
    }
}
