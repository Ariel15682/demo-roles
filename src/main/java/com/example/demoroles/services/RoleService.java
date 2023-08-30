package com.example.demoroles.services;


import com.example.demoroles.entities.Role;

public interface RoleService {
    Role findByName(String name);
}
