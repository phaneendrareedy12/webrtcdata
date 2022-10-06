package com.webrtc.model;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "devices")
public class Device {
    @Indexed
    private String deviceId;
    private DeviceDetails deviceDetails;
}
