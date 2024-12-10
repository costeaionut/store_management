package com.ing.demo.store_management.mappers;

import com.ing.demo.store_management.controller.dto.authentication.RegisterRequestDTO;
import com.ing.demo.store_management.model.authentication.StoreUser;

public class UserMapper {
    public static StoreUser toModel(RegisterRequestDTO dto) {
        StoreUser model = new StoreUser();

        model.setName(dto.getName());
        model.setSurname(dto.getSurname());
        model.setEmail(dto.getEmail());
        model.setPassword(dto.getPassword());
        model.setRole(dto.getRole());

        return model;
    }
}
