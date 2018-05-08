package com.softwerke.salesregister.console;

import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Formatter {
    public static void printFormatDevice(Stream<Device> stream, IOStream sink) {
        Objects.requireNonNull(sink);
        List<Device> deviceList = Objects.requireNonNull(stream).filter(Objects::nonNull).collect(Collectors.toList());
        if (deviceList.isEmpty()) {
            sink.printLine();                         /* Separating blank line */
            sink.printLine(ConsoleIOStream.LIST_IS_EMPTY_TEXT);
            return;
        }
        sink.printLine();                             /* Separating blank line */
        sink.printLine(" ID | Device vendor / model |   Color   |  Type  | Prod. date |   Price");
        sink.printLine("-------------------------------------------------------------------------");
        deviceList.forEach(device -> sink.printLine(
                leftPad(device.id, 3) + " | " +
                        leftPad(device.getLabel(), 21) + " | " +
                        leftPad(device.color, 9) + " | " +
                        leftPad(device.deviceType, 6) + " | " +
                        device.productionDate + " | " +
                        leftPad(device.price, 9)));
        sink.ask(ConsoleIOStream.PRESS_ANYKEY_TEXT);
    }

    public static void printFormatInvoice(Stream<Invoice> stream, IOStream sink) {
        Objects.requireNonNull(sink);
        List<Invoice> invoiceList = Objects.requireNonNull(stream).filter(Objects::nonNull).collect(Collectors.toList());
        if (invoiceList.isEmpty()) {
            sink.printLine();                         /* Separating blank line */
            sink.printLine(ConsoleIOStream.LIST_IS_EMPTY_TEXT);
            return;
        }
        sink.printLine();                             /* Separating blank line */
        sink.printLine(" ID |   Customer name   |    Total    | Invoice date");
        sink.printLine("-----------------------------------------------------");
        invoiceList.forEach(invoice -> sink.printLine(
                leftPad(invoice.id, 3) + " | " +
                        leftPad(invoice.person, 17) + " | " +
                        leftPad(invoice.totalSum, 11) + " |  " +
                        invoice.date));
        sink.ask(ConsoleIOStream.PRESS_ANYKEY_TEXT);
    }

    public static void printFormatPerson(Stream<Person> stream, IOStream sink) {
        Objects.requireNonNull(sink);
        List<Person> personList = Objects.requireNonNull(stream).filter(Objects::nonNull).collect(Collectors.toList());
        if (personList.isEmpty()) {
            sink.printLine();                         /* Separating blank line */
            sink.printLine(ConsoleIOStream.LIST_IS_EMPTY_TEXT);
            return;
        }
        sink.printLine();                             /* Separating blank line */
        sink.printLine(" ID |            Name           | Birth date");
        sink.printLine("--------------------------------------------");
        personList.forEach(person -> sink.printLine(
                leftPad(person.id, 3) + " | " +
                        leftPad(person.getFullName(), 25) + " | " + person.birthDate));
        sink.ask(ConsoleIOStream.PRESS_ANYKEY_TEXT);
    }

    public static void printShopList(Stream<InvoiceLine> items, IOStream sink) {
        List<InvoiceLine> orderItems = items.collect(Collectors.toList());
        if (!ObjectUtils.allNotNull(orderItems, sink)) {
            throw new IllegalArgumentException("One or more arguments is null!");
        }
        if (orderItems.isEmpty()) {
            sink.printLine("Shop list is empty.");
            return;
        }
        sink.printLine("            Items            | Amount |   Total");
        sink.printLine("--------------------------------------------------");
        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceLine invoiceLine : orderItems) {
            if (Objects.isNull(invoiceLine)) {
                continue;
            }
            total = total.add(invoiceLine.internalSum);
            String formattedName = rightPad(invoiceLine.device, 29);
            String formattedAmount = leftPad(invoiceLine.amount, 7);
            String formattedInternalSum = leftPad(invoiceLine.internalSum, 11);
            sink.printLine(formattedName + "|" + formattedAmount + " |" + formattedInternalSum);
        }
        String formattedTotal = leftPad(total, 43);
        sink.printLine(" Total:" + formattedTotal);
        sink.printLine();
    }

    public static void printReceipt(Invoice invoice, IOStream sink) {
        if (!ObjectUtils.allNotNull(invoice, sink)) {
            throw new IllegalArgumentException("One or more arguments is null!");
        }
        sink.printLine(" Shopping date: " + invoice.date);
        sink.printLine(" Customer name: " + invoice.person.toString());
        printShopList(invoice.getInvoices(), sink);
    }

    private static String leftPad(Object text, int length) {
        if (Objects.isNull(text) || length < 1) {
            throw new IllegalArgumentException("Arguments is null or length is less or equals zero!");
        }
        return String.format("%1$" + length + "s", text.toString());
    }

    private static String rightPad(Object text, int length) {
        if (Objects.isNull(text) || length < 1) {
            throw new IllegalArgumentException("Arguments is null or length is less or equals zero!");
        }
        return String.format("%1$-" + length + "s", text.toString());
    }
}
