package com.softwerke.list.comparators;

import com.softwerke.list.HasId;

public class HasIdComparator<T extends HasId> extends SortOrderComparator<T> {

    public HasIdComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(T t1, T t2) {
        int comparisonResult = Integer.compare(t1.getId(), t2.getId());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}