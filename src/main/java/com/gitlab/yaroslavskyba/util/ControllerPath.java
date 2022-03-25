package com.gitlab.yaroslavskyba.util;

public class ControllerPath {
    private static final String ROOT = "/api/v1/";
    private static final String UUID = "/{uuid}";

    public static final String LOGINS = ROOT + "logins";
    public static final String REGISTRATIONS = ROOT + "registrations";
    public static final String REFRESH_JWT = ROOT + "jwts";
    public static final String ORDERS = ROOT + "orders";
    public static final String PRODUCTS = ROOT + "products";
    public static final String PRODUCT = PRODUCTS + UUID;
    public static final String PRODUCT_IMG = PRODUCT + "/img";
    public static final String REVIEWS = PRODUCTS + "/{uuidProduct}/reviews";
    public static final String REVIEW = REVIEWS + UUID;
    public static final String ROLES = ROOT + "roles";
    public static final String ROLE = ROLES + UUID;
    public static final String USERS = ROOT + "users";
    public static final String USER = USERS + UUID;

    private ControllerPath() {}
}
