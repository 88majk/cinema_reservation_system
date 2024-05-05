package com.example.cinemaressys.controllers;

import com.example.cinemaressys.dtos.access.AccessCreateAdminRequestDto;
import com.example.cinemaressys.dtos.access.AccessDeleteAdminRequestDto;
import com.example.cinemaressys.dtos.user.UserRegisterRequestDto;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.services.access.AccessService;
import com.example.cinemaressys.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AccessController {
    private final AccessService accessService;

    @Autowired
    public AccessController(AccessService accessService){
        this.accessService = accessService;
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody AccessCreateAdminRequestDto accessCreateAdminRequestDto) {
        try {
            accessService.createAdmin(accessCreateAdminRequestDto);
            return ResponseEntity.ok().body("New admin has been added!");
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/deleteAdmin")
    public ResponseEntity<?> deleteAdmin(@RequestBody AccessDeleteAdminRequestDto accessDeleteAdminRequestDto) {
        try {
            accessService.deleteAdmin(accessDeleteAdminRequestDto);
            return ResponseEntity.ok().body("Administrator has been deleted!");
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
