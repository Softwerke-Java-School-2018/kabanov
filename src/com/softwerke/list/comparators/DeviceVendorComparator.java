package com.softwerke.list.comparators;

import com.softwerke.tables.Device;

public class DeviceVendorComparator extends SortOrderComparator<Device> {

    public DeviceVendorComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Device device1, Device device2) {
        int comparisonResult = device1.getVendor().compareToIgnoreCase(device2.getVendor());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}