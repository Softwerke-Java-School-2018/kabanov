package com.softwerke.tables;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Sale {
    private final int personID;
    private final SeveralDevices[] severalDevices;
    private final LocalDate saleDate;
    private final BigDecimal totalSum;

    public Sale(int personID, SeveralDevices[] devicesSold, LocalDate saleDate, ArrayList<Device> devices) {
        this.personID = personID;
        this.severalDevices = devicesSold;
        this.saleDate = saleDate;
        BigDecimal sum = new BigDecimal(0);
        for (SeveralDevices severalDevices : devicesSold) {
            Device deviceToSell = devices.get(severalDevices.getDeviceID());
            BigDecimal internalSum = deviceToSell.getPrice().multiply(new BigDecimal(severalDevices.getAmount()));
            sum = sum.add(internalSum);
        }
        totalSum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public String toString() {
        return personID + " | " + saleDate + " | " + totalSum;
    }
}
