package com.softwerke.salesregister.menu.editlist;

import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.io.ConsoleIOStream;
import com.softwerke.salesregister.io.Formatter;
import com.softwerke.salesregister.io.StringLiterals;
import com.softwerke.salesregister.menu.base.Menu;
import com.softwerke.salesregister.menu.base.MenuItem;
import com.softwerke.salesregister.menu.edititem.EditPersonMenu;
import com.softwerke.salesregister.tables.person.Person;

import java.time.LocalDate;
import java.util.Objects;

public class EditPersonListMenu extends Menu {
    public EditPersonListMenu() {
        super("-- Edit person list menu --",
                new MenuItem("Print person list",
                        () -> Formatter.printFormatPerson(internalData.daoPerson.persons()
                                .filter(person -> !person.isDeleted()), internalData.ioStream)),

                new MenuItem("Add person", () -> {
                    String firstName = internalData.ioStream.askNonEmptyString("Enter person's first name:");
                    String lastName = internalData.ioStream.askNonEmptyString("Enter person's last name:");
                    LocalDate birthDate = internalData.ioStream.askLocalDate("Enter person's birth date (dd/mm/yyyy with any separator):");
                    Person personToAdd =
                            Person.of(internalData.daoPerson.getSize(), firstName, lastName, birthDate, false);
                    internalData.daoPerson.addPerson(personToAdd);
                    internalData.ioStream.printLine(StringLiterals.SUCCESSFUL);
                }),

                new MenuItem("Edit person", () -> {
                    internalData.currentPerson =
                            Utils.selectPerson(internalData.daoPerson.persons(), internalData.ioStream);
                    if (Objects.isNull(internalData.currentPerson)) {
                        return;
                    }
                    new EditPersonMenu().execute();
                }));
    }
}