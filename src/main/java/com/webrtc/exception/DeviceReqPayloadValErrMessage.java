package com.webrtc.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceReqPayloadValErrMessage {
    private int statusCode;
    private Date timestamp;
    private Map<String, String> validationErrors;
    private String details;
}
