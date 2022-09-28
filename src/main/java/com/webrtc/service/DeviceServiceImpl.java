package com.webrtc.service;

import com.webrtc.model.Device;
import com.webrtc.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public Device addDevice(Device device) {
        deviceRepository.save(device);
        return device;
    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Optional<Device> findById(String deviceid) {
        Optional<Device> device = deviceRepository.findOne(Example.of(new Device(deviceid, null)));
        return device;
    }
}
