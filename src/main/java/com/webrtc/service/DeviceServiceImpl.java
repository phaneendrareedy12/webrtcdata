package com.webrtc.service;

import com.webrtc.document.Device;
import com.webrtc.document.DeviceDetailsAudit;
import com.webrtc.dto.DeviceDto;
import com.webrtc.exception.DeviceDetailsPresentException;
import com.webrtc.exception.DeviceNotFoundException;
import com.webrtc.mapper.DeviceMapper;
import com.webrtc.repository.DeviceAuditRepository;
import com.webrtc.repository.DeviceRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceAuditRepository deviceAuditRepository;

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public Device addDevice(DeviceDto deviceDto) {
        Optional<Device> deviceFromDB = deviceRepository.findByDeviceId(deviceDto.getDeviceId());
        return deviceFromDB.isPresent() ?
                CompareAndUpdateDevice(deviceDto, deviceFromDB.get()) : saveDevice(deviceDto);
    }

    private Device CompareAndUpdateDevice(DeviceDto deviceDto, Device deviceFromDB) {
        if(checkDeviceInfo(deviceDto, deviceFromDB))
            throw new DeviceDetailsPresentException("Device details are already present with given info");
        return updateDevice(deviceDto);
    }

    private boolean checkDeviceInfo(DeviceDto deviceDto, Device deviceFromDB) {
        if(deviceDto.getDeviceDetails().size() != deviceFromDB.getDeviceDetails().size())
            return false;
        return deviceDto.getDeviceDetails().entrySet().stream()
                .allMatch(e -> e.getValue().equals(deviceFromDB.getDeviceDetails().get(e.getKey())));
    }

    private Device saveDevice(DeviceDto deviceDto) {
        Device savedDevice = deviceRepository.save(deviceMapper.mapDeviceDtoToNewDeviceDocument(deviceDto));
        saveDeviceAuditInfo(deviceDto);
        return savedDevice;
    }

    private Device updateDevice(DeviceDto deviceDto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("deviceId").is(deviceDto.getDeviceId()));
        Update update = new Update();
        update.set("deviceDetails", deviceDto.getDeviceDetails());
        update.set("updatedTs", LocalDateTime.now());
        Device updatedDeviceDtl = mongoTemplate.findAndModify(query, update, Device.class);
        log.info("updated device details are : " + updatedDeviceDtl);
        saveDeviceAuditInfo(deviceDto);
        return updatedDeviceDtl;
    }

    private void saveDeviceAuditInfo(DeviceDto deviceDto) {
        deviceAuditRepository.save(deviceMapper.mapDeviceDtoToDeviceDetailsAudit(deviceDto));
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
    public Device updateDeviceDetails(DeviceDto device) {
        Query query = new Query();
        query.addCriteria(Criteria.where("deviceId").is(device.getDeviceId()));
        Update update = new Update();
        update.set("deviceDetails", device.getDeviceDetails());
        update.set("updatedTs", LocalDateTime.now());
        Device updatedDeviceDtl = mongoTemplate.findAndModify(query, update, Device.class);
        log.info("updated device details are : " + updatedDeviceDtl);
        if(ObjectUtils.isEmpty(updatedDeviceDtl))
            throw new DeviceNotFoundException("There is no device with give id , So no update done");
        return updatedDeviceDtl;
     }

    @Override
    public List<DeviceDetailsAudit> getDeviceAuditInfo(String deviceid) {
        List<DeviceDetailsAudit> deviceDetailsAuditList = deviceAuditRepository.findByDeviceId(deviceid).get();
        if(deviceDetailsAuditList.isEmpty())
            throw new DeviceNotFoundException("No device found with the given device id");
        return deviceDetailsAuditList;
    }
}
