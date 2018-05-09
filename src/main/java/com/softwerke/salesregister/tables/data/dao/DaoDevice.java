package com.softwerke.salesregister.tables.data.dao;

import com.softwerke.salesregister.tables.data.storage.Storage;
import com.softwerke.salesregister.tables.device.Device;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class DaoDevice extends Dao {
    public DaoDevice(Storage storage) {
        super(storage);
    }

    public void addDevice(Device device) {
        List<Device> deviceList = storage.getDeviceList();
        deviceList.add(Objects.requireNonNull(device));
    }

    public void updateDevice(Device device) {
        Objects.requireNonNull(device);
        storage.getDeviceList().set(device.getId(), device);
    }

    public Device getDevice(int id) {
        return storage.getDeviceList().get(id);
    }

    public int getSize() {
        return storage.getDeviceList().size();
    }

    public Stream<Device> devices() {
        return storage.getDeviceList().stream();
    }

    public void clearStorage() {
        storage.getDeviceList().clear();
    }
}
