package com.example.cinemaressys.services.access;

import com.example.cinemaressys.dtos.access.AccessCreateAdminRequestDto;
import com.example.cinemaressys.dtos.access.AccessDeleteAdminRequestDto;

public interface AccessService {
    public void createAdmin(AccessCreateAdminRequestDto accessCreateAdminRequestDto);
    public void deleteAdmin(AccessDeleteAdminRequestDto accessDeleteAdminRequestDto);
}
