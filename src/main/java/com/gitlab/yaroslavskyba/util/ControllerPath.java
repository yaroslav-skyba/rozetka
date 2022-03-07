package com.gitlab.yaroslavskyba.util;

public class ControllerPath {
    public static final String ROOT = "api/v1";
    public static final String UUID = "{uuid}";
    public static final String LOGINS = "logins";
    public static final String REGISTRATIONS = "registrations";
    public static final String USERS = ROOT + "/users";
    public static final String ORDERS = ROOT + "/orders";
    public static final String ORDER_ITEMS = UUID + "/items";

    private ControllerPath() {}
}
