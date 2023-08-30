package com.example.demoroles.services;

import com.example.demoroles.dto.UserDto;
import com.example.demoroles.entities.User;
import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
   

}
