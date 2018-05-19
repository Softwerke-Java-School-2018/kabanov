package com.softwerke.salesregister.tables.invoice;

import com.softwerke.salesregister.tables.device.Device;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Objects;

public class InvoiceLine {
    private static final Logger logger = LogManager.getLogger(InvoiceLine.class);
    private final Device device;
    private final int amount;
    private final BigDecimal internalSum;

    public InvoiceLine(Device device, int amount) {
        Objects.requireNonNull(device);
        if (amount < 1) {
            logger.fatal("Devices amount should be greater than zero! [InvoiceLine constructor]");
            throw new IllegalArgumentException("Devices amount should be greater than zero!");
        }
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
