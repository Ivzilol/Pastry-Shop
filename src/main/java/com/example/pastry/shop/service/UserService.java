package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<Users> findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
