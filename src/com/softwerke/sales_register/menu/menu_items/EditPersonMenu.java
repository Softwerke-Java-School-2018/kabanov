package com.softwerke.menu.menu_items;

import com.softwerke.console.IOPipe;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuAction;
import com.softwerke.tables.Person;

import java.time.LocalDate;

import static com.softwerke.StringPool.EDIT_PERSON_COMMANDS;
import static com.softwerke.StringPool.SUCCESSFUL;
import static com.softwerke.menu.menu_items.MenuInternalData.currentPerson;
import static com.softwerke.menu.menu_items.MenuInternalData.database;

class EditPersonMenu extends Menu {
    EditPersonMenu() {
        super(new MenuAction[]{
                /* Update first name menu item */
                () -> {
                    String newName = IOPipe.getNotNullLineByDialog("Enter person's new first name:");
                    database.updatePerson(currentPerson.setFirstName(newName));
                    IOPipe.printLine(SUCCESSFUL);
                },

                /* Update last name */
                () -> {
                    String newName = IOPipe.getNotNullLineByDialog("Enter person's new last name:");
                    database.updatePerson(currentPerson.setLastName(newName));
                    IOPipe.printLine(SUCCESSFUL);
                },

                /* Update birth date */
                () -> {
                    LocalDate newDate = IOPipe.getLocalDateByDialog("Enter person's new birth date (dd/mm/yyyy with any separator):");
                    database.updatePerson(currentPerson.setBirthDate(newDate));
                    IOPipe.printLine(SUCCESSFUL);
                },

                /* Delete person */
                () -> {
                    database.updatePerson(currentPerson.getId(), Person.DELETED_PERSON);
                    IOPipe.printLine(SUCCESSFUL);
                    Menu.incrementRollback();
                },
        }, EDIT_PERSON_COMMANDS);
    }

}
