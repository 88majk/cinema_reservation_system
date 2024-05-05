package com.example.cinemaressys.services.user;

import com.example.cinemaressys.dtos.user.UserLoginRequestDto;
import com.example.cinemaressys.dtos.user.UserRegisterRequestDto;

public interface UserService {
    void registerUser(UserRegisterRequestDto userRegisterRequestDto);
    void loginUser(UserLoginRequestDto userLoginRequestDto);
}
