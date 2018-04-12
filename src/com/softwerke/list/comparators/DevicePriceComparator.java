package com.softwerke.list.comparators;

import com.softwerke.tables.Device;

public class DevicePriceComparator extends SortOrderComparator<Device> {

    public DevicePriceComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Device device1, Device device2) {
        int comparisonResult = device1.getPrice().compareTo(device2.getPrice());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}