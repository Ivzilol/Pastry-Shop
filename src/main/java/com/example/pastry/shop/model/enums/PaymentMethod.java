package com.example.pastry.shop.model.enums;

public enum PaymentMethod {
    IN_CASH("InCash"),
    withCard("withCard");

    private final String method;

    PaymentMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
