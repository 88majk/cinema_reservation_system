package com.example.cinemaressys.services.user;

import com.example.cinemaressys.dtos.jwt.JwtClaims;
import com.example.cinemaressys.dtos.user.UserLoginRequestDto;
import com.example.cinemaressys.dtos.user.UserRegisterRequestDto;
import com.example.cinemaressys.dtos.user.UserUpdateRequestDto;
import com.example.cinemaressys.entities.Booking;
import com.example.cinemaressys.entities.BookingSeat;
import com.example.cinemaressys.entities.Role;
import com.example.cinemaressys.entities.User;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.repositories.*;
import com.example.cinemaressys.security.JwtTokenProvider;
import com.example.cinemaressys.security.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepositories userRepositories;
    final private RoleRepositories roleRepositories;
    final private AccessRepositories accessRepositories;
    final private BookingRepositories bookingRepositories;
    final private BookingSeatRepositories bookingSeatRepositories;

    @Override
    public User registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        if (userRepositories.existsByEmail(userRegisterRequestDto.getEmail())){
            throw new MyException("Email address already exists. Try to log in.");
        }

        try{
            String hashPassword = PasswordEncoder.encodePassword(userRegisterRequestDto.getPassword());
            Role defaultRole = roleRepositories.findByName("User");

            User user = new User(userRegisterRequestDto.getName(),
                    userRegisterRequestDto.getSurname(),
                    userRegisterRequestDto.getEmail(),
                    hashPassword,
                    userRegisterRequestDto.getDateOfBirth(),
                    defaultRole
            );

            userRepositories.save(user);
            return user;
        }
        catch (Exception e){
            throw new MyException("An unexpected error occurred during registration, " +
                    "please try again later.");
        }
    }

    @Override
    public User loginUser(UserLoginRequestDto userLoginRequestDto) {
        if (!userRepositories.existsByEmail(userLoginRequestDto.getEmail())){
            throw new MyException("Email address doesn't exists. First you need to register.");
        }
        else {
            try {
                String encodedPassword = userRepositories.findPasswordByEmail(userLoginRequestDto.getEmail());
                boolean correctPassword = PasswordEncoder.comparePasswords(userLoginRequestDto.getPassword(),
                        encodedPassword);

                if (!correctPassword) {
                    throw new MyException("Password is not correct.");
                }

                User user = userRepositories.findUserByEmail(userLoginRequestDto.getEmail());
                return user;
            } catch (MyException e) {
                throw e;
            } catch (Exception e) {
                throw new MyException("An unexpected error occurred during login, please try again later.");
            }
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepositories.findUserByEmail(email);
    }

    @Override
    public void updateUserData(UserUpdateRequestDto updateRequest, String token) {
        JwtClaims jwtClaims = JwtTokenProvider.decodeJwtToken(token);
        if(userRepositories.existsByEmail(jwtClaims.getEmail())) {
            User existingUser = userRepositories.findUserByEmail(jwtClaims.getEmail());

            if(!userRepositories.existsByEmail(updateRequest.getEmail())) {
                existingUser.setName(updateRequest.getName());
                existingUser.setSurname(updateRequest.getSurname());
                existingUser.setEmail(updateRequest.getEmail());
                existingUser.setDateOfBirth(updateRequest.getDateOfBirth());
                userRepositories.save(existingUser);
            } else {
                throw new MyException("Email you provided is already taken.");
            }
        } else {
            throw new MyException("User with email " + jwtClaims.getEmail() + " not found.");
        }
    }

    @Override
    public void updateUserPassword(String password, String token) {
        JwtClaims jwtClaims = JwtTokenProvider.decodeJwtToken(token);
        if(userRepositories.existsByEmail(jwtClaims.getEmail())) {
            User existingUser = userRepositories.findUserByEmail(jwtClaims.getEmail());

            String hashPassword = PasswordEncoder.encodePassword(password);

            existingUser.setPassword(hashPassword);
            userRepositories.save(existingUser);
        } else {
            throw new MyException("User with email " + jwtClaims.getEmail() + " not found.");
        }
    }

    @Override
    public void deleteAccount(String roleName, String email) {
        User user = userRepositories.findUserByEmail(email);
        if (user == null) {
            throw new MyException("Such email does not exist");
        }
        Role role = roleRepositories.findByName(roleName);
        if (role == null) {
            throw new MyException("The user role does not exist.");
        }
        if (role.getName().equals("Administrator") || role.getName().equals("Cinema manager")) {
            try {
                accessRepositories.deleteByUserUserId(user.getUserId());
            } catch (Exception e) {
                throw new MyException("Failed to delete all accesses.");
            }
        }

        List<Booking> allBookingByUser = bookingRepositories.getBookingsByUserId(user.getUserId());
        for (Booking booking : allBookingByUser) {
            try {
                bookingSeatRepositories.deleteByBookingBookingId(booking.getBookingId());

            } catch (Exception e) {
                throw new MyException("Failed to delete all bookingSeat");
            }

            try {
                bookingRepositories.deleteByBookingId(booking.getBookingId());
            } catch (Exception e) {
                throw new MyException("Failed to delete all booking");
            }
        }

        try {
            userRepositories.deleteByUserId(user.getUserId());
        } catch (Exception e) {
            throw new MyException("Failed to delete user");
        }
    }
}
