package com.example.cinemaressys.services.devicetoken;

import com.example.cinemaressys.entities.DeviceToken;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.repositories.DeviceTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeviceTokenServiceImpl implements DeviceTokenService{
    private final DeviceTokenRepository deviceTokenRepository;
    @Override
    public void saveDeviceToken(String token) {

        if(deviceTokenRepository.findByDeviceToken(token).isEmpty()) {
            DeviceToken deviceToken = new DeviceToken();
            deviceToken.setDeviceToken(token);

            deviceTokenRepository.save(deviceToken);
        } else {
            throw new MyException("Token already exists in database");
        }
    }

    @Override
    public List<DeviceToken> getAllDeviceTokens() {
        return deviceTokenRepository.findAll();
    }


}
