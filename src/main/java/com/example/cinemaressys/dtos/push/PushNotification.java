package com.example.cinemaressys.dtos.push;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PushNotification {
    String notificationTitle;
    String notificationMessage;
}
