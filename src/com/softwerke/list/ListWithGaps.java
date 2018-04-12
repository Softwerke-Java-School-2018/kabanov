package com.softwerke.list;

import java.util.ArrayList;

public abstract class ListWithGaps<T extends HasId> extends ArrayList<T> {

    @Override
    public abstract ListWithGaps<T> clone();

    public int getNotDeletedItemsNumber() {
        int i = 0;
        for (T t : this)
            if (t.getId() != -1) i++;
        return i;
    }
}
