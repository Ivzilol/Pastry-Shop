package com.example.pastry.shop.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String categories;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    private int likes;

    private int numberOrders;

    private String comments;

    @ManyToOne(optional = false)
    private Shops shops;

    @ManyToOne
    public Users admin;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Users> userLikes;

    public Products() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getNumberOrders() {
        return numberOrders;
    }

    public void setNumberOrders(int numberOrders) {
        this.numberOrders = numberOrders;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Shops getShops() {
        return shops;
    }

    public void setShops(Shops shops) {
        this.shops = shops;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users getAdmin() {
        return admin;
    }

    public void setAdmin(Users admin) {
        this.admin = admin;
    }

    public Set<Users> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Set<Users> userLikes) {
        this.userLikes = userLikes;
    }
}
