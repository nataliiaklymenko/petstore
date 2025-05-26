package com.nataliia.klymenko.petstore.constants;

public class Endpoint {
    public static final String V2 = "/v2";
    public static final String STORE = "/store";
    public static final String ORDER = STORE + "/order";
    public static final String INVENTORY = STORE + "/inventory";
    public static final String BY_ID = ORDER + "/{orderId}";

}
