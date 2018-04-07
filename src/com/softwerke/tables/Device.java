package com.softwerke.tables;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softwerke.masklist.Utils.leftPad;

/* Immutable */
public class Device {
    private final int id;
    private final String model;
    private final String vendor;
    private final Color color;
    private final LocalDate productionDate;
    private final DeviceType deviceType;
    private final BigDecimal price;

    public Device(String model, String vendor, Color color, LocalDate productionDate, DeviceType deviceType, String price, int id) {
        this.id = id;
        this.model = model;
        this.vendor = vendor;
        this.color = color;
        this.productionDate = productionDate;
        this.deviceType = deviceType;
        this.price = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return vendor + " " + model + " @ " + color;
    }

    public int getId() {
        return id;
    }

    public String toFormattedString() {
        return leftPad(String.valueOf(id), 3) + " | " +
                leftPad(vendor + " " + model, 21) + " | " +
                leftPad(color.toString(), 9) + " | " +
                leftPad(deviceType.toString(), 6) + " | " +
                productionDate + " | " +
                leftPad(price.toString(), 9);
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public String getVendor() {
        return vendor;
    }

    public String getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }
}
