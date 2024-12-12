package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.exception.authentication.InvalidCredentialsException;
import com.ing.demo.store_management.exception.authentication.RegistrationFailedException;
import com.ing.demo.store_management.exception.authentication.UserAlreadyExistsException;
import com.ing.demo.store_management.model.authentication.StoreUser;
import com.ing.demo.store_management.repository.UserRepository;
import com.ing.demo.store_management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(PasswordEncoder encoder, UserRepository repository, AuthenticationManager authenticationManager) {
        this.encoder = encoder;
        this.repository = repository;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Register a new user in the system.
     *
     * @param user user details
     * @throws IllegalArgumentException    if the user details are null.
     * @throws UserAlreadyExistsException  if the user's email is already taken.
     * @throws RegistrationFailedException if registration fails due to an unexpected error.
     */
    @Override
    public void registerUser(@Validated StoreUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid user details");
        }

        try {
            if (repository.findByEmail(user.getEmail()).isPresent()) {
                throw new UserAlreadyExistsException("Email is already taken");
            }

            user.setPassword(encoder.encode(user.getPassword()));
            repository.save(user);
        } catch (UserAlreadyExistsException e) {
            LOGGER.error("User registration failed {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("User registration failed due to unexpected error {}", e.getMessage());
            throw new RegistrationFailedException(e.getMessage(), e.getCause());
        }
    }

    /**
     * Verifies the user's credentials for authentication.
     *
     * @param email    user email
     * @param password user password
     * @return the authenticated user details
     * @throws IllegalArgumentException    if the email or password is invalid.
     * @throws InvalidCredentialsException if authentication fails.
     */
    @Override
    public Authentication verifyUser(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is missing or invalid");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is missing or invalid");
        }

        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
            return authenticationManager.authenticate(authentication);
        } catch (AuthenticationException e) {
            //Not the best practice since email is a sensitive data. Should be changed for ID.
            LOGGER.error("User authentication failed for email {} {}", email, e.getMessage());
            throw new InvalidCredentialsException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            LOGGER.error("User authentication failed due to unexpected error {}", e.getMessage());
            throw new InvalidCredentialsException(e.getMessage(), e.getCause());
        }
    }

    /**
     * Fetch the authenticated user based on the current authentication context
     *
     * @return the authenticated {@link StoreUser} uer
     */
    @Override
    public StoreUser getAuthenticatedUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<StoreUser> currentUser = repository.findByEmail(email);

        if (currentUser.isEmpty()) {
            //Redirect to log out...
            throw new IllegalStateException("Invalid authentication context.");
        }

        return currentUser.get();
    }
}
