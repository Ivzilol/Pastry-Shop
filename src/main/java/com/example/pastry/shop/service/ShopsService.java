package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.ShopStatusEnum;
import com.example.pastry.shop.repository.ShopsRepository;
import com.example.pastry.shop.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ShopsService {


    private final ShopsRepository shopsRepository;

    public ShopsService(ShopsRepository shopsRepository, UsersRepository usersRepository) {
        this.shopsRepository = shopsRepository;
    }

    public Shops createShop(Users user) {
        Shops shop = new Shops();
        shop.setName("Sladcarnicata na Mama");
        shop.setStatus(ShopStatusEnum.WORKING.getStatus());
        shop.setNumber(findNextShopToSubmit(user));
        shop.setTown("Sofia");
        shop.setAddress("str. AlaBala");
        shop.setUsers(user);

        return shopsRepository.save(shop);
    }

    private Integer findNextShopToSubmit(Users user) {
        Set<Shops> shopsByUser = shopsRepository.findByUsers(user);
        if (shopsByUser == null) {
            return 1;
        }
        Optional<Integer> nextShopNumber = shopsByUser.stream()
                .sorted((s1, s2) -> {
                    if (s1.getNumber() == null)
                        return 1;
                    if (s2.getNumber() == null)
                        return 1;
                    return s2.getNumber().compareTo(s1.getNumber());
                })
                .map(shop -> {
                    if (shop.getNumber() == null) return 1;
                    return shop.getNumber() + 1;
                })
                .findFirst();
        return nextShopNumber.orElse(1);
    }

    public Optional<Shops> findById(Shops shop) {
        return shopsRepository.findById(shop.getId());
    }

    public Set<Shops> findByUser(Users user) {
        return shopsRepository.findByUsers(user);
    }

    public Optional<Shops> findById(Long shopId) {
        return shopsRepository.findById(shopId);
    }

    public Shops saveShop(Shops shop) {
        return shopsRepository.save(shop);
    }
}
