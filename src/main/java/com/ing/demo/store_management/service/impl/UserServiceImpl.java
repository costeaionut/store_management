package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.exception.auth.RegistrationFailedException;
import com.ing.demo.store_management.exception.auth.UserAlreadyExistsException;
import com.ing.demo.store_management.model.authentication.StoreUser;
import com.ing.demo.store_management.repository.UserRepository;
import com.ing.demo.store_management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final PasswordEncoder encoder;
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(PasswordEncoder encoder, UserRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    @Override
    public boolean registerUser(StoreUser user) {
        try {
            if (user == null) {
                throw new IllegalArgumentException("Invalid user details.");
            }

            if (repository.findByEmail(user.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("Email is already taken.");
            }

            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);

            return true;
        } catch (UserAlreadyExistsException e) {
            LOGGER.error("User registration failed {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("User registration failed due to unexpected error {}", e.getMessage());
            throw new RegistrationFailedException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public boolean verifyUser(StoreUser user) {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
