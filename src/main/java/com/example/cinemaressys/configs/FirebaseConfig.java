package com.example.cinemaressys.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException{
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("cinemaressys-mobile-firebase-adminsdk-85kfh-ca77a00802.json")
                        .getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials).build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "CinemaResSys-mobile");
        return  FirebaseMessaging.getInstance(app);
    }

}