package com.example.cinemaressys.services.notifications;

import com.example.cinemaressys.dtos.push.PushNotification;
import com.example.cinemaressys.entities.DeviceToken;
import com.example.cinemaressys.services.devicetoken.DeviceTokenService;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FirebaseMessagingService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;
    private DeviceTokenService deviceTokenService;

    public FirebaseMessagingService(DeviceTokenService deviceTokenService) {
        this.deviceTokenService = deviceTokenService;
    }

    public String sendNotificationByToken(String deviceToken, PushNotification pushNotification) {
        Notification notification = Notification
                .builder()
                .setTitle(pushNotification.getNotificationTitle())
                .setBody(pushNotification.getNotificationMessage())
                .build();

        Message message = Message
                .builder()
                .setToken(deviceToken)
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return "Success sending notification";
        } catch (FirebaseMessagingException e) {
            return "Error sending notification";
        }
    }

    public String sendNotificationToAll(PushNotification pushNotification) {
        List<DeviceToken> deviceTokens = deviceTokenService.getAllDeviceTokens();
        if(!deviceTokens.isEmpty()) {
            List<String> tokens = deviceTokens.stream()
                    .map(DeviceToken::getDeviceToken)
                    .collect(Collectors.toList());

            Notification notification = Notification
                    .builder()
                    .setTitle(pushNotification.getNotificationTitle())
                    .setBody(pushNotification.getNotificationMessage())
                    .build();

            MulticastMessage message = MulticastMessage
                    .builder()
                    .setNotification(notification)
                    .addAllTokens(tokens)
                    .build();


            try {
                firebaseMessaging.sendMulticast(message);
                return "Success sending notification to all tokens";
            } catch (FirebaseMessagingException e) {
                return "Error sending notification to all tokens";
            }
        } else {
            return "There is not any token in database";
        }
    }
}
