package com.webrtc.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "devicehistory")
public class DeviceDetailsAudit {

    private String deviceId;
    private Map<String, String> deviceDetails;
    private LocalDateTime createdTs;
}
