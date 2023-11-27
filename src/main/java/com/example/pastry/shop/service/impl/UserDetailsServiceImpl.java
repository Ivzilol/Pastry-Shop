package com.example.pastry.shop.service.impl;

import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UsersRepository usersRepository;

    private static final String INVALID_CREDENTIAL_ERROR = "Invalid Credential";

    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> userOpt = usersRepository.findByUsername(username);
        return userOpt.orElseThrow(() -> new UsernameNotFoundException(INVALID_CREDENTIAL_ERROR));
    }
}
