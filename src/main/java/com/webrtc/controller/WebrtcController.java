package com.webrtc.controller;

import com.webrtc.model.Device;
import com.webrtc.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> getDeviceById(@PathVariable("deviceid")String deviceid) {
        Optional<Device> device = deviceService.findById(deviceid);
        if(device.isPresent()) {
            return ResponseEntity.ok(device.get());
        }
        return ResponseEntity.ok("Device not found");
    }
}
