package com.webrtc.controller;

import com.webrtc.model.Device;
import com.webrtc.repository.DeviceRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/webrtc")
public class WebrtcController {

    @Autowired
    private DeviceRepository deviceRepository;

    @PostMapping("/device")
    public ResponseEntity<?> addDevice(@RequestBody Device device) {
        deviceRepository.save(device);
        return ResponseEntity.ok(device);
    }

    @GetMapping("/device")
    public ResponseEntity<?> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/device/{deviceid}")
    public ResponseEntity<?> getDeviceById(@PathVariable("deviceid")String deviceid) {
        Optional<Device> device = deviceRepository.findById(deviceid);
        return ResponseEntity.ok(device.get());
    }
}
