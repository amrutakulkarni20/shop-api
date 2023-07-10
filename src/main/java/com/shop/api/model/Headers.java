package com.shop.api.model;

public enum Headers {

    API_KEY("X-API-KEY"),
    AUTHORIZATION("Authorization");

    private final String value;

    Headers(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
