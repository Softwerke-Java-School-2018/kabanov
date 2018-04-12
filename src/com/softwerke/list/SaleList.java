package com.softwerke.list;

import com.softwerke.Utils;
import com.softwerke.console.IOPipe;
import com.softwerke.tables.DeviceType;
import com.softwerke.tables.Sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.softwerke.StringPool.LIST_IS_EMPTY_TEXT;
import static com.softwerke.StringPool.PRESS_ANYKEY_TEXT;

public class SaleList extends ListWithGaps<Sale> {
    public SaleList maskBySaleId(String from, String to) {
        int fromId = Integer.parseInt(from);
        int toId = Integer.parseInt(to);
        SaleList returning = this.clone();
        returning.removeIf(x -> !Utils.inBetween(fromId, x.getId(), toId));
        return returning;
    }

    public SaleList maskBySaleDate(String from, String to) {
        LocalDate fromDate = LocalDate.parse(from.replaceAll("\\D+", "-"),
                DateTimeFormatter.ofPattern("d-MM-yyyy"));
        LocalDate toDate = LocalDate.parse(to.replaceAll("\\D+", "-"),
                DateTimeFormatter.ofPattern("d-MM-yyyy"));
        SaleList returning = this.clone();
        returning.removeIf(x -> !Utils.inBetween(fromDate, x.getSaleDate(), toDate));
        return returning;
    }

    public SaleList maskByTotalSum(String from, String to) {
        BigDecimal fromValue = new BigDecimal(from);
        BigDecimal toValue = new BigDecimal(to);
        SaleList returning = this.clone();
        returning.removeIf(x -> !Utils.inBetween(fromValue, x.getTotalSum(), toValue));
        return returning;
    }

    public SaleList maskByPersonId(String from, String to) {
        int fromId = Integer.parseInt(from);
        int toId = Integer.parseInt(to);
        SaleList returning = this.clone();
        returning.removeIf(x -> !Utils.inBetween(fromId, x.getPerson().getId(), toId));
        return returning;
    }

    public SaleList maskByDeviceId(String from, String to) {
        int fromId = Integer.parseInt(from);
        int toId = Integer.parseInt(to);
        SaleList returning = this.clone();
        returning.removeIf(x -> x.getSeveralDevices().stream().noneMatch(
                y -> Utils.inBetween(fromId, y.getDevice().getId(), toId)));
        return returning;
    }

    public SaleList maskByDeviceType(DeviceType[] deviceTypes) {
        SaleList returning = this.clone();
        returning.removeIf(x -> x.getSeveralDevices().stream().noneMatch(y -> Arrays.stream(deviceTypes).anyMatch(z -> z.equals(y.getDevice().getDeviceType()))));
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
        IOPipe.printLine(" ID |   Customer name   |    Total    |  Sale date");
        IOPipe.printLine("----------------------------------------------------");
        for (Sale sale : this)
                IOPipe.printLine(sale.toFormattedString());
        IOPipe.getLineByDialog(PRESS_ANYKEY_TEXT);
    }

    @Override
    public SaleList clone() {
        SaleList returning = new SaleList();
        returning.addAll(this);
        return returning;
    }
}
