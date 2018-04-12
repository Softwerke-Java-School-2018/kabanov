package com.softwerke.list.comparators;

import com.softwerke.tables.Device;

public class DeviceTypeComparator extends SortOrderComparator<Device> {

    public DeviceTypeComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Device device1, Device device2) {
        int comparisonResult = device1.getDeviceType().compareTo(device2.getDeviceType());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}