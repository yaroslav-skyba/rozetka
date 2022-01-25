package com.gitlab.yaroslavskyba;

public class MediaType {
    public static final String ROZETKA = "application/vnd.rozetka";
    public static final String ORDER = ROZETKA + ".order+json";
    public static final String ORDER_ITEM_LIST = ROZETKA + ".orderItemList+json";
    public static final String PRODUCT_LIST = ROZETKA + ".productList+json";
    public static final String PRODUCT = ROZETKA + ".product+json";
    public static final String REVIEW_LIST = ROZETKA + ".reviewList+json";
    public static final String REVIEW = ROZETKA + ".review+json";
    public static final String ROLE = ROZETKA + ".role+json";
    public static final String ROLE_LIST = ROZETKA + ".roleList+json";
    public static final String USER = ROZETKA + ".user+json";
    public static final String USER_LIST = ROZETKA + ".userList+json";
    public static final String AUTH_REQUEST = ROZETKA + ".authRequest+json";

    private MediaType() {}
}
