package com.softwerke.menu.menu_items;

import com.softwerke.console.IOPipe;
import com.softwerke.Utils;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;

import java.time.LocalDate;

import static com.softwerke.StringPool.EDIT_PERSON_LIST_COMMANDS;
import static com.softwerke.StringPool.SUCCESSFUL;
import static com.softwerke.menu.menu_items.MenuInternalData.*;

/* Since it should be instantiated after setting the static currentPerson in EditPersonListMenu, it's package private */
class EditPersonListMenu extends Menu {
    EditPersonListMenu() {
        /* Edit person list menu */
        super(new MenuAction[]{
                /* Print person list */
                () -> database.getPersonList().print(),

                /* Add person */
                () -> {
                    String firstName = IOPipe.getNotNullLineByDialog("Enter person's first name:");
                    String lastName = IOPipe.getNotNullLineByDialog("Enter person's last name:");
                    LocalDate birthDate = IOPipe.getLocalDateByDialog("Enter person's birth date (dd/mm/yyyy with any separator):");
                    database.addPerson(firstName, lastName, birthDate);
                    IOPipe.printLine(SUCCESSFUL);
                    searchPersonList = database.getPersonList();
                },

                /* Edit person */
                () -> {
                    currentPerson = Utils.selectPerson(database.getPersonList());
                    if (currentPerson == null) return;
                    new EditPersonMenu().execute();
                },
        }, EDIT_PERSON_LIST_COMMANDS);
    }
}

