package com.example.cinemaressys.services.user;

import com.example.cinemaressys.dtos.user.UserDTO;
import com.example.cinemaressys.dtos.user.UserLoginRequestDto;
import com.example.cinemaressys.dtos.user.UserRegisterRequestDto;
import com.example.cinemaressys.dtos.user.UserUpdateRequestDto;
import com.example.cinemaressys.entities.User;

public interface UserService {
    User registerUser(UserRegisterRequestDto userRegisterRequestDto);
    User loginUser(UserLoginRequestDto userLoginRequestDto);
    User findUserByEmail(String email);
    UserDTO findUserByToken(String token);
    void updateUserData(UserUpdateRequestDto updateRequest, String token);
    void updateUserPassword(String password, String token);
}
