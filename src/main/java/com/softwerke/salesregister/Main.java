package com.softwerke.salesregister;

import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.InternalData;
import com.softwerke.salesregister.menu.MainMenu;
import com.softwerke.salesregister.tables.data.Database;


class Main {
    public static final String USERNAME = "%username%";

    public static void main(String[] args) {
        Database database = new Database();

        /* Setting the link for database */
        Menu.setInternalData(new InternalData(database));

        IOPipe.printLine("Hello, " + USERNAME + ".");

        new MainMenu().execute();
    }
}
