package com.softwerke;

import com.softwerke.console.IOPipe;
import com.softwerke.menu.menu_items.MainMenu;
import com.softwerke.tables.Database;

import static com.softwerke.StringPool.USERNAME;

class Main {
    public static void main(String[] args) {
        Database database = new Database();

        /* Setting the static link for database */
        MainMenu.setDatabase(database);

        IOPipe.printLine("Hello, " + USERNAME + ".");

        new MainMenu().execute();
    }
}
