package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.UsersRepository;
import com.example.pastry.shop.util.CustomPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private CustomPasswordEncoder passwordEncoder;

    private UsersRepository usersRepository;

    public UserDetailsServiceImpl(CustomPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> usersOpt = usersRepository.findByUsername(username);

        return usersOpt.orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
    }
}
