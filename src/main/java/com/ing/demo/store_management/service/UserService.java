package com.ing.demo.store_management.service;

import com.ing.demo.store_management.model.authentication.StoreUser;
import org.springframework.security.core.Authentication;

public interface UserService {
    void registerUser(StoreUser user);

    Authentication verifyUser(String email, String password);

    StoreUser getAuthenticatedUser();
}
