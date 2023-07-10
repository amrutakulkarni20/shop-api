package com.shop.api.exception;

public enum ErrorCode {

    INVALID_REQUEST("INVALID_REQUEST");

    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
