package com.softwerke.tables;

import java.math.BigDecimal;

class SeveralDevices {
    private final int deviceID;
    private final int amount;
    private final BigDecimal internalSum;

    public SeveralDevices(int deviceID, int amount, BigDecimal internalSum) {
        this.deviceID = deviceID;
        this.amount = amount;
        this.internalSum = internalSum;
    }

    public int getAmount() {
        return amount;
    }

    public int getDeviceID() {
        return deviceID;
    }
}
