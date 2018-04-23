package com.softwerke.salesregister.menu.base;

import com.softwerke.salesregister.console.ConsoleIOStream;
import com.softwerke.salesregister.menu.InternalData;

public class Menu {

    protected static InternalData internalData;
    private static int rollback = 0;
    private MenuItem[] actions;
    private String descriptionText;

    protected Menu(String descriptionText, MenuItem... actions) {
        if (actions.length > 9 || actions.length == 0) {
            throw new IllegalArgumentException();
        }
        this.actions = actions;
        this.descriptionText = descriptionText;
    }

    protected static void incrementRollback() {
        rollback++;
    }

    public static void setInternalData(InternalData internalData) {
        Menu.internalData = internalData;
    }

    public void execute() {
        while (true) {
            internalData.ioStream.printLine(descriptionText);
            for (int i = 1; i <= actions.length; i++) {
                internalData.ioStream.printLine(i + " - " + actions[i - 1].getLabel());
            }
            internalData.ioStream.printLine("0 - Return");

            int parsedCommand;
            try {
                parsedCommand = Integer.parseInt(internalData.ioStream.ask());
            } catch (NumberFormatException e) {
                /* Input error */
                internalData.ioStream.printLine(ConsoleIOStream.WRONG_COMMAND_TEXT);
                continue;
            }
            if (parsedCommand == 0) {
                /* Exiting menu */
                return;
            }
            if (parsedCommand > actions.length) {
                /* Input error */
                internalData.ioStream.printLine(ConsoleIOStream.WRONG_COMMAND_TEXT);
                continue;
            }
            actions[parsedCommand - 1].runItem();
            if (rollback > 0) {
                /* Exiting menu */
                rollback--;
                return;
            }
        }
    }
}
