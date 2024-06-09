package com.example.cinemaressys.services.access;

import com.example.cinemaressys.dtos.access.AccessCreateAdminRequestDto;
import com.example.cinemaressys.dtos.access.AccessDeleteAdminRequestDto;
import com.example.cinemaressys.dtos.jwt.JwtClaims;
import com.example.cinemaressys.entities.Access;
import com.example.cinemaressys.entities.Cinema;
import com.example.cinemaressys.entities.Role;
import com.example.cinemaressys.entities.User;
import com.example.cinemaressys.exception.MyException;
import com.example.cinemaressys.repositories.AccessRepositories;
import com.example.cinemaressys.repositories.CinemaRepositories;
import com.example.cinemaressys.repositories.RoleRepositories;
import com.example.cinemaressys.repositories.UserRepositories;
import com.example.cinemaressys.security.JwtTokenProvider;
import com.example.cinemaressys.security.PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
public class AccessServiceImpl implements AccessService{
    final private UserRepositories userRepositories;
    final private CinemaRepositories cinemaRepositories;
    final private AccessRepositories accessRepositories;
    final private RoleRepositories roleRepositories;


    @Override
    public void createAdmin(AccessCreateAdminRequestDto accessCreateAdminRequestDto) {
        if (accessCreateAdminRequestDto.getToken() == null) {
            throw new MyException("You have to login first!.");
        }
        JwtClaims jwtClaims = JwtTokenProvider.decodeJwtToken(accessCreateAdminRequestDto.getToken());
        User admin = userRepositories.findUserByEmail(jwtClaims.getEmail());
        if (admin == null) {
            throw new MyException("Admin doesn't exist!");
        }
        if (!admin.getRole().getName().equals("Administrator")) {
            throw new MyException("This user dont have admin permission to create admin");
        }

        User user = userRepositories.findUserByEmail(accessCreateAdminRequestDto.getEmail());
        if (user == null) {
            throw new MyException("User with the provided email does not exist. Please register this user first.");
        }
        if (accessRepositories.existsByUserUserId(user.getUserId())) {
            throw new MyException("This user is already an admin.");
        }
        if (accessCreateAdminRequestDto.isAllCinema()){
            try {
                List<Cinema> allCinemas = cinemaRepositories.findAll();
                Role adminRole = roleRepositories.findByName("Administrator");

                for (Cinema cinema : allCinemas) {
                    Access access = new Access(user, cinema);
                    accessRepositories.save(access);
                }
                user.setRole(adminRole);
                userRepositories.save(user);

            }catch (MyException e){
                throw new MyException("Something went wrong while adding access to all cinemas.");
            }
        }
        else {
            if (accessCreateAdminRequestDto.getCinemaId() == 0){
                throw new MyException("First you need to specify the cinema.");
            }
            Cinema cinema = cinemaRepositories.getByCinemaId(accessCreateAdminRequestDto.getCinemaId());
            if (cinema == null) {
                throw new MyException("A cinema with the given id does not exist!");
            }

            Role cinemaManagerRole = roleRepositories.findByName("Cinema manager");

            Access access = new Access(user, cinema);
            accessRepositories.save(access);

            user.setRole(cinemaManagerRole);
            userRepositories.save(user);
        }
    }

    @Override
    public void deleteAdmin(AccessDeleteAdminRequestDto accessDeleteAdminRequestDto) {
        if (accessDeleteAdminRequestDto.getToken() == null) {
            throw new MyException("You have to login first!.");
        }
        JwtClaims jwtClaims = JwtTokenProvider.decodeJwtToken(accessDeleteAdminRequestDto.getToken());
        User admin = userRepositories.findUserByEmail(jwtClaims.getEmail());
        if (admin == null) {
            throw new MyException("Admin doesn't exist!");
        }
        if (!admin.getRole().getName().equals("Administrator")) {
            throw new MyException("This user dont have admin permission to create admin");
        }

        User user = userRepositories.findUserByEmail(accessDeleteAdminRequestDto.getEmail());
        if (user == null) {
            throw new MyException("User with the provided email does not exist.");
        }
        if (!accessRepositories.existsByUserUserId(user.getUserId())) {
            throw new MyException("This user is not an admin.");
        }
        try{
            accessRepositories.deleteByUserUserId(user.getUserId());
        }catch (MyException e){
            throw new MyException("Failed to remove access for the given user. Try again alter.");
        }

        Role userRole = roleRepositories.findByName("User");
        user.setRole(userRole);
        userRepositories.save(user);

    }

    public void addAdminToNewCinema(Cinema cinema){
        try {
            Role adminRole = roleRepositories.findByName("Administrator");
            List<User> allAdmins = userRepositories.findByRoleRoleId(adminRole.getRoleId());
            for (User user : allAdmins) {
                Access access = new Access(user, cinema);
                accessRepositories.save(access);
            }
        }catch (MyException e){
            throw new MyException("Something went wrong with adding permission for admins to new cinema.");
        }
    }

}
