package com.webrtc.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "devices")
public class Device {
    private String id;
    private String name;
    private String ip;
}
