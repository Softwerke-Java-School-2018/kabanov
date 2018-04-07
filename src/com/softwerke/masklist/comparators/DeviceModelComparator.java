package com.softwerke.masklist.comparators;

import com.softwerke.masklist.SortingOrder;
import com.softwerke.tables.Device;

import java.util.Comparator;

import static com.softwerke.masklist.SortingOrder.ASCENDING;

public class DeviceModelComparator implements Comparator<Device> {
    private final SortingOrder order;

    public DeviceModelComparator(SortingOrder order) {
        this.order = order;
    }

    @Override
    public int compare(Device device1, Device device2) {
        int comparisonResult = device1.getModel().compareToIgnoreCase(device2.getModel());
        return (order == ASCENDING) ? comparisonResult : -comparisonResult;
    }
}