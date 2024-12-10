package com.ing.demo.store_management.service.impl;

import com.ing.demo.store_management.model.authentication.Role;
import com.ing.demo.store_management.model.authentication.StoreUser;
import com.ing.demo.store_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class StoreUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public StoreUserDetailsService(@Autowired UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        StoreUser storeUser =
                repository
                        .findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        return new User(storeUser.getEmail(), storeUser.getPassword(), getUserAuthorities(storeUser));
    }

    private Collection<SimpleGrantedAuthority> getUserAuthorities(StoreUser user) {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }
}
