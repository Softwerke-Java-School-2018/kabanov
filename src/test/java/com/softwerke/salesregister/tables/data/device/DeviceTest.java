package com.softwerke.salesregister.tables.data.device;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.io.ConsoleIOStreamTest;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class DeviceTest {
    @Test
    public void DeviceBuilder_FilledBuilder_Success() throws BuilderNotInitializedException {
        Device device = new Device.DeviceBuilder()
                .color(Color.RED)
                .type(DeviceType.PHONE)
                .model(ConsoleIOStreamTest.STUB + ConsoleIOStreamTest.STUB)
                .price(BigDecimal.ZERO)
                .productionDate(LocalDate.MIN)
                .vendor(ConsoleIOStreamTest.STUB)
                .id(12345)
                .isDeleted(false)
                .build();

        assertEquals(device.getId(), 12345);
        assertEquals(device.getColor(), Color.RED);
        assertEquals(device.getDeviceType(), DeviceType.PHONE);
        assertEquals(device.getModel(), ConsoleIOStreamTest.STUB + ConsoleIOStreamTest.STUB);
        assertEquals(device.getPrice().toString(), "0.00");
        assertEquals(device.getProductionDate(), LocalDate.MIN);
        assertEquals(device.getVendor(), ConsoleIOStreamTest.STUB);
        assertFalse(device.isDeleted());
    }

    @Test
    public void DeviceBuilder_FilledBuilderDeletedDevice_Success() throws BuilderNotInitializedException {
        Device device = new Device.DeviceBuilder()
                .color(Color.RED)
                .type(DeviceType.PHONE)
                .model(ConsoleIOStreamTest.STUB + ConsoleIOStreamTest.STUB)
                .price(BigDecimal.ZERO)
                .productionDate(LocalDate.MIN)
                .vendor(ConsoleIOStreamTest.STUB)
                .id(12345)
                .build();

        assertEquals(device.getId(), 12345);
        assertEquals(device.getColor(), Color.RED);
        assertEquals(device.getDeviceType(), DeviceType.PHONE);
        assertEquals(device.getModel(), ConsoleIOStreamTest.STUB + ConsoleIOStreamTest.STUB);
        assertEquals(device.getPrice().toString(), "0.00");
        assertEquals(device.getProductionDate(), LocalDate.MIN);
        assertEquals(device.getVendor(), ConsoleIOStreamTest.STUB);
        assertTrue(device.isDeleted());
    }

    @Test(expected = BuilderNotInitializedException.class)
    public void DeviceBuilder_FilledWithNull_BuilderNotInitializedException() throws BuilderNotInitializedException {
        new Device.DeviceBuilder()
                .color(null)
                .type(DeviceType.PHONE)
                .model(ConsoleIOStreamTest.STUB)
                .price(BigDecimal.ZERO)
                .productionDate(LocalDate.MIN)
                .vendor(ConsoleIOStreamTest.STUB)
                .id(12345)
                .build();
    }

    @Test(expected = BuilderNotInitializedException.class)
    public void DeviceBuilder_NotFilled_BuilderNotInitializedException() throws BuilderNotInitializedException {
        new Device.DeviceBuilder()
                .color(Color.RED)
                .type(DeviceType.PHONE)
                .price(BigDecimal.ZERO)
                .productionDate(LocalDate.MIN)
                .vendor(ConsoleIOStreamTest.STUB)
                .id(12345)
                .build();
    }
}
