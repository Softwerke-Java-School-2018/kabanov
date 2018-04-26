package com.softwerke.salesregister.menu.base;

import java.util.Objects;

public class MenuItem {
    private final String label;
    private final Runnable menuAction;

    public MenuItem(String label, Runnable menuAction) {
        this.label = Objects.requireNonNull(label);
        this.menuAction = Objects.requireNonNull(menuAction);
    }

    void runItem() {
        menuAction.run();
    }

    String getLabel() {
        return label;
    }
}