package com.viii28stw.pensiltikfrontend.util;

public class BasicAuth {
    private static final String BASIC_AUTH_USER;
    private static final String BASIC_AUTH_PASSWORD;

    static {
        BASIC_AUTH_USER = "Trim";
        BASIC_AUTH_PASSWORD = "Tab";
    }

    private BasicAuth() {
        throw new IllegalStateException("Utility class");
    }

    public static String getUser() {
        return BASIC_AUTH_USER;
    }
    public static String getPassword() {
        return BASIC_AUTH_PASSWORD;
    }
}