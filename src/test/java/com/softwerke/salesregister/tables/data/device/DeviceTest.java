package com.softwerke.salesregister.tables.data.device;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.io.ConsoleIOStreamTest;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DeviceTest {
    @Test
    public void deviceBuilderTest() throws BuilderNotInitializedException {
        new Device.DeviceBuilder()
                .color(Color.RED)
                .type(DeviceType.PHONE)
                .model(ConsoleIOStreamTest.STUB)
                .price(BigDecimal.ZERO)
                .productionDate(LocalDate.MIN)
                .vendor(ConsoleIOStreamTest.STUB)
                .id(12345)
                .build();
    }

    @Test(expected = BuilderNotInitializedException.class)
    public void deviceBuilderTestNull() throws BuilderNotInitializedException {
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
    public void deviceBuilderTestNotFull() throws BuilderNotInitializedException {
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
