package com.webrtc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class DeviceDetails {
    private String deviceName;
    private String deviceModel;
    private String deviceIp;
    private String screenLx;
    private String screenWx;
}
