package com.webrtc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webrtc")
public class HealthConntroller {

    @GetMapping("/health")
    public ResponseEntity<?> getHealth() {
        return ResponseEntity.ok("OK");
    }
}
