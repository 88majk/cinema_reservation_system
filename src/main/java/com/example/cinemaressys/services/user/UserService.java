package com.example.cinemaressys.services.user;

import com.example.cinemaressys.dtos.user.UserLoginRequestDto;
import com.example.cinemaressys.dtos.user.UserRegisterRequestDto;
import com.example.cinemaressys.entities.User;

public interface UserService {
    void registerUser(UserRegisterRequestDto userRegisterRequestDto);
    User loginUser(UserLoginRequestDto userLoginRequestDto);
}
