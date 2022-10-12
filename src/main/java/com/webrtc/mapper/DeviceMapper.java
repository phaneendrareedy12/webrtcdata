package com.webrtc.mapper;

import com.webrtc.document.Device;
import com.webrtc.document.DeviceDetailsAudit;
import com.webrtc.dto.DeviceDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DeviceMapper {
    public Device mapDeviceDtoToNewDeviceDocument(DeviceDto deviceDto) {
        return new Device().builder()
                .deviceId(deviceDto.getDeviceId())
                .deviceDetails(deviceDto.getDeviceDetails())
                .createdTs(LocalDateTime.now())
                .updatedTs(LocalDateTime.now())
                .build();
    }

    public DeviceDetailsAudit mapDeviceDtoToDeviceDetailsAudit(DeviceDto deviceDto) {
        return new DeviceDetailsAudit().builder()
                .deviceId(deviceDto.getDeviceId())
                .deviceDetails(deviceDto.getDeviceDetails())
                .createdTs(LocalDateTime.now())
                .build();
    }
}
