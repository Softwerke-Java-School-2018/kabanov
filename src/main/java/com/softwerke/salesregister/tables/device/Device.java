package com.softwerke.salesregister.tables.device;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Device {
    public static final Device DELETED_DEVICE =
            new Device("N/A", "N/A", Color.BLACK, LocalDate.now(), DeviceType.PHONE, "0", -1);
    private final int id;
    private final String model;
    private final String vendor;
    private final String modelLowerCase;
    private final String vendorLowerCase;
    private final Color color;
    private final LocalDate productionDate;
    private final DeviceType deviceType;
    private final BigDecimal price;

    public Device(String model, String vendor, Color color, LocalDate productionDate, DeviceType deviceType, String price, int id) {
        this.id = id;
        this.model = model;
        this.vendor = vendor;
        this.modelLowerCase = model.toLowerCase();
        this.vendorLowerCase = vendor.toLowerCase();
        this.color = color;
        this.productionDate = productionDate;
        this.deviceType = deviceType;
        this.price = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
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

    public Device cloneWithNewModelName(String newName) {
        return new Device(newName, vendor, color, productionDate, deviceType, price.toString(), id);
    }

    public Device cloneWithNewVendorName(String newName) {
        return new Device(model, newName, color, productionDate, deviceType, price.toString(), id);
    }

    public Device cloneWithNewColor(Color newColor) {
        return new Device(model, vendor, newColor, productionDate, deviceType, price.toString(), id);
    }

    public Device cloneWithNewType(DeviceType newType) {
        return new Device(model, vendor, color, productionDate, newType, price.toString(), id);
    }

    public Device cloneWithNewProductionDate(LocalDate newProductionDate) {
        return new Device(model, vendor, color, newProductionDate, deviceType, price.toString(), id);
    }

    public Device cloneWithNewPrice(String newPrice) {
        return new Device(model, vendor, color, productionDate, deviceType, newPrice, id);
    }

    public String getModelLowerCase() {
        return modelLowerCase;
    }

    public String getVendorLowerCase() {
        return vendorLowerCase;
    }

    @Override
    public String toString() {
        return vendor + " " + model + " @ " + color;
    }
}