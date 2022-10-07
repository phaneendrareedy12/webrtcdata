package com.webrtc.controller;

import com.webrtc.model.Device;
import com.webrtc.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/webrtc")
public class WebrtcController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/device")
    public ResponseEntity<?> addDevice(@RequestBody Device device) {
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
    public ResponseEntity<?> updateDeviceDetails(@RequestBody Device device) {
        return ResponseEntity.ok(deviceService.updateDeviceDetails(device));
    }
}
