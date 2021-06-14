package com.gitlab.yarunkan.controller.util;

public class MediaType {
    public static final String ROZETKA = "application/rozetka";
    public static final String ORDER = ROZETKA + ".order+json";
    public static final String PRODUCT_LIST = ROZETKA + ".productList+json";
    public static final String PRODUCT = ROZETKA + ".product+json";
    public static final String REVIEW_LIST = ROZETKA + ".reviewList+json";
    public static final String REVIEW = ROZETKA + ".review+json";

    private MediaType() {}
}
