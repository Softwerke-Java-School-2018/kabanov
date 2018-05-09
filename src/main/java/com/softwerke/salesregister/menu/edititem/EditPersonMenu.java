package com.softwerke.salesregister.menu.edititem;

import com.softwerke.salesregister.io.StringLiterals;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.person.Person;

import java.time.LocalDate;

public class EditPersonMenu extends Menu {
    public EditPersonMenu() {
        super("-- Edit person menu --",
                new MenuItem("Update first name", () -> {
                    String newName = internalData.ioStream.askNonEmptyString("Enter person's new first name:");
                    Person newPerson = internalData.currentPerson.copyWithNewFirstName(newName);
                    internalData.daoPerson.updatePerson(newPerson);
                    internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                }),

                new MenuItem("Update last name", () -> {
                    String newName = internalData.ioStream.askNonEmptyString("Enter person's new last name:");
                    Person newPerson = internalData.currentPerson.copyWithNewLastName(newName);
                    internalData.daoPerson.updatePerson(newPerson);
                    internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                }),

                new MenuItem("Update birth date", () -> {
                    LocalDate newDate = internalData.ioStream.askLocalDate("Enter person's new birth date (dd/mm/yyyy with any separator):");
                    Person newPerson = internalData.currentPerson.copyWithNewBirthDate(newDate);
                    internalData.daoPerson.updatePerson(newPerson);
                    internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                }),

                new MenuItem("Delete person", () -> {
                    Person newPerson = internalData.currentPerson.copyWithNewIsDeleted(false);
                    internalData.daoPerson.updatePerson(newPerson);
                    internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                    Menu.incrementRollback();
                }));
    }
}
