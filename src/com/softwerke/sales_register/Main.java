package com.softwerke;

import com.softwerke.console.IOPipe;
import com.softwerke.menu.Menu;
import com.softwerke.menu.menu_items.InternalData;
import com.softwerke.menu.menu_items.MainMenu;
import com.softwerke.tables.Database;


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
