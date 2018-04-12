package com.softwerke.list.comparators;

import com.softwerke.tables.Device;

public class DeviceColorComparator extends SortOrderComparator<Device> {

    public DeviceColorComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Device device1, Device device2) {
        int comparisonResult = device1.getColor().compareTo(device2.getColor());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}