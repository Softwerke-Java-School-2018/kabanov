package com.softwerke.menu;

import static com.softwerke.console.ConsolePipe.*;

class Menu {

    private MenuAction[] actions;
    private String[] descriptionText;

    Menu(MenuAction[] actions, String[] descriptionText) {
        if (actions.length > 9) throw new IllegalArgumentException();
        this.actions = actions;
        this.descriptionText = descriptionText;
    }

    public void executeCommand() {
        while (true) {
            printStringArray(descriptionText);
            String command = getCommand();
            int parsedCommand;
            try {
                parsedCommand = Integer.parseInt(command);
            } catch (NumberFormatException e) {
                /* Input error */
                printWithDelay(WRONG_COMMAND_TEXT);
                continue;
            }
            if (parsedCommand == 0) {
                /* Exiting menu */
                break;
            }
            if (parsedCommand > actions.length) {
                /* Input error */
                printWithDelay(WRONG_COMMAND_TEXT);
                continue;
            }
            actions[parsedCommand - 1].runItem();
        }
    }

}
