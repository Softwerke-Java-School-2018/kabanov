package com.softwerke.salesregister.tables.invoice;

import com.softwerke.salesregister.tables.device.Device;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceLine {
    public final Device device;
    public final int amount;
    public final BigDecimal internalSum;

    public InvoiceLine(Device device, int amount) {
        Objects.requireNonNull(device);
        if (amount < 1) {
            throw new IllegalArgumentException("Devices amount should be greater than zero!");
        }
        this.device = device;
        this.amount = amount;
        this.internalSum = device.price.multiply(BigDecimal.valueOf(amount));
    }
}
