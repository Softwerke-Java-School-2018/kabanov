package com.softwerke.salesregister.menu.base;

import com.softwerke.salesregister.io.ConsoleIOStream;
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

import static com.softwerke.salesregister.io.ConsoleIOStreamTest.STUB;
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
    public void menuTest() {
        class TestMenu extends Menu {
            private TestMenu(String descriptionText, MenuItem... actions) {
                super(descriptionText, actions);
            }
        }
        initConsoleWithInputText("Z" + System.lineSeparator() + "9" +  System.lineSeparator() + "0");
        Menu.setInternalData(new InternalData(console, daoPerson, daoDevice, daoInvoice));

        TestMenu testMenu = new TestMenu(STUB, new MenuItem(STUB, () -> {
        }));
        testMenu.execute();
    }

    @Test
    public void menuTestRollback() {
        class TestMenu extends Menu {
            private TestMenu(String descriptionText, MenuItem... actions) {
                super(descriptionText, actions);
            }

            private void incrementRollbackTest() {
                incrementRollback();
            }
        }
        initConsoleWithInputText("1");
        Menu.setInternalData(new InternalData(console, daoPerson, daoDevice, daoInvoice));

        TestMenu testMenu = new TestMenu(STUB, new MenuItem(STUB, () -> {
        }));
        testMenu.incrementRollbackTest();
        testMenu.execute();
    }

    @Test(expected = NullPointerException.class)
    public void menuTestForgotInternalData() {
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
    public void menuTestNull0() {
        class TestMenu extends Menu {
            private TestMenu() {
                super(STUB, (MenuItem) null);
            }
        }
        TestMenu testMenu = new TestMenu();
        testMenu.execute();
    }

    @Test(expected = IllegalArgumentException.class)
    public void menuTestNull1() {
        class TestMenu extends Menu {
            private TestMenu() {
                super(STUB, (MenuItem[]) null);
            }
        }
        TestMenu testMenu = new TestMenu();
        testMenu.execute();
    }

    @Test(expected = NullPointerException.class)
    public void menuTestNull2() {
        class TestMenu extends Menu {
            private TestMenu(MenuItem... actions) {
                super(null, actions);
            }
        }
        TestMenu testMenu = new TestMenu(new MenuItem(STUB, () -> {
        }));
        testMenu.execute();
    }
}
