package com.softwerke.salesregister.io;

import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Formatter {
    private Formatter() {
    }

    public static void printFormatDevice(Stream<Device> stream, IOStream sink) {
        Objects.requireNonNull(sink);
        List<Device> deviceList = Objects.requireNonNull(stream).filter(Objects::nonNull).collect(Collectors.toList());
        if (deviceList.isEmpty()) {
            sink.printLine();                         /* Separating blank line */
            sink.printLine(StringLiterals.LIST_IS_EMPTY_TEXT);
            return;
        }
        sink.printLine();                             /* Separating blank line */
        sink.printLine(" ID | Device vendor / model |   Color   |  Type  | Prod. date |   Price");
        sink.printLine("-------------------------------------------------------------------------");
        deviceList.forEach(device -> sink.printLine(
                StringUtils.leftPad(String.valueOf(device.getId()), 3) + " | " +
                        StringUtils.leftPad(device.getLabelText(), 21) + " | " +
                        StringUtils.leftPad(String.valueOf(device.getColor()), 9) + " | " +
                        StringUtils.leftPad(String.valueOf(device.getDeviceType()), 6) + " | " +
                        device.getProductionDate() + " | " +
                        StringUtils.leftPad(String.valueOf(device.getPrice()), 9)));
        sink.ask(StringLiterals.PRESS_ANYKEY_TEXT);
    }

    public static void printFormatInvoice(Stream<Invoice> stream, IOStream sink) {
        Objects.requireNonNull(sink);
        List<Invoice> invoiceList = Objects.requireNonNull(stream).filter(Objects::nonNull).collect(Collectors.toList());
        if (invoiceList.isEmpty()) {
            sink.printLine();                         /* Separating blank line */
            sink.printLine(StringLiterals.LIST_IS_EMPTY_TEXT);
            return;
        }
        sink.printLine();                             /* Separating blank line */
        sink.printLine(" ID |   Customer name   |    Total    | Invoice date");
        sink.printLine("-----------------------------------------------------");
        invoiceList.forEach(invoice -> sink.printLine(
                StringUtils.leftPad(String.valueOf(invoice.getId()), 3) + " | " +
                        StringUtils.leftPad(String.valueOf(invoice.getPerson()), 17) + " | " +
                        StringUtils.leftPad(String.valueOf(invoice.getTotalSum()), 11) + " |  " +
                        invoice.getDate()));
        sink.ask(StringLiterals.PRESS_ANYKEY_TEXT);
    }

    public static void printFormatPerson(Stream<Person> stream, IOStream sink) {
        Objects.requireNonNull(sink);
        List<Person> personList = Objects.requireNonNull(stream).filter(Objects::nonNull).collect(Collectors.toList());
        if (personList.isEmpty()) {
            sink.printLine();                         /* Separating blank line */
            sink.printLine(StringLiterals.LIST_IS_EMPTY_TEXT);
            return;
        }
        sink.printLine();                             /* Separating blank line */
        sink.printLine(" ID |            Name           | Birth date");
        sink.printLine("--------------------------------------------");
        personList.forEach(person -> sink.printLine(
                StringUtils.leftPad(String.valueOf(person.getId()), 3) + " | " +
                        StringUtils.leftPad(person.getFullName(), 25) + " | " + person.getBirthDate()));
        sink.ask(StringLiterals.PRESS_ANYKEY_TEXT);
    }

    public static void printShopList(Stream<InvoiceLine> invoiceLines, IOStream sink) {
        if (!ObjectUtils.allNotNull(invoiceLines, sink)) {
            Logger.fatal("One or more arguments is null! [printShopList method]");
            throw new IllegalArgumentException(StringLiterals.NULL_ARG_EXC);
        }
        List<InvoiceLine> invoiceLineList = invoiceLines.collect(Collectors.toList());
        if (invoiceLineList.isEmpty()) {
            sink.printLine("Shop list is empty.");
            return;
        }
        sink.printLine("            Items            | Amount |   Total");
        sink.printLine("--------------------------------------------------");
        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceLine invoiceLine : invoiceLineList) {
            if (Objects.isNull(invoiceLine)) {
                continue;
            }
            total = total.add(invoiceLine.getInternalSum());
            String formattedName = StringUtils.rightPad(String.valueOf(invoiceLine.getDevice()), 29);
            String formattedAmount = StringUtils.leftPad(String.valueOf(invoiceLine.getAmount()), 7);
            String formattedInternalSum = StringUtils.leftPad(String.valueOf(invoiceLine.getInternalSum()), 11);
            sink.printLine(formattedName + "|" + formattedAmount + " |" + formattedInternalSum);
        }
        String formattedTotal = StringUtils.leftPad(String.valueOf(total), 43);
        sink.printLine(" Total:" + formattedTotal);
        sink.printLine();
    }

    public static void printReceipt(Invoice invoice, IOStream sink) {
        if (!ObjectUtils.allNotNull(invoice, sink)) {
            Logger.fatal("One or more arguments is null! [printReceipt method]");
            throw new IllegalArgumentException(StringLiterals.NULL_ARG_EXC);
        }
        sink.printLine(" Shopping date: " + invoice.getDate());
        sink.printLine(" Customer name: " + invoice.getPerson().toString());
        printShopList(invoice.getInvoices(), sink);
    }
}
