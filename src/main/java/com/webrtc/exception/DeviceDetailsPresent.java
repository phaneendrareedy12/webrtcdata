package com.webrtc.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDetailsPresent {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String details;
}
