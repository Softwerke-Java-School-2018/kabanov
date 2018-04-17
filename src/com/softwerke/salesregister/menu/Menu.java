package com.softwerke.salesregister.menu;

import com.softwerke.salesregister.console.IOPipe;
import com.softwerke.salesregister.menu.menuitems.InternalData;

public class Menu {

    private MenuItem[] actions;
    private String descriptionText;
    protected static InternalData internalData;
    private static int rollback = 0;

    protected Menu(String descriptionText, MenuItem[] actions) {
        if (actions.length > 9) throw new IllegalArgumentException();
        this.actions = actions;
        this.descriptionText = descriptionText;
    }

    public void execute() {
        while (true) {
            IOPipe.printLine(descriptionText);
            for (int i = 1; i <= actions.length; i++) IOPipe.printLine(i + " - " + actions[i - 1].label);
            IOPipe.printLine("0 - Return");

            String command = IOPipe.getCommand();
            int parsedCommand;
            try {
                parsedCommand = Integer.parseInt(command);
            } catch (NumberFormatException e) {
                /* Input error */
                IOPipe.printLine(IOPipe.WRONG_COMMAND_TEXT);
                continue;
            }
            if (parsedCommand == 0) {
                /* Exiting menu */
                return;
            }
            if (parsedCommand > actions.length) {
                /* Input error */
                IOPipe.printLine(IOPipe.WRONG_COMMAND_TEXT);
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

    protected static void incrementRollback() {
        rollback++;
    }

    public static void setInternalData(InternalData internalData) {
        Menu.internalData = internalData;
    }
}
