package com.example.pastry.shop.service;

import com.example.pastry.shop.model.dto.UserDTO;
import com.example.pastry.shop.model.dto.UserRegistrationDTO;
import com.example.pastry.shop.model.entity.Authority;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.AuthorityRepository;
import com.example.pastry.shop.repository.UsersRepository;
import com.example.pastry.shop.util.CustomPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;

    private CustomPasswordEncoder customPasswordEncoder;

    private AuthorityRepository authorityRepository;

    public UserService(UsersRepository usersRepository, CustomPasswordEncoder customPasswordEncoder, AuthorityRepository authorityRepository) {
        this.usersRepository = usersRepository;
        this.customPasswordEncoder = customPasswordEncoder;
        this.authorityRepository = authorityRepository;
    }

    public Optional<Users> findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public UserDTO findUserByEmail(String email) {
        Users user = usersRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return null;
        }
        return this.mapUserDTO(user);
    }

    private UserDTO mapUserDTO(Users user) {
        return new UserDTO()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setEmail(user.getEmail());
    }

    public void createUser(UserRegistrationDTO userRegistrationDTO) {
        Users newUser = new Users();
        newUser.setUsername(userRegistrationDTO.getUsername());
        newUser.setFirstName(userRegistrationDTO.getFirstName());
        newUser.setLastName(userRegistrationDTO.getLastName());
        newUser.setAddress(userRegistrationDTO.getAddress());
        newUser.setEmail(userRegistrationDTO.getEmail());
        String encodedPassword = customPasswordEncoder
                .getPasswordEncoder().encode(userRegistrationDTO.getPassword());
        newUser.setPassword(encodedPassword);
        usersRepository.save(newUser);
        Authority authority = new Authority();
        authority.setAuthority("user");
        authority.setUsers(newUser);
        authorityRepository.save(authority);
    }
}
