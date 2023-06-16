package com.example.pastry.shop.service;

import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.model.enums.ShopStatusEnum;
import com.example.pastry.shop.repository.ShopsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShopsService {


    private final ShopsRepository shopsRepository;

    public ShopsService(ShopsRepository shopsRepository) {
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

//    public Set<Shops> findByUser(Users user) {
//        //load shops if are you moderator
//        boolean isAdmin = user.getAuthorities()
//                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
//        if (isAdmin) {
//            return shopsRepository.findByAdmin(user);
//        } else {
//            // load shops if are you user
//            return shopsRepository.findByUsers(user);
//        }
//
//    }

    public Optional<Shops> findById(Long shopId) {
        return shopsRepository.findById(shopId);
    }

    public Shops saveShop(Shops shop) {
        return shopsRepository.save(shop);
    }

    public List<Shops> findAll() {
        return this.shopsRepository.findAll();
    }

    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }

    public void deleteShop(Long shopId, Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            this.shopsRepository.deleteById(shopId);
        }
    }
}
