package com.webrtc.service;

import com.webrtc.model.Device;

import java.util.List;
import java.util.Optional;

public interface DeviceService {

    Device addDevice(Device device);
    List<Device> findAll();
    Device findById(String deviceid);
    Device updateDeviceDetails(Device device);
}
