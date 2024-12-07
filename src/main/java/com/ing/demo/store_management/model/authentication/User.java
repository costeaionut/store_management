package com.ing.demo.store_management.model.authentication;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String surname;
    private String dateOfBirth;
    private Role role;
}
