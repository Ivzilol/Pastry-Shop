package com.example.pastry.shop.service.impl;

import com.example.pastry.shop.model.dto.ShopDTO;
import com.example.pastry.shop.model.dto.ShopsDTO;
import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import com.example.pastry.shop.model.enums.AuthorityEnum;
import com.example.pastry.shop.model.enums.ShopStatusEnum;
import com.example.pastry.shop.repository.ShopsRepository;
import com.example.pastry.shop.service.ShopsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShopsServiceImpl implements ShopsService {


    private final ShopsRepository shopsRepository;

    private static final String SHOP_NAME = "Sladcarnicata na Mama";

    private static final String SHOP_TOWN = "Sofia";

    private static final String SHOP_ADDRESS = "str. AlaBala";

    public ShopsServiceImpl(ShopsRepository shopsRepository) {
        this.shopsRepository = shopsRepository;
    }

    @Override
    public void createShop(Users user) {
        Shops shop = new Shops();
        shop.setName(SHOP_NAME);
        shop.setStatus(ShopStatusEnum.WORKING.getStatus());
        shop.setNumber(findNextShopToSubmit(user));
        shop.setTown(SHOP_TOWN);
        shop.setAddress(SHOP_ADDRESS);
        shop.setUsers(user);
        shopsRepository.save(shop);
    }

    private Integer findNextShopToSubmit(Users user) {
        Set<Shops> shopsByUser = shopsRepository.findByUsers(user);
        if (shopsByUser == null) {
            return 1;
        }
        return nextNumberToShop(shopsByUser);
    }

    @NotNull
    private static Integer nextNumberToShop(Set<Shops> shopsByUser) {
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

    @Override
    public Optional<ShopDTO> findById(Long shopId) {
        return shopsRepository.findShopById(shopId);
    }


    @Override
    public List<ShopsDTO> findAll() {
        return this.shopsRepository.findAllShops();
    }

    private static boolean isAdmin(Users user) {
        return user.getAuthorities()
                .stream().anyMatch(auth -> AuthorityEnum.admin.name().equals(auth.getAuthority()));
    }

    @Override
    public void deleteShop(Long shopId, Users user) {
        boolean isAdmin = isAdmin(user);
        if (isAdmin) {
            this.shopsRepository.deleteById(shopId);
        }
    }

    @Override
    public Shops saveShop(Shops shop, Users user) {
        if (user != null) {
            boolean isAdmin = isAdmin(user);
            if (isAdmin) {
                this.shopsRepository.save(shop);
                return shop;
            }
        } else {
            this.shopsRepository.save(shop);
            return shop;
        }
        return shop;
    }

    @Override
    public boolean updateShop(Shops shop, Long shopId, Users user) {
        Optional<Shops> currentShop = this.shopsRepository.findById(shopId);
        currentShop.get().setId(shop.getId());
        currentShop.get().setAddress(shop.getAddress());
        currentShop.get().setName(shop.getName());
        currentShop.get().setTown(shop.getTown());
        currentShop.get().setUsers(user);
        this.shopsRepository.save(currentShop.get());
        return true;
    }

    @Override
    public Shops findByName(String name) {
        return this.shopsRepository.findByName(name);
    }
}
