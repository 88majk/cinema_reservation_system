package com.example.cinemaressys.services.devicetoken;

import com.example.cinemaressys.entities.DeviceToken;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface DeviceTokenService {
    void saveDeviceToken(String token);
    List<DeviceToken> getAllDeviceTokens();
}
