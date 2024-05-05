package com.example.cinemaressys.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "RoleId")
    private Role role;

    public User(String name, String surname, String email, String password, LocalDate dateOfBirth, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }
    public User(){
    }

}
