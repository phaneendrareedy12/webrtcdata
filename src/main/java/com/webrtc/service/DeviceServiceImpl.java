package com.webrtc.service;

import com.webrtc.exception.DeviceNotFoundException;
import com.webrtc.model.Device;
import com.webrtc.repository.DeviceRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    private MongoTemplate mongoTemplate;

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

    @Override
    public Device updateDeviceDetails(Device device) {
        Query query = new Query();
        query.addCriteria(Criteria.where("deviceId").is(device.getDeviceId()));
        Update update = new Update();
        update.set("deviceDetails", device.getDeviceDetails());
        Device updatedDeviceDtl = mongoTemplate.findAndModify(query, update, Device.class);
        log.info("updated device details are : " + updatedDeviceDtl);
        if(ObjectUtils.isEmpty(updatedDeviceDtl))
            throw new DeviceNotFoundException("There is no device with give id , So no update done");
        return device;
     }
}
