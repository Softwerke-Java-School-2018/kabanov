package com.softwerke.salesregister.console;

import com.softwerke.salesregister.exception.BuilderNotInitializedException;
import com.softwerke.salesregister.tables.device.Color;
import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.device.DeviceType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.softwerke.salesregister.console.ConsoleIOStreamTest.STUB;
import static com.softwerke.salesregister.console.Formatter.printFormatDevice;
import static org.junit.Assert.fail;

public class FormatterTest {
    private static ConsoleIOStream console;
    private static ByteArrayOutputStream outputStream;

    private static void initConsoleWithInputText(String input) {
        try {
            outputStream = new ByteArrayOutputStream();
            console = new ConsoleIOStream(IOUtils.toInputStream(input, "UTF-8"), outputStream);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void printFormatDeviceTest() throws BuilderNotInitializedException {
        initConsoleWithInputText(STUB);
        List<Device> deviceList = new ArrayList<>();
        deviceList.add(null);
        Device testDevice = new Device.DeviceBuilder()
                .color(Color.RED)
                .type(DeviceType.PHONE)
                .model(STUB)
                .price(BigDecimal.ZERO)
                .productionDate(LocalDate.MIN)
                .vendor(STUB)
                .id(12345)
                .build();
        deviceList.add(testDevice);
        deviceList.add(null);

        printFormatDevice(deviceList.stream(), console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatDeviceTestNull0() {
        initConsoleWithInputText(STUB);

        printFormatDevice(null, console);
    }

    @Test(expected = NullPointerException.class)
    public void printFormatDeviceTestNull1() {
        printFormatDevice(Stream.empty(), null);
    }

    @Test
    public void printFormatDeviceTestEmpty() {
        initConsoleWithInputText(STUB);

        printFormatDevice(Stream.empty(), console);
    }
}
