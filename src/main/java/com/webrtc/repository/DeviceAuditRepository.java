package com.webrtc.repository;

import com.webrtc.document.Device;
import com.webrtc.document.DeviceDetailsAudit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceAuditRepository extends MongoRepository<DeviceDetailsAudit, String> {
    Optional<List<DeviceDetailsAudit>> findByDeviceId(String deviceId);
}
