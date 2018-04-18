package com.softwerke.salesregister.tables.invoice;

import com.softwerke.salesregister.tables.device.Device;

import java.math.BigDecimal;

public class InvoiceLine {
    private final Device device;
    private final int amount;
    private final BigDecimal internalSum;

    public InvoiceLine(Device device, int amount) {
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
