package com.example.cinemaressys.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    public static String encodePassword(String password) {
        try {
            // obiekt MessageDigest dla SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // haszowanie hasła
            byte[] hash = digest.digest(password.getBytes());

            // Konwersja tablicy bajtów na postać szesnastkową
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // zahaszowane hasło w postaci szesnastkowej
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Obsługa wyjątku, gdy algorytm nie jest dostępny
            e.printStackTrace();
            return null;
        }
    }

    public static boolean comparePasswords(String inputPassword, String hashedPasswordFromDatabase) {
        // haszowwanie wprowadzonego przez użytkownika hasła
        String hashedInputPassword = encodePassword(inputPassword);

        // Porównanie zahaszowanego hasła
        return hashedInputPassword.equals(hashedPasswordFromDatabase);
    }
}
