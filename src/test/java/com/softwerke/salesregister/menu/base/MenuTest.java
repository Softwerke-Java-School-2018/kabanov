package com.softwerke.salesregister.menu.base;

import com.softwerke.salesregister.io.ConsoleIOStream;
import com.softwerke.salesregister.io.StringLiterals;
import com.softwerke.salesregister.menu.InternalData;
import com.softwerke.salesregister.tables.data.dao.DaoDevice;
import com.softwerke.salesregister.tables.data.dao.DaoInvoice;
import com.softwerke.salesregister.tables.data.dao.DaoPerson;
import com.softwerke.salesregister.tables.data.storage.ArrayListStorage;
import com.softwerke.salesregister.tables.data.storage.Storage;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.softwerke.salesregister.io.ConsoleIOStreamTest.STUB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MenuTest {
    private static DaoPerson daoPerson;
    private static DaoDevice daoDevice;
    private static DaoInvoice daoInvoice;
    private static Storage storage;
    private static ConsoleIOStream console;
    private static ByteArrayOutputStream outputStream;

    @BeforeClass
    public static void initStorage() {
        storage = new ArrayListStorage();
        daoPerson = new DaoPerson(storage);
        daoDevice = new DaoDevice(storage);
        daoInvoice = new DaoInvoice(storage);
    }

    private static void initConsoleWithInputText(String input) {
        try {
            outputStream = new ByteArrayOutputStream();
            console = new ConsoleIOStream(IOUtils.toInputStream(input, "UTF-8"), outputStream);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void execute_WrongInput_MessagePrinted() throws UnsupportedEncodingException {
        class TestMenu extends Menu {
            private TestMenu(String descriptionText, MenuItem... actions) {
                super(descriptionText, actions);
            }
        }
        initConsoleWithInputText("Z" + System.lineSeparator() + "9" + System.lineSeparator() + "0");
        Menu.setInternalData(new InternalData(console, daoPerson, daoDevice, daoInvoice));

        TestMenu testMenu = new TestMenu(STUB, new MenuItem(STUB, () -> {
        }));
        testMenu.execute();

        assertEquals(STUB + System.lineSeparator() +
                        "1 - stub!" + System.lineSeparator() +
                        "0 - Return" + System.lineSeparator() + System.lineSeparator() +
                        StringLiterals.WRONG_COMMAND_TEXT + System.lineSeparator() +                // Z char

                        STUB + System.lineSeparator() +
                        "1 - stub!" + System.lineSeparator() +
                        "0 - Return" + System.lineSeparator() + System.lineSeparator() +
                        StringLiterals.WRONG_COMMAND_TEXT + System.lineSeparator() +                // 9 char

                        STUB + System.lineSeparator() +
                        "1 - stub!" + System.lineSeparator() +
                        "0 - Return" + System.lineSeparator() + System.lineSeparator(),             // 0 char
                outputStream.toString("UTF-8"));
    }

    @Test
    public void execute_CorrectInput_Quit() throws UnsupportedEncodingException {
        class TestMenu extends Menu {
            private TestMenu(String descriptionText, MenuItem... actions) {
                super(descriptionText, actions);
            }
        }
        initConsoleWithInputText("0");
        Menu.setInternalData(new InternalData(console, daoPerson, daoDevice, daoInvoice));

        TestMenu testMenu = new TestMenu(STUB, new MenuItem(STUB, () -> {
        }));
        testMenu.execute();

        assertEquals(STUB + System.lineSeparator() +
                        "1 - stub!" + System.lineSeparator() +
                        "0 - Return" + System.lineSeparator() + System.lineSeparator(),             // 0 char
                outputStream.toString("UTF-8"));
    }

    @Test(expected = NullPointerException.class)
    public void execute_InternalDataIsNull_NPE() {
        class TestMenu extends Menu {
            private TestMenu(String descriptionText, MenuItem... actions) {
                super(descriptionText, actions);
            }
        }
        Menu.internalData = null;

        TestMenu testMenu = new TestMenu(STUB, new MenuItem(STUB, () -> {
        }));
        testMenu.execute();
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_MenuItemIsNull_IllegalArgumentException() {
        class TestMenu extends Menu {
            private TestMenu() {
                super(STUB, (MenuItem) null);
            }
        }
        TestMenu testMenu = new TestMenu();
        testMenu.execute();
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_MenuItemArrayIsNull_IllegalArgumentException() {
        class TestMenu extends Menu {
            private TestMenu() {
                super(STUB, (MenuItem[]) null);
            }
        }
        TestMenu testMenu = new TestMenu();
        testMenu.execute();
    }

    @Test(expected = NullPointerException.class)
    public void Menu_DescriptionTextIsNull_NPE() {
        class TestMenu extends Menu {
            private TestMenu(MenuItem... actions) {
                super(null, actions);
            }
        }
        new TestMenu(new MenuItem(STUB, () -> {
        }));
    }
}
