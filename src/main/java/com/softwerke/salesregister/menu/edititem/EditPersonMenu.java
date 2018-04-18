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
                        internalData.currentPerson = internalData.currentPerson.cloneWithNewFirstName(newName);
                        internalData.database.updatePerson(internalData.currentPerson);
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Update last name") {
                    @Override
                    public void runItem() {
                        String newName = IOPipe.getNotNullLineByDialog("Enter person's new last name:");
                        internalData.currentPerson = internalData.currentPerson.cloneWithNewLastName(newName);
                        internalData.database.updatePerson(internalData.currentPerson);
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Update birth date") {
                    @Override
                    public void runItem() {
                        LocalDate newDate = IOPipe.getLocalDateByDialog("Enter person's new birth date (dd/mm/yyyy with any separator):");
                        internalData.currentPerson = internalData.currentPerson.cloneWithNewBirthDate(newDate);
                        internalData.database.updatePerson(internalData.currentPerson);
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                    }
                },

                new MenuItem("Delete person") {
                    @Override
                    public void runItem() {
                        internalData.database.updatePerson(internalData.currentPerson.getId(), Person.DELETED_PERSON);
                        IOPipe.printLine(IOPipe.SUCCESSFUL);
                        Menu.incrementRollback();
                    }
                },
        });
    }
}