package com.webrtc.webrtc.service;

import com.webrtc.mapper.DeviceMapper;
import com.webrtc.repository.DeviceRepository;
import com.webrtc.service.DeviceServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceImplTest {

    @InjectMocks
    private DeviceServiceImpl deviceServiceImpl;

    @Mock
    private DeviceRepository deviceRepository;

    @Spy
    private DeviceMapper deviceMapper;
}
