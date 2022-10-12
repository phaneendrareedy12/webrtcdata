package com.webrtc.controller;

import com.webrtc.dto.DeviceDto;
import com.webrtc.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/webrtc")
public class WebrtcController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/device")
    public ResponseEntity<?> addDevice(@Valid @RequestBody DeviceDto device) {
        return ResponseEntity.ok(deviceService.addDevice(device));
    }

    @GetMapping("/devices")
    public ResponseEntity<?> getAllDevices() {
        return ResponseEntity.ok(deviceService.findAll());
    }

    @GetMapping("/device/{deviceid}")
    public ResponseEntity<?> getDeviceById(@PathVariable("deviceid") String deviceid) {
        return ResponseEntity.ok(deviceService.findById(deviceid));
    }

    @PutMapping("/device")
    public ResponseEntity<?> updateDeviceDetails(@RequestBody DeviceDto device) {
        return ResponseEntity.ok(deviceService.updateDeviceDetails(device));
    }

    @GetMapping("/audit/device/{deviceid}")
    public ResponseEntity<?> getDeviceAuditInfo(@PathVariable("deviceid") String deviceid) {
        return ResponseEntity.ok(deviceService.getDeviceAuditInfo(deviceid));
    }
}
