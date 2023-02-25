package com.example.pastry.shop.util;

import com.example.pastry.shop.model.entity.Users;

public class AuthorityUtil {

    public static Boolean hasRole(String role, Users user) {
        return user.getAuthorities()
                .stream()
                .filter(auth -> auth.getAuthority().equals(role))
                .count() > 0;
    }
}
