package com.softwerke.menu;

import com.softwerke.console.IOPipe;

import static com.softwerke.StringPool.WRONG_COMMAND_TEXT;

public class Menu {

    private MenuAction[] actions;
    private String[] descriptionText;
    private static int rollback = 0;

    protected Menu(MenuAction[] actions, String[] descriptionText) {
        if (actions.length > 9) throw new IllegalArgumentException();
        this.actions = actions;
        this.descriptionText = descriptionText;
    }

    protected static void incrementRollback() {
        rollback++;
    }

    public void execute() {
        while (true) {
            IOPipe.printStringArray(descriptionText);
            String command = IOPipe.getCommand();
            int parsedCommand;
            try {
                parsedCommand = Integer.parseInt(command);
            } catch (NumberFormatException e) {
                /* Input error */
                IOPipe.printLine(WRONG_COMMAND_TEXT);
                continue;
            }
            if (parsedCommand == 0) {
                /* Exiting menu */
                break;
            }
            if (parsedCommand > actions.length) {
                /* Input error */
                IOPipe.printLine(WRONG_COMMAND_TEXT);
                continue;
            }
            actions[parsedCommand - 1].runItem();
            if (rollback > 0) {
                rollback--;
                return;
            }
        }
    }

}
