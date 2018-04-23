package com.softwerke.salesregister.console;

import com.softwerke.salesregister.tables.device.Device;
import com.softwerke.salesregister.tables.invoice.Invoice;
import com.softwerke.salesregister.tables.invoice.InvoiceLine;
import com.softwerke.salesregister.tables.person.Person;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Formatter {
    public static void printFormatDevice(Stream<Device> stream, IOStream sink) {
        List<Device> deviceList = stream.collect(Collectors.toList());
        if (deviceList.isEmpty()) {
            sink.printLine();                         /* Separating blank line */
            sink.printLine(ConsoleIOStream.LIST_IS_EMPTY_TEXT);
            return;
        }
        sink.printLine();                             /* Separating blank line */
        sink.printLine(" ID | Device vendor / model |   Color   |  Type  | Prod. date |   Price");
        sink.printLine("-------------------------------------------------------------------------");
        deviceList.forEach(device -> sink.printLine(
                leftPad(String.valueOf(device.getId()), 3) + " | " +
                        leftPad(device.getVendor() + " " + device.getModel(), 21) + " | " +
                        leftPad(device.getColor().toString(), 9) + " | " +
                        leftPad(device.getDeviceType().toString(), 6) + " | " +
                        device.getProductionDate() + " | " +
                        leftPad(device.getPrice().toString(), 9)));
        sink.ask(ConsoleIOStream.PRESS_ANYKEY_TEXT);
    }

    public static void printFormatInvoice(Stream<Invoice> stream, IOStream sink) {
        List<Invoice> invoiceList = stream.collect(Collectors.toList());
        if (invoiceList.isEmpty()) {
            sink.printLine();                         /* Separating blank line */
            sink.printLine(ConsoleIOStream.LIST_IS_EMPTY_TEXT);
            return;
        }
        sink.printLine();                             /* Separating blank line */
        sink.printLine(" ID |   Customer name   |    Total    | Invoice date");
        sink.printLine("-----------------------------------------------------");
        invoiceList.forEach(invoice -> sink.printLine(
                leftPad(String.valueOf(invoice.getId()), 3) + " | " +
                        leftPad(invoice.getPerson().toString(), 17) + " | " +
                        leftPad(invoice.getTotalSum().toString(), 11) + " |  " +
                        invoice.getDate()));
        sink.ask(ConsoleIOStream.PRESS_ANYKEY_TEXT);
    }

    public static void printFormatPerson(Stream<Person> stream, IOStream sink) {
        List<Person> personList = stream.collect(Collectors.toList());
        if (personList.isEmpty()) {
            sink.printLine();                         /* Separating blank line */
            sink.printLine(ConsoleIOStream.LIST_IS_EMPTY_TEXT);
            return;
        }
        sink.printLine();                             /* Separating blank line */
        sink.printLine(" ID |            Name           | Birth date");
        sink.printLine("--------------------------------------------");
        personList.forEach(person -> sink.printLine(
                leftPad(String.valueOf(person.getId()), 3) + " | " +
                        leftPad(person.getFirstName() + " " + person.getLastName(), 25) + " | " +
                        person.getBirthDate()));
        sink.ask(ConsoleIOStream.PRESS_ANYKEY_TEXT);
    }

    public static void printShopList(Collection<InvoiceLine> orderItems, IOStream sink) {
        if (orderItems.isEmpty()) {
            sink.printLine("Shop list is empty.");
            return;
        }
        sink.printLine("            Items            | Amount |   Total");
        sink.printLine("--------------------------------------------------");
        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceLine invoiceLine : orderItems) {
            total = total.add(invoiceLine.getInternalSum());
            String formattedName = rightPad(invoiceLine.getDevice().toString(), 29);
            String formattedAmount = leftPad(String.valueOf(invoiceLine.getAmount()), 7);
            String formattedInternalSum = leftPad(invoiceLine.getInternalSum().toString(), 11);
            sink.printLine(formattedName + "|" + formattedAmount + " |" + formattedInternalSum);
        }
        String formattedTotal = leftPad(total.toString(), 43);
        sink.printLine(" Total:" + formattedTotal);
        sink.printLine();
    }

    public static void printReceipt(Invoice invoice, IOStream sink) {
        sink.printLine(" Shopping date: " + invoice.getDate());
        sink.printLine(" Customer name: " + invoice.getPerson());
        printShopList(invoice.getInvoiceItems(), sink);
    }

    private static String leftPad(String text, int length) {
        return String.format("%1$" + length + "s", text);
    }

    private static String rightPad(String text, int length) {
        return String.format("%1$-" + length + "s", text);
    }
}
