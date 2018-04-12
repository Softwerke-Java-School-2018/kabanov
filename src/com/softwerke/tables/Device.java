package com.softwerke.tables;

import com.softwerke.list.HasId;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softwerke.Utils.leftPad;

/* Immutable */
public class Device extends HasId {
    public static final Device DELETED_DEVICE =
            new Device("N/A", "N/A", Color.BLACK, LocalDate.now(), DeviceType.PHONE, "0", -1);
    private final String model;
    private final String vendor;
    private final String modelLowerCase;
    private final String vendorLowerCase;
    private final Color color;
    private final LocalDate productionDate;
    private final DeviceType deviceType;
    private final BigDecimal price;

    public Device(String model, String vendor, Color color, LocalDate productionDate, DeviceType deviceType, String price, int id) {
        super(id);
        this.model = model;
        this.vendor = vendor;
        this.modelLowerCase = model.toLowerCase();
        this.vendorLowerCase = vendor.toLowerCase();
        this.color = color;
        this.productionDate = productionDate;
        this.deviceType = deviceType;
        this.price = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public String toFormattedString() {
        return leftPad(String.valueOf(id), 3) + " | " +
                leftPad(vendor + " " + model, 21) + " | " +
                leftPad(color.toString(), 9) + " | " +
                leftPad(deviceType.toString(), 6) + " | " +
                productionDate + " | " +
                leftPad(price.toString(), 9);
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return vendor + " " + model + " @ " + color;
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

    public Device setModelName(String newName) {
        return new Device(newName, vendor, color, productionDate, deviceType, price.toString(), id);
    }

    public Device setVendorName(String newName) {
        return new Device(model, newName, color, productionDate, deviceType, price.toString(), id);
    }

    public Device setColor(Color newColor) {
        return new Device(model, vendor, newColor, productionDate, deviceType, price.toString(), id);
    }

    public Device setType(DeviceType newType) {
        return new Device(model, vendor, color, productionDate, newType, price.toString(), id);
    }

    public Device setProductionDate(LocalDate newProductionDate) {
        return new Device(model, vendor, color, newProductionDate, deviceType, price.toString(), id);
    }

    public Device setPrice(String newPrice) {
        return new Device(model, vendor, color, productionDate, deviceType, newPrice, id);
    }

    public String getModelLowerCase() {
        return modelLowerCase;
    }

    public String getVendorLowerCase() {
        return vendorLowerCase;
    }
}
