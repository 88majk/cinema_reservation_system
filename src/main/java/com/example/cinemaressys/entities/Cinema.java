package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name= "cinemas")
@Data
@AllArgsConstructor
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    private String name;
    private String localization;
    private String phoneNumber;
    private String emailContact;

    public Cinema(String address, String name, String localization, String phoneNumber, String emailContact) {
        this.address = address;
        this.name = name;
        this.localization = localization;
        this.phoneNumber = phoneNumber;
        this.emailContact = emailContact;
    }

    public Cinema() {}
}
