package com.example.cinemaressys.controllers;

import com.example.cinemaressys.dtos.jwt.JwtClaims;
import com.example.cinemaressys.dtos.jwt.TokenRequestDto;
import com.example.cinemaressys.dtos.user.*;
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
            User user = userService.registerUser(userRegisterRequestDto);
            String token = JwtTokenProvider.generateToken(user);
            TokenResponse tokenResponse = new TokenResponse(token);
            return ResponseEntity.ok().body(tokenResponse);
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
            return ResponseEntity.ok().body(new TestTokenResponse(true));
        } catch(MyException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{user_email}")
    public ResponseEntity<?> findUserByEmail(@PathVariable String user_email) {
        try {
            User user = userService.findUserByEmail(user_email);
            return ResponseEntity.ok().body(user);
        } catch (MyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("update/{token}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateRequestDto requestDto, @PathVariable String token) {
        try{
            userService.updateUserData(requestDto, token);
            return ResponseEntity.ok("User was updated succesfully.");
        } catch (MyException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("changePassword/{token}")
    public ResponseEntity<?> updateUserPassword(@RequestBody String password, @PathVariable String token) {
        try {
            userService.updateUserPassword(password, token);
            return ResponseEntity.ok("Users password was updated successfully.");
        } catch (MyException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
