package com.webrtc.webrtc.service;

import com.webrtc.document.Device;
import com.webrtc.document.DeviceDetailsAudit;
import com.webrtc.dto.DeviceDto;
import com.webrtc.exception.DeviceDetailsPresentException;
import com.webrtc.exception.DeviceNotFoundException;
import com.webrtc.mapper.DeviceMapper;
import com.webrtc.repository.DeviceAuditRepository;
import com.webrtc.repository.DeviceRepository;
import com.webrtc.service.DeviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceImplTest {

    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceAuditRepository deviceAuditRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Spy
    private DeviceMapper deviceMapper;

    private DeviceDto deviceDto;

    private Device device;

    @BeforeEach
    public void setUp() {
        Map<String, String> deviceDetails= new HashMap<>();
        deviceDetails.put("deviceName", "samsung");
        deviceDetails.put("deviceip", "10.65.34.0");
        deviceDto = DeviceDto.builder()
                .deviceDetails(deviceDetails)
                .deviceId("samsung")
                .build();
        device = new Device().builder()
                .deviceDetails(deviceDetails)
                .deviceId("samsung")
                .updatedTs(LocalDateTime.now())
                .createdTs(LocalDateTime.now())
                .build();
    }

    @DisplayName("Test case for DeviceServiceImpl.addDevice() method when there is no existing device in DB")
    @Test
    public void testAddDevice_newDevice() {
        when(deviceRepository.findByDeviceId(any())).thenReturn(Optional.empty());
        when(deviceRepository.save(any())).thenReturn(device);
        when(deviceAuditRepository.save(any())).thenReturn(null);
        Device device = deviceServiceImpl.addDevice(deviceDto);
        assertNotNull(device);
        assertEquals("samsung", device.getDeviceId());
        assertNotNull(device.getDeviceDetails());
        assertEquals("samsung", device.getDeviceDetails().get("deviceName"));
        assertEquals("10.65.34.0", device.getDeviceDetails().get("deviceip"));
    }

    @DisplayName("Test case for DeviceServiceImpl.addDevice() method when there is existing device in DB")
    @Test
    public void testAddDevice_existingDevice_with_updatedData() {
        Map<String, String> deviceDetails= new HashMap<>();
        deviceDetails.put("deviceName", "samsung");
        deviceDetails.put("deviceip", "10.65.34.0");
        deviceDetails.put("screenLx","1000");
        Device updatedDevice = new Device().builder()
                .deviceDetails(deviceDetails)
                .deviceId("samsung")
                .updatedTs(LocalDateTime.now())
                .createdTs(LocalDateTime.now())
                .build();
        when(deviceRepository.findByDeviceId(any())).thenReturn(Optional.of(device));
        when(mongoTemplate.findAndModify(any(), any(), any())).thenReturn(updatedDevice);
        when(deviceAuditRepository.save(any())).thenReturn(null);

        Map<String, String> deviceDetailsDto= new HashMap<>();
        deviceDetailsDto.put("deviceName", "samsung");
        deviceDetailsDto.put("deviceip", "10.65.34.0");
        deviceDetailsDto.put("screenLx","1000");
        DeviceDto updatedDeviceDto = DeviceDto.builder()
                .deviceDetails(deviceDetailsDto)
                .deviceId("samsung")
                .build();
        Device device = deviceServiceImpl.addDevice(updatedDeviceDto);
        assertNotNull(device);
        assertEquals("samsung", device.getDeviceId());
        assertNotNull(device.getDeviceDetails());
        assertEquals("samsung", device.getDeviceDetails().get("deviceName"));
        assertEquals("10.65.34.0", device.getDeviceDetails().get("deviceip"));
        //assertEquals("1000", device.getDeviceDetails().get("screenLx"));
    }

    @DisplayName("Test case for DeviceServiceImpl.addDevice() method when there is existing device in DB and the details in payload are also same")
    @Test
    public void testAddDevice_existingDevice_sameDetails() {
        when(deviceRepository.findByDeviceId(any())).thenReturn(Optional.of(device));
        assertThrows(DeviceDetailsPresentException.class, () -> deviceServiceImpl.addDevice(deviceDto));
    }

    @DisplayName("Test case for DeviceServiceImpl.findAll()")
    @Test
    public void testFindAll() {
        List<Device> devices = new ArrayList<>();
        Map<String, String> deviceDetails= new HashMap<>();
        deviceDetails.put("deviceName", "samsung");
        deviceDetails.put("deviceip", "10.65.34.0");
        devices.add(new Device().builder()
                .deviceDetails(deviceDetails)
                .deviceId("samsung")
                .updatedTs(LocalDateTime.now())
                .createdTs(LocalDateTime.now())
                .build());
        devices.add(new Device().builder()
                .deviceDetails(deviceDetails)
                .deviceId("redmi")
                .updatedTs(LocalDateTime.now())
                .createdTs(LocalDateTime.now())
                .build());

        when(deviceRepository.findAll()).thenReturn(devices);
        List<Device> devicesInDB = deviceServiceImpl.findAll();
        assertNotNull(devicesInDB);
        assertEquals(2, devicesInDB.size());
    }

    @DisplayName("Test case for DeviceServiceImpl.findById()")
    @Test
    public void testFindById() {
        when(deviceRepository.findByDeviceId(any())).thenReturn(Optional.of(device));
        Optional<Device> deviceById = Optional.ofNullable(deviceServiceImpl.findById("samsung"));
        assertTrue(deviceById.isPresent());
        Device deviceFromDb = deviceById.get();
        assertEquals("samsung", deviceFromDb.getDeviceId());
        assertNotNull(device.getDeviceDetails());
        assertEquals("samsung", device.getDeviceDetails().get("deviceName"));
        assertEquals("10.65.34.0", device.getDeviceDetails().get("deviceip"));
    }

    @DisplayName("Test case for DeviceServiceImpl.findById() when device not found in DB")
    @Test
    public void testFindById_DeviceNotFoundException() {
        when(deviceRepository.findByDeviceId(any())).thenReturn(Optional.empty());
        assertThrows(DeviceNotFoundException.class, () -> deviceServiceImpl.findById("samsung"));
    }

    @DisplayName("Test case for DeviceServiceImpl.getDeviceAuditInfo()")
    @Test
    public void testGetDeviceAuditInfo() {
        List<DeviceDetailsAudit> devices = new ArrayList<>();
        Map<String, String> deviceDetails= new HashMap<>();
        deviceDetails.put("deviceName", "samsung");
        deviceDetails.put("deviceip", "10.65.34.0");
        devices.add(new DeviceDetailsAudit().builder()
                .deviceDetails(deviceDetails)
                .deviceId("samsung")
                .createdTs(LocalDateTime.now())
                .build());
        devices.add(new DeviceDetailsAudit().builder()
                .deviceDetails(deviceDetails)
                .deviceId("samsung")
                .createdTs(LocalDateTime.now())
                .build());
        when(deviceAuditRepository.findByDeviceId("samsung")).thenReturn(Optional.of(devices));
        Optional<List<DeviceDetailsAudit>> deviceDetailsAudit = Optional.ofNullable(deviceServiceImpl.getDeviceAuditInfo("samsung"));
        assertNotNull(deviceDetailsAudit);
        assertTrue(deviceDetailsAudit.isPresent());
        List<DeviceDetailsAudit> auditList = deviceDetailsAudit.get();
        assertEquals(2, auditList.size());
    }

    @DisplayName("Test case for DeviceServiceImpl.getDeviceAuditInfo() when there is no records in DB with give deviceId")
    @Test
    public void testGetDeviceAuditInfo_DeviceNotFoundException() {
        when(deviceAuditRepository.findByDeviceId("samsung")).thenReturn(Optional.of(new ArrayList<>()));
        assertThrows(DeviceNotFoundException.class, () -> deviceServiceImpl.getDeviceAuditInfo("samsung"));
    }
}
