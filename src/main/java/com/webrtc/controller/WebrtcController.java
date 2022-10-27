package com.webrtc.controller;

import com.webrtc.document.Device;
import com.webrtc.document.DeviceDetailsAudit;
import com.webrtc.dto.DeviceDto;
import com.webrtc.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/webrtc")
public class WebrtcController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/device")
    public Device addDevice(@Valid @RequestBody DeviceDto device) {
        return deviceService.addDevice(device);
    }

    @GetMapping("/devices")
    public List<Device> getAllDevices() {
        return deviceService.findAll();
    }

    @GetMapping("/device/{deviceid}")
    public Device getDeviceById(@PathVariable("deviceid") String deviceid) {
        return deviceService.findById(deviceid);
    }

    @PutMapping("/device")
    public Device updateDeviceDetails(@RequestBody DeviceDto device) {
        return deviceService.updateDeviceDetails(device);
    }

    @GetMapping("/audit/device/{deviceid}")
    public List<DeviceDetailsAudit> getDeviceAuditInfo(@PathVariable("deviceid") String deviceid) {
        return deviceService.getDeviceAuditInfo(deviceid);
    }
}
