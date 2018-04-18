package com.softwerke.salesregister.menu.edititem;

import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.person.Person;

import java.time.LocalDate;

public class EditPersonMenu extends Menu {
    public EditPersonMenu() {
        super("-- Edit person menu --", new MenuItem[]{
                new MenuItem("Update first name") {
                    @Override
                    public void runItem() {
                        String newName = IOPipe.getNotNullLineByDialog("Enter person's new first name:");
                        Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(internalData.currentPerson);
                        internalData.daoPerson.updatePerson(builder.firstName(newName).build());
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Update last name") {
                    @Override
                    public void runItem() {
                        String newName = IOPipe.getNotNullLineByDialog("Enter person's new last name:");
                        Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(internalData.currentPerson);
                        internalData.daoPerson.updatePerson(builder.lastName(newName).build());
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Update birth date") {
                    @Override
                    public void runItem() {
                        LocalDate newDate = IOPipe.getLocalDateByDialog("Enter person's new birth date (dd/mm/yyyy with any separator):");
                        Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(internalData.currentPerson);
                        internalData.daoPerson.updatePerson(builder.birthDate(newDate).build());
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Delete person") {
                    @Override
                    public void runItem() {
                        Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(internalData.currentPerson);
                        internalData.daoPerson.updatePerson(builder.isDeleted(true).build());
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                        Menu.incrementRollback();
                    }
                },
        });
    }
}