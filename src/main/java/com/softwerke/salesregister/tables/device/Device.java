package com.softwerke.salesregister.tables.device;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Device {
    private final int id;
    private final String model;
    private final String vendor;
    private final String modelLowerCase;
    private final String vendorLowerCase;
    private final Color color;
    private final LocalDate productionDate;
    private final DeviceType deviceType;
    private final BigDecimal price;
    private final boolean isDeleted;

    private Device(String model, String vendor, Color color, LocalDate productionDate,
                   DeviceType deviceType, String price, int id, boolean isDeleted) {
        this.id = id;
        this.model = model;
        this.vendor = vendor;
        this.modelLowerCase = model.toLowerCase();
        this.vendorLowerCase = vendor.toLowerCase();
        this.color = color;
        this.productionDate = productionDate;
        this.deviceType = deviceType;
        this.price = new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.isDeleted = isDeleted;
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

    public String getLabelText() {
        return vendor + " " + model;
    }

    public Color getColor() {
        return color;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public String getModelLowerCase() {
        return modelLowerCase;
    }

    public String getVendorLowerCase() {
        return vendorLowerCase;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return vendor + " " + model + " @ " + color;
    }

    public static class DeviceBuilder {
        static int id;
        static String model;
        static String vendor;
        static Color color;
        static LocalDate productionDate;
        static DeviceType deviceType;
        static BigDecimal price;
        static boolean isDeleted;

        public DeviceBuilder() {
            id = -1;
            model = null;
            vendor = null;
            color = null;
            deviceType = null;
            productionDate = null;
            price = null;
            isDeleted = true;
        }

        public static DeviceBuilder setupFromDevice(Device device) {
            DeviceBuilder deviceBuilder = new DeviceBuilder();
            return deviceBuilder
                    .id(device.id)
                    .model(device.model)
                    .vendor(device.vendor)
                    .color(device.color)
                    .productionDate(device.productionDate)
                    .type(device.deviceType)
                    .isDeleted(device.isDeleted)
                    .price(device.price);
        }

        public DeviceBuilder id(int id) {
            DeviceBuilder.id = id;
            return this;
        }

        public DeviceBuilder model(String model) {
            DeviceBuilder.model = model;
            return this;
        }

        public DeviceBuilder vendor(String vendor) {
            DeviceBuilder.vendor = vendor;
            return this;
        }

        public DeviceBuilder color(Color color) {
            DeviceBuilder.color = color;
            return this;
        }

        public DeviceBuilder productionDate(LocalDate productionDate) {
            DeviceBuilder.productionDate = productionDate;
            return this;
        }

        public DeviceBuilder type(DeviceType deviceType) {
            DeviceBuilder.deviceType = deviceType;
            return this;
        }

        public DeviceBuilder isDeleted(boolean isDeleted) {
            DeviceBuilder.isDeleted = isDeleted;
            return this;
        }

        public DeviceBuilder price(BigDecimal price) {
            DeviceBuilder.price = price;
            return this;
        }

        public Device build() throws BuilderNotInitializedException {
            if (id == -1 || !ObjectUtils.allNotNull(model, vendor, color, deviceType, productionDate, price)) {
                throw new BuilderNotInitializedException("Builder isn't filled!");
            }
            return new Device(model, vendor, color, productionDate, deviceType, price.toString(), id, isDeleted);
        }
    }
}