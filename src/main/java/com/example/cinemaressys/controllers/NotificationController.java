package com.example.cinemaressys.controllers;

import com.example.cinemaressys.dtos.push.PushNotification;
import com.example.cinemaressys.services.notifications.FirebaseMessagingService;
import com.google.firebase.messaging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @PostMapping("/send-to-all")
    public String sendNotificationToAllDevices(@RequestBody PushNotification pushNotification) {
        return firebaseMessagingService.sendNotificationToAll(pushNotification);
    }

    @PostMapping("/send-by-token/{device_token}")
    public String sendNotificationByToken(@PathVariable String device_token) {
        return firebaseMessagingService.sendNotificationByToken(device_token);
    }
}


