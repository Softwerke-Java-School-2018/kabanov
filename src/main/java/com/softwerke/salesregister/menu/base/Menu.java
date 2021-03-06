package com.softwerke.salesregister.menu.base;

import com.softwerke.salesregister.io.StringLiterals;
import com.softwerke.salesregister.menu.InternalData;

import java.util.Arrays;
import java.util.Objects;

public class Menu {
    protected static InternalData internalData;
    private static int rollback = 0;
    private MenuItem[] actions;
    private String descriptionText;

    protected Menu(String descriptionText, MenuItem... actions) {
        if (Objects.isNull(actions) || actions.length > 9) {
            throw new IllegalArgumentException();
        }
        actions = Arrays.stream(actions).filter(Objects::nonNull).toArray(MenuItem[]::new);
        if (actions.length == 0) {
            throw new IllegalArgumentException();
        }
        this.descriptionText = Objects.requireNonNull(descriptionText);
        this.actions = actions;
    }

    protected static void incrementRollback() {
        rollback++;
    }

    public static void setInternalData(InternalData internalData) {
        Menu.internalData = Objects.requireNonNull(internalData);
    }

    public void execute() {
        if (Objects.isNull(Menu.internalData)) {
            throw new NullPointerException("Internal data is not set!");
        }
        while (true) {
            /* Print menu labels */
            internalData.ioStream.printLine(descriptionText);
            for (int i = 1; i <= actions.length; i++) {
                internalData.ioStream.printLine(i + " - " + actions[i - 1].getLabel());
            }
            internalData.ioStream.printLine("0 - Return");

            int parsedCommand;
            try {
                parsedCommand = Integer.parseInt(internalData.ioStream.ask());
                if (parsedCommand > actions.length || parsedCommand < 0) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                /* Input error */
                internalData.ioStream.printLine(StringLiterals.WRONG_COMMAND_TEXT);
                continue;
            }
            if (parsedCommand == 0) {
                return;                                     /* Entered "0", exit menu */
            }
            actions[parsedCommand - 1].runItem();           /* Execute menu item */
            if (rollback > 0) {
                rollback--;                                 /* Exiting menu */
                return;
            }
        }
    }
}
