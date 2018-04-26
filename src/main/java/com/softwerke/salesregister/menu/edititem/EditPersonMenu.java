package com.softwerke.salesregister.menu.edititem;

import com.softwerke.salesregister.console.ConsoleIOStream;
import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.tables.person.Person;

import java.time.LocalDate;

public class EditPersonMenu extends Menu {
    public EditPersonMenu() {
        super("-- Edit person menu --",
                new MenuItem("Update first name", () -> {
                    String newName = internalData.ioStream.askNonEmptyString("Enter person's new first name:");
                    Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(internalData.currentPerson);
                    try {
                        internalData.daoPerson.updatePerson(builder.firstName(newName).build());
                        internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                    } catch (BuilderNotInitializedException e) {
                        // internalData.ioStream.logSomething();
                        internalData.ioStream.printLine(ConsoleIOStream.PROGRAM_ERROR);
                    }
                }),

                new MenuItem("Update last name", () -> {
                    String newName = internalData.ioStream.askNonEmptyString("Enter person's new last name:");
                    Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(internalData.currentPerson);
                    try {
                        internalData.daoPerson.updatePerson(builder.lastName(newName).build());
                        internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                    } catch (BuilderNotInitializedException e) {
                        // internalData.ioStream.logSomething();
                        internalData.ioStream.printLine(ConsoleIOStream.PROGRAM_ERROR);
                    }
                }),

                new MenuItem("Update birth date", () -> {
                    LocalDate newDate = internalData.ioStream.askLocalDate("Enter person's new birth date (dd/mm/yyyy with any separator):");
                    Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(internalData.currentPerson);
                    try {
                        internalData.daoPerson.updatePerson(builder.birthDate(newDate).build());
                        internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                    } catch (BuilderNotInitializedException e) {
                        // internalData.ioStream.logSomething();
                        internalData.ioStream.printLine(ConsoleIOStream.PROGRAM_ERROR);
                    }
                }),

                new MenuItem("Delete person", () -> {
                    Person.PersonBuilder builder = Person.PersonBuilder.setupFromPerson(internalData.currentPerson);
                    try {
                        internalData.daoPerson.updatePerson(builder.isDeleted(true).build());
                        internalData.ioStream.printLine(ConsoleIOStream.SUCCESSFUL);
                        Menu.incrementRollback();
                    } catch (BuilderNotInitializedException e) {
                        // internalData.ioStream.logSomething();
                        internalData.ioStream.printLine(ConsoleIOStream.PROGRAM_ERROR);
                    }
                }));
    }
}
