package com.softwerke.menu.menu_items;

import com.softwerke.Utils;
import com.softwerke.console.Formatter;
import com.softwerke.console.IOPipe;
import com.softwerke.menu.Menu;
import com.softwerke.menu.MenuItem;

import java.time.LocalDate;

class EditPersonListMenu extends Menu {
    EditPersonListMenu() {
        /* Edit person list menu */
        super("-- Edit person list menu --", new MenuItem[]{
                new MenuItem("Print person list") {
                    @Override
                    public void runItem() {
                        Formatter.printFormatPerson(
                                internalData.database.getPersonStream().filter(person -> person.getId() != -1));
                    }
                },

                new MenuItem("Add person") {
                    @Override
                    public void runItem() {
                        String firstName = IOPipe.getNotNullLineByDialog("Enter person's first name:");
                        String lastName = IOPipe.getNotNullLineByDialog("Enter person's last name:");
                        LocalDate birthDate = IOPipe.getLocalDateByDialog("Enter person's birth date (dd/mm/yyyy with any separator):");
                        internalData.database.addPerson(firstName, lastName, birthDate);
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Edit person") {
                    @Override
                    public void runItem() {
                        internalData.currentPerson = Utils.selectPerson(internalData.database.getPersonStream());
                        if (internalData.currentPerson == null) return;
                        new EditPersonMenu().execute();
                    }
                },
        });
    }
}