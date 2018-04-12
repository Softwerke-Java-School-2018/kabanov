package com.softwerke.list.comparators;

import com.softwerke.tables.Device;

public class DeviceProductionDateComparator extends SortOrderComparator<Device> {

    public DeviceProductionDateComparator(boolean isOrderAscending) {
        super(isOrderAscending);
    }

    @Override
    public int compare(Device device1, Device device2) {
        int comparisonResult = device1.getProductionDate().compareTo(device2.getProductionDate());
        return isOrderAscending ? comparisonResult : -comparisonResult;
    }
}