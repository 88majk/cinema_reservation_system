package com.example.cinemaressys.services;

import com.example.cinemaressys.dtos.user.UserLoginRequestDto;
import com.example.cinemaressys.dtos.user.UserRegisterRequestDto;
import com.example.cinemaressys.entities.User;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.repositories.UserRepositories;
import com.example.cinemaressys.security.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    final private UserRepositories userRepositories;
    @Override
    public void registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        if (userRepositories.existsByEmail(userRegisterRequestDto.getEmail())){
            throw new MyException("Email address already exists. Try to log in.");
        }

        try{
            String hashPassword = PasswordEncoder.encodePassword(userRegisterRequestDto.getPassword());

            userRepositories.save(new User(userRegisterRequestDto.getName(),
                    userRegisterRequestDto.getSurname(),
                    userRegisterRequestDto.getEmail(),
                    hashPassword,
                    userRegisterRequestDto.getDateOfBirth()
            ));
        }
        catch (Exception e){
            throw new MyException("An unexpected error occurred during registration, " +
                    "please try again later.");
        }
    }

    @Override
    public void loginUser(UserLoginRequestDto userLoginRequestDto) {
        if (!userRepositories.existsByEmail(userLoginRequestDto.getEmail())){
            throw new MyException("Email address doesn't exists. First you need to register");
        }
        else {
            try {
                String encodedPassword = userRepositories.findPasswordByEmail(userLoginRequestDto.getEmail());
                boolean correctPassword = PasswordEncoder.comparePasswords(userLoginRequestDto.getPassword(), encodedPassword);

                if (!correctPassword) {
                    throw new MyException("Password is not correct.");
                }
            } catch (MyException e) {
                throw e;
            } catch (Exception e) {
                throw new MyException("An unexpected error occurred during login, please try again later.");
            }
        }
    }
}
