package com.webrtc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto {

    @NotNull(message = "Device Id is mandatory.")
    @Valid
    private String deviceId;
    @NotNull(message = "Device details are mandatory.")
    @Valid
    private Map<String, String> deviceDetails;
}
