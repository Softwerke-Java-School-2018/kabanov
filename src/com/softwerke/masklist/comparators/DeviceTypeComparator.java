package com.softwerke.masklist.comparators;

import com.softwerke.masklist.SortingOrder;
import com.softwerke.tables.Device;

import java.util.Comparator;

import static com.softwerke.masklist.SortingOrder.ASCENDING;

public class DeviceTypeComparator implements Comparator<Device> {
    private final SortingOrder order;

    public DeviceTypeComparator(SortingOrder order) {
        this.order = order;
    }

    @Override
    public int compare(Device device1, Device device2) {
        int comparisonResult = device1.getDeviceType().compareTo(device2.getDeviceType());
        return (order == ASCENDING) ? comparisonResult : -comparisonResult;
    }
}