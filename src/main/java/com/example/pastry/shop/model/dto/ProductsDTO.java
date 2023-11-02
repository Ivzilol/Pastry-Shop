package com.example.pastry.shop.model.dto;


import com.example.pastry.shop.model.entity.Shops;
import com.example.pastry.shop.model.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class ProductsDTO {

    private Long id;

    private String categories;

    private String description;

    private String imageUrl;

    private Integer likes;

    private String name;

    private Double price;

    private Shops shops;

    private Set<UsersLikesDTO> userLikes;

    public ProductsDTO() {
    }


}
