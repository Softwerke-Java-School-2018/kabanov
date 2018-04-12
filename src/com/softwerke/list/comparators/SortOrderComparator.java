package com.softwerke.list.comparators;

import java.util.Comparator;

abstract class SortOrderComparator<T> implements Comparator<T> {
    final boolean isOrderAscending;

    SortOrderComparator(boolean isOrderAscending) {
        this.isOrderAscending = isOrderAscending;
    }
}
