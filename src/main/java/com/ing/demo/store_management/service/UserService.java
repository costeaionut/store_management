package com.ing.demo.store_management.service;

import com.ing.demo.store_management.model.authentication.StoreUser;

public interface UserService {
    boolean registerUser(StoreUser user);
    boolean verifyUser(StoreUser user);
}
