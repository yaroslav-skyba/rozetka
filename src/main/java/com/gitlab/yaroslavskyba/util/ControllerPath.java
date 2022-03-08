package com.gitlab.yaroslavskyba.util;

public class ControllerPath {
    public static final String LOGINS = "logins";
    public static final String REGISTRATIONS = "registrations";
    public static final String ROOT = "api/v1";
    public static final String USERS = ROOT + "/users";
    public static final String ORDERS = ROOT + "/orders";
    public static final String PRODUCTS = ROOT + "/products";
    public static final String UUID = "{uuid}";
    public static final String ORDER_ITEMS = UUID + "/items";
    public static final String UUID_PRODUCT = "{uuidProduct}";
    public static final String REVIEWS = UUID_PRODUCT + "/reviews";
    public static final String PRODUCT_IMAGE = UUID_PRODUCT + "/image";
    public static final String REVIEW = UUID_PRODUCT + "/reviews/" + UUID;

    private ControllerPath() {}
}
