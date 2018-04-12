package com.softwerke.list.comparators;

import com.softwerke.tables.Sale;

public class SaleTotalComparator extends SortOrderComparator<Sale> {

    public SaleTotalComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Sale sale1, Sale sale2) {
        int comparisonResult = sale1.getTotalSum().compareTo(sale2.getTotalSum());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}