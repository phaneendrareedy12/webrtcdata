package com.webrtc.service;

import com.webrtc.document.Device;
import com.webrtc.document.DeviceDetailsAudit;
import com.webrtc.dto.DeviceDto;

import java.util.List;

public interface DeviceService {

    Device addDevice(DeviceDto device);
    List<Device> findAll();
    Device findById(String deviceid);
    Device updateDeviceDetails(DeviceDto device);
    List<DeviceDetailsAudit> getDeviceAuditInfo(String deviceid);
}
