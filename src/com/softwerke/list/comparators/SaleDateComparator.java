package com.softwerke.list.comparators;

import com.softwerke.tables.Sale;

public class SaleDateComparator extends SortOrderComparator<Sale> {

    public SaleDateComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Sale sale1, Sale sale2) {
        int comparisonResult = sale1.getSaleDate().compareTo(sale2.getSaleDate());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}