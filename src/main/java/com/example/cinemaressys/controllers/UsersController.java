package com.example.cinemaressys.controllers;

import com.example.cinemaressys.dtos.jwt.JwtClaims;
import com.example.cinemaressys.dtos.jwt.TokenRequestDto;
import com.example.cinemaressys.dtos.user.TokenResponse;
import com.example.cinemaressys.dtos.user.UserLoginRequestDto;
import com.example.cinemaressys.dtos.user.UserRegisterRequestDto;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cinemaressys.entities.User;
import com.example.cinemaressys.security.JwtTokenProvider;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {
    private final UserService userService;
    @Autowired
    public UsersController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        try {
            userService.registerUser(userRegisterRequestDto);
            return ResponseEntity.ok().body("User registered successfully!");
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred during registration, please try again later." + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        try{
            User user = userService.loginUser(userLoginRequestDto);
            String token = JwtTokenProvider.generateToken(user);
            TokenResponse tokenResponse = new TokenResponse(token);
            return ResponseEntity.ok().body(tokenResponse);
        } catch(MyException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/testToken")
    public ResponseEntity<?> test(@RequestBody TokenRequestDto token) {
        try{
            JwtClaims jwtClaims = JwtTokenProvider.decodeJwtToken(token.getToken());
            return ResponseEntity.ok().body(jwtClaims.getUserId()+" "+jwtClaims.getRole());
        } catch(MyException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
