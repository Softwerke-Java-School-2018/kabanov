package com.softwerke.salesregister.console;

import com.softwerke.salesregister.Utils;
import com.softwerke.salesregister.tables.Device;
import com.softwerke.salesregister.tables.Invoice;
import com.softwerke.salesregister.tables.Person;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Formatter {
    public static void printFormatDevice(Stream<Device> stream) {
        List<Device> deviceList = stream.collect(Collectors.toList());
        if (deviceList.isEmpty()) {
            IOPipe.printLine();                         /* Separating blank line */
            IOPipe.printLine(IOPipe.LIST_IS_EMPTY_TEXT);
            return;
        }
        IOPipe.printLine();                             /* Separating blank line */
        IOPipe.printLine(" ID | Device vendor / model |   Color   |  Type  | Prod. date |   Price");
        IOPipe.printLine("-------------------------------------------------------------------------");
        deviceList.forEach(device -> IOPipe.printLine(
                Utils.leftPad(String.valueOf(device.getId()), 3) + " | " +
                        Utils.leftPad(device.getVendor() + " " + device.getModel(), 21) + " | " +
                        Utils.leftPad(device.getColor().toString(), 9) + " | " +
                        Utils.leftPad(device.getDeviceType().toString(), 6) + " | " +
                        device.getProductionDate() + " | " +
                        Utils.leftPad(device.getPrice().toString(), 9)));
        IOPipe.getLineByDialog(IOPipe.PRESS_ANYKEY_TEXT);
    }

    public static void printFormatInvoice(Stream<com.softwerke.salesregister.tables.Invoice> stream) {
        List<Invoice> invoiceList = stream.collect(Collectors.toList());
        if (invoiceList.isEmpty()) {
            IOPipe.printLine();                         /* Separating blank line */
            IOPipe.printLine(IOPipe.LIST_IS_EMPTY_TEXT);
            return;
        }
        IOPipe.printLine();                             /* Separating blank line */
        IOPipe.printLine(" ID |   Customer name   |    Total    | Invoice date");
        IOPipe.printLine("-----------------------------------------------------");
        invoiceList.forEach(invoice -> IOPipe.printLine(
                Utils.leftPad(String.valueOf(invoice.getId()), 3) + " | " +
                        Utils.leftPad(invoice.getPerson().toString(), 17) + " | " +
                        Utils.leftPad(invoice.getTotalSum().toString(), 11) + " |  " +
                        invoice.getDate()));
        IOPipe.getLineByDialog(IOPipe.PRESS_ANYKEY_TEXT);
    }

    public static void printFormatPerson(Stream<Person> stream) {
        List<Person> personList = stream.collect(Collectors.toList());
        if (personList.isEmpty()) {
            IOPipe.printLine();                         /* Separating blank line */
            IOPipe.printLine(IOPipe.LIST_IS_EMPTY_TEXT);
            return;
        }
        IOPipe.printLine();                             /* Separating blank line */
        IOPipe.printLine(" ID |            Name           | Birth date");
        IOPipe.printLine("--------------------------------------------");
        personList.forEach(person -> IOPipe.printLine(
                Utils.leftPad(String.valueOf(person.getId()), 3) + " | " +
                        Utils.leftPad(person.getFirstName() + " " + person.getLastName(), 25) + " | " +
                        person.getBirthDate()));
        IOPipe.getLineByDialog(IOPipe.PRESS_ANYKEY_TEXT);
    }
}
