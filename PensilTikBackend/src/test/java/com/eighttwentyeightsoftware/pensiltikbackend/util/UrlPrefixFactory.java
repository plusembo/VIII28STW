package com.eighttwentyeightsoftware.pensiltikbackend.util;

public class UrlPrefixFactory {

    private static final String URL_PREFIX;

    static {
        URL_PREFIX = "http://localhost:9000/pensiltik";
    }

    public static String getUrlPrefix() {
        return URL_PREFIX;
    }
}
