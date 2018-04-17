package com.softwerke.salesregister.menu;

public abstract class MenuItem {
    final String label;

    protected MenuItem(String label) {
        this.label = label;
    }

    protected abstract void runItem();
}