package com.softwerke.tables;

import java.math.BigDecimal;

public class SeveralDevices {
    private final Device device;
    private final int amount;
    private final BigDecimal internalSum;

    public SeveralDevices(Device device, int amount) {
        this.device = device;
        this.amount = amount;
        this.internalSum = device.getPrice().multiply(BigDecimal.valueOf(amount));
    }

    public int getAmount() {
        return amount;
    }

    public Device getDevice() {
        return device;
    }

    public BigDecimal getInternalSum() {
        return internalSum;
    }
}
