package com.softwerke.masklist.comparators;

import com.softwerke.masklist.SortingOrder;
import com.softwerke.tables.Device;

import java.util.Comparator;

import static com.softwerke.masklist.SortingOrder.ASCENDING;

public class DeviceIdComparator implements Comparator<Device> {
    private final SortingOrder order;

    public DeviceIdComparator(SortingOrder order) {
        this.order = order;
    }

    @Override
    public int compare(Device device1, Device device2) {
        int comparisonResult = Integer.compare(device1.getId(), device2.getId());
        return (order == ASCENDING) ? comparisonResult : -comparisonResult;
    }
}