package com.softwerke.salesregister.menu.base;

public class MenuItem {
    private final String label;
    private final Runnable menuAction;

    public MenuItem(String label, Runnable menuAction) {
        this.label = label;
        this.menuAction = menuAction;
    }

    void runItem() {
        menuAction.run();
    }

    String getLabel() {
        return label;
    }
}