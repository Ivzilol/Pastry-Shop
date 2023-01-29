package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.repository.ShopsRepository;
import com.example.pastry.shop.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ShopsService {


    private final ShopsRepository shopsRepository;

    private UsersRepository usersRepository;

    public ShopsService(ShopsRepository shopsRepository) {
        this.shopsRepository = shopsRepository;
    }

    public Shops createShop(Users user) {
        Shops shop = new Shops();
        shop.setName("Sladcarnicata na Mama");
        shop.setTown("Sofia");
        shop.setAddress("str. AlaBala");
        shop.setUsers(user);

        return shopsRepository.save(shop);
    }

    public Set<Shops> findByUser(Users user) {
        return shopsRepository.findByUsers(user);
    }
}
