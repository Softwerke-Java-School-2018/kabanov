package com.softwerke.list;

import com.softwerke.Utils;
import com.softwerke.console.IOPipe;
import com.softwerke.tables.Color;
import com.softwerke.tables.Device;
import com.softwerke.tables.DeviceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.softwerke.StringPool.LIST_IS_EMPTY_TEXT;
import static com.softwerke.StringPool.PRESS_ANYKEY_TEXT;

public class DeviceList extends ListWithGaps<Device> {
    public DeviceList maskByDeviceId(String from, String to) {
        int fromId = Integer.parseInt(from);
        int toId = Integer.parseInt(to);
        DeviceList returning = this.clone();
        returning.removeIf(x -> !Utils.inBetween(fromId, x.getId(), toId));
        return returning;
    }

    public DeviceList maskByDeviceVendor(String nameToMask) {
        if (nameToMask.trim().equals("*")) return this;
        String nameLowerCase = nameToMask.toLowerCase();
        DeviceList returning = this.clone();
        returning.removeIf(x -> !x.getVendorLowerCase().contains(nameLowerCase));
        return returning;
    }

    public DeviceList maskByDeviceModel(String nameToMask) {
        if (nameToMask.trim().equals("*")) return this;
        String nameLowerCase = nameToMask.toLowerCase();
        DeviceList returning = this.clone();
        returning.removeIf(x -> !x.getModelLowerCase().contains(nameLowerCase));
        return returning;
    }

    public DeviceList maskByDeviceProductionDate(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from.replaceAll("\\D+", "-"),
                DateTimeFormatter.ofPattern("d-MM-yyyy"));
        LocalDate toDate = LocalDate.parse(to.replaceAll("\\D+", "-"),
                DateTimeFormatter.ofPattern("d-MM-yyyy"));
        DeviceList returning = this.clone();
        returning.removeIf(x -> !Utils.inBetween(fromDate, x.getProductionDate(), toDate));
        return returning;
    }

    public DeviceList maskByDeviceColor(Color[] colors) {
        DeviceList returning = this.clone();
        returning.removeIf(x -> Arrays.stream(colors).noneMatch(c -> c.equals(x.getColor())));
        return returning;
    }

    public DeviceList maskByDeviceType(DeviceType[] deviceTypes) {
        DeviceList returning = this.clone();
        returning.removeIf(x -> Arrays.stream(deviceTypes).noneMatch(c -> c.equals(x.getDeviceType())));
        return returning;
    }

    public DeviceList maskByDevicePrice(String from, String to) {
        BigDecimal fromValue = new BigDecimal(from);
        BigDecimal toValue = new BigDecimal(to);
        DeviceList returning = this.clone();
        returning.removeIf(x -> !Utils.inBetween(fromValue, x.getPrice(), toValue));
        return returning;
    }

    public void print() {
        removeIf(x -> x.getId() == -1);
        if (isEmpty()) {
            IOPipe.printLine();                         /* Separating blank line */
            IOPipe.printLine(LIST_IS_EMPTY_TEXT);
            return;
        }
        IOPipe.printLine();                             /* Separating blank line */
        IOPipe.printLine(" ID | Device vendor / model |   Color   |  Type  | Prod. date |   Price");
        IOPipe.printLine("-------------------------------------------------------------------------");
        for (Device device : this)
                IOPipe.printLine(device.toFormattedString());
        IOPipe.getLineByDialog(PRESS_ANYKEY_TEXT);
    }

    @Override
    public DeviceList clone() {
        DeviceList returning = new DeviceList();
        returning.addAll(this);
        return returning;
    }
}
