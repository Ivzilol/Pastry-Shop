package com.example.pastry.shop.model.dto;

    public class ShopsDTO {

    private Long id;

    private Integer number;

    private String name;

    private String status;

    private String town;

    private String address;

    public ShopsDTO(Long id, Integer number, String name, String status, String town, String address) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.status = status;
        this.town = town;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
