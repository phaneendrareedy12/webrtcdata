package com.webrtc.controller;

import com.webrtc.model.Device;
import com.webrtc.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/webrtc")
public class WebrtcController {

    @Autowired
    private DeviceRepository deviceRepository;

    @PostMapping("/device")
    public Flux<Device> addDevice(@RequestBody Device device) {
        return deviceRepository.insert(Mono.just(device));
    }

    @GetMapping("/devices")
    public Flux<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @GetMapping("/device/{deviceid}")
    public Mono<Device> getDeviceById(@PathVariable("deviceid")String deviceid) {
        return deviceRepository.findOne(Example.of(new Device(deviceid, null)));
    }
}
