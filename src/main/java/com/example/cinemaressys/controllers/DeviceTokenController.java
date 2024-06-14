package com.example.cinemaressys.controllers;

import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.services.devicetoken.DeviceTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device-token")
@AllArgsConstructor
public class DeviceTokenController {
    private DeviceTokenService deviceTokenService;
    @PostMapping("/save-token")
    public ResponseEntity<String> saveDeviceToken(@RequestBody String token) {
        try {
            deviceTokenService.saveDeviceToken(token);
            return ResponseEntity.ok().body("New device token has been saved");
        } catch(MyException e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }


}
