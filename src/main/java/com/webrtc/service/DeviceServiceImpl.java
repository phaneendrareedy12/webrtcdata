package com.webrtc.service;

import com.webrtc.exception.DeviceNotFoundException;
import com.webrtc.model.Device;
import com.webrtc.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public Device addDevice(final Device device) {
        deviceRepository.save(device);
        return device;
    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device findById(final String deviceid) {
        return deviceRepository.findByDeviceId(deviceid).orElseThrow(() -> new DeviceNotFoundException("No device found with the given device id"));
    }
}
