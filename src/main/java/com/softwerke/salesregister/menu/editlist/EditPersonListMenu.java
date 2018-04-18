package com.softwerke.salesregister.menu.editlist;

import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.console.Formatter;
import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.menu.edititem.EditPersonMenu;
import com.softwerke.salesregister.tables.person.Person;

import java.time.LocalDate;

public class EditPersonListMenu extends Menu {
    public EditPersonListMenu() {
        /* Edit person list menu */
        super("-- Edit person list menu --", new MenuItem[]{
                new MenuItem("Print person list") {
                    @Override
                    public void runItem() {
                        Formatter.printFormatPerson(internalData.daoPerson.getPersonStream()
                                .filter(person -> !person.isDeleted()));
                    }
                },

                new MenuItem("Add person") {
                    @Override
                    public void runItem() {
                        String firstName = IOPipe.getNotNullLineByDialog("Enter person's first name:");
                        String lastName = IOPipe.getNotNullLineByDialog("Enter person's last name:");
                        LocalDate birthDate = IOPipe.getLocalDateByDialog("Enter person's birth date (dd/mm/yyyy with any separator):");
                        Person.PersonBuilder builder = new Person.PersonBuilder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .birthDate(birthDate)
                                .isDeleted(false);
                        internalData.daoPerson.addPerson(builder.build());
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Edit person") {
                    @Override
                    public void runItem() {
                        internalData.currentPerson = Utils.selectPerson(internalData.daoPerson.getPersonStream());
                        if (internalData.currentPerson == null) return;
                        new EditPersonMenu().execute();
                    }
                },
        });
    }
}