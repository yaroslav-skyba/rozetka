package com.gitlab.yaroslavskyba.rozetka.util;

public class MediaType {
    private static final String ROOT = "application/vnd.rozetka.";
    private static final String SUFFIX = "+json";

    public static final String ORDER_ITEM_LIST = ROOT + "orderItemList" + SUFFIX;
    public static final String PRODUCT_LIST = ROOT + "productList" + SUFFIX;
    public static final String PRODUCT = ROOT + "product" + SUFFIX;
    public static final String REVIEW_LIST = ROOT + "reviewList" + SUFFIX;
    public static final String REVIEW = ROOT + "review" + SUFFIX;
    public static final String ROLE = ROOT + "role" + SUFFIX;
    public static final String ROLE_LIST = ROOT + "roleList" + SUFFIX;
    public static final String USER = ROOT + "user" + SUFFIX;
    public static final String USER_LIST = ROOT + "userList" + SUFFIX;
    public static final String LOGIN = ROOT + "login" + SUFFIX;

    private MediaType() {}
}
