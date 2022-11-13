package com.webrtc.webrtc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webrtc.controller.WebrtcController;
import com.webrtc.document.Device;
import com.webrtc.document.DeviceDetailsAudit;
import com.webrtc.dto.DeviceDto;
import com.webrtc.service.DeviceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebrtcController.class)
public class WebrtcControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    DeviceService deviceService;

    @DisplayName("unit test for get all devices api (/webrtc/devices)")
    @Test
    public void testGetAllDevices() throws Exception {
        when(deviceService.findAll()).thenReturn(getMockDeviceDetails());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/webrtc/devices")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].deviceId", is("Samsung555")))
                .andExpect(jsonPath("$[1].deviceId", is("Apple555")));
    }

    @DisplayName("unit test for get device audit info api (/webrtc/audit/device/{deviceid})")
    @Test
    public void testGetDeviceAuditInfo() throws Exception {
        when(deviceService.getDeviceAuditInfo("samsung")).thenReturn(getMockDeviceAuditInfo());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/webrtc/audit/device/samsung")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].deviceId", is("samsung")))
                .andExpect(jsonPath("$[1].deviceId", is("samsung")));
    }

    @DisplayName("unit test for get device by id api (/webrtc/device/{deviceid})")
    @Test
    public void testGetDeviceById() throws Exception {
        when(deviceService.findById("samsung")).thenReturn(getDeviceMockData());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/webrtc/device/samsung")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
                //.andExpect(jsonPath("$.deviceId", is("samsung")));
    }

    @DisplayName("unit test for add device api (/webrtc/device)")
    @Test
    public void testAddDevice() throws Exception {
        Map<String, String> deviceDetails= new HashMap<>();
        deviceDetails.put("deviceName", "samsung");
        deviceDetails.put("deviceip", "10.65.34.0");
        DeviceDto deviceDto = DeviceDto.builder()
                .deviceDetails(deviceDetails)
                .deviceId("samsung")
                .build();
        when(deviceService.addDevice(deviceDto)).thenReturn(getDeviceMockData());
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/webrtc/device")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(deviceDto));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.deviceId", is("samsung")));
    }

    @DisplayName("unit test for update device details api (/webrtc/device)")
    @Test
    public void testUpdateDeviceDetails() throws Exception {
        Map<String, String> deviceDetails= new HashMap<>();
        deviceDetails.put("deviceName", "samsung");
        deviceDetails.put("deviceip", "10.65.34.0");
        DeviceDto deviceDto = DeviceDto.builder()
                .deviceDetails(deviceDetails)
                .deviceId("samsung")
                .build();
        when(deviceService.updateDeviceDetails(deviceDto)).thenReturn(getDeviceMockData());
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/webrtc/device")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(deviceDto));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.deviceId", is("samsung")));
    }

    private Device getDeviceMockData() {
        Map<String, String> deviceDetails= new HashMap<>();
        deviceDetails.put("deviceName", "samsung");
        deviceDetails.put("deviceip", "10.65.34.0");
        return new Device().builder()
                .deviceId("samsung")
                .deviceDetails(deviceDetails)
                .createdTs(LocalDateTime.now())
                .updatedTs(LocalDateTime.now())
                .build();
    }

    private List<DeviceDetailsAudit> getMockDeviceAuditInfo() {
        List<DeviceDetailsAudit> devices = new ArrayList<>();
        Map<String, String> deviceDetailsOne= new HashMap<>();
        deviceDetailsOne.put("deviceName", "samsung");
        deviceDetailsOne.put("deviceip", "10.65.34.0");
        devices.add(new DeviceDetailsAudit().builder()
                .deviceId("samsung")
                .deviceDetails(deviceDetailsOne)
                .createdTs(LocalDateTime.now())
                .build());
        Map<String, String> deviceDetailstwo= new HashMap<>();
        deviceDetailsOne.put("deviceName", "samsung");
        deviceDetailsOne.put("deviceip", "10.89.56.0");
        devices.add(new DeviceDetailsAudit().builder()
                .deviceId("samsung")
                .deviceDetails(deviceDetailsOne)
                .createdTs(LocalDateTime.now())
                .build());
        return devices;
    }

    private List<Device> getMockDeviceDetails() {
        List<Device> devices = new ArrayList<>();
        Map<String, String> deviceDetailsOne= new HashMap<>();
        deviceDetailsOne.put("deviceName", "samsung");
        deviceDetailsOne.put("deviceip", "10.65.34.0");
        devices.add(new Device().builder()
                .deviceId("Samsung555")
                .deviceDetails(deviceDetailsOne)
                .createdTs(LocalDateTime.now())
                .updatedTs(LocalDateTime.now())
                .build());
        Map<String, String> deviceDetailstwo= new HashMap<>();
        deviceDetailsOne.put("deviceName", "apple");
        deviceDetailsOne.put("deviceip", "10.89.56.0");
        devices.add(new Device().builder()
                .deviceId("Apple555")
                .deviceDetails(deviceDetailsOne)
                .createdTs(LocalDateTime.now())
                .updatedTs(LocalDateTime.now())
                .build());
        return devices;
    }

}
