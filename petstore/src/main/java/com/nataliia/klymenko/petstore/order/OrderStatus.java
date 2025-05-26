package com.nataliia.klymenko.petstore.order;

public enum OrderStatus {
    PLACED("placed");
    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
