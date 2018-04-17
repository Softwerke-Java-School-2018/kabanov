package com.softwerke.salesregister;

import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.Menu;
import com.softwerke.salesregister.menu.menuitems.InternalData;
import com.softwerke.salesregister.menu.menuitems.MainMenu;
import com.softwerke.salesregister.tables.Database;


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
