package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.exception.authentication.RegistrationFailedException;
import com.ing.demo.store_management.exception.authentication.UserAlreadyExistsException;
import com.ing.demo.store_management.model.authentication.Role;
import com.ing.demo.store_management.model.authentication.StoreUser;
import com.ing.demo.store_management.repository.UserRepository;
import com.ing.demo.store_management.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTests {

    @Mock
    private UserRepository repositoryMock;

    @Mock
    private PasswordEncoder encoderMock;

    private StoreUser validUser;
    private UserService userService;

    @BeforeEach
    public void before() {
        encoderMock = Mockito.mock(PasswordEncoder.class);
        repositoryMock = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(encoderMock, repositoryMock);
        validUser = new StoreUser("Name", "Surname", "email@email.com", "password123", Role.USER);
    }

    @Test
    public void testRegisterUser_Success() {
        assertDoesNotThrow(() -> userService.registerUser(validUser));
        verify(repositoryMock, times(1)).save(validUser);
    }

    @Test
    public void testRegisterUser_UsernameAlreadyExists() {
        when(repositoryMock.findByEmail(validUser.getEmail())).thenReturn(Optional.ofNullable(validUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(validUser));
        verify(repositoryMock, times(0)).save(validUser);
    }

    @Test
    public void testRegisterUser_RegistrationFailed() {
        assertThrows(RegistrationFailedException.class, () -> userService.registerUser(null));
    }
}
