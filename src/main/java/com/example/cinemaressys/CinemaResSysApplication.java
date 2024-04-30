package com.example.cinemaressys;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class CinemaResSysApplication {
    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(CinemaResSysApplication.class, args);
    }

    @PostConstruct
    public void checkConnection(){
        try{
            dataSource.getConnection();
            System.out.println("Nice");
        } catch (Exception e){
            System.err.println("Error" + e.getMessage());
        }
    }

}
