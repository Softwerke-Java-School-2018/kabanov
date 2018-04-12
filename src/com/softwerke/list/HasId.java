package com.softwerke.list;

public abstract class HasId {
    protected final int id;

    protected HasId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
