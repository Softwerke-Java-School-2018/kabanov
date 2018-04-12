package com.softwerke.list.comparators;

import com.softwerke.tables.Sale;

public class SaleLastNameComparator extends SortOrderComparator<Sale> {

    public SaleLastNameComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Sale sale1, Sale sale2) {
        int comparisonResult = sale1.getPerson().getLastName().compareToIgnoreCase(sale2.getPerson().getLastName());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}