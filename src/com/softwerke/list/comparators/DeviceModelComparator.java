package com.softwerke.list.comparators;

import com.softwerke.tables.Device;

public class DeviceModelComparator extends SortOrderComparator<Device> {

    public DeviceModelComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Device device1, Device device2) {
        int comparisonResult = device1.getModel().compareToIgnoreCase(device2.getModel());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}