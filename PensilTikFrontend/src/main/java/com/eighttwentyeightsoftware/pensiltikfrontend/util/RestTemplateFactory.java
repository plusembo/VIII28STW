package com.eighttwentyeightsoftware.pensiltikfrontend.util;

import org.springframework.web.client.RestTemplate;

/**
 * @author Plamedi L. Lusembo
 */

public class RestTemplateFactory {

    private static final RestTemplate REST_TEMPLATE;

    static {
        REST_TEMPLATE = new RestTemplate();
    }

    private RestTemplateFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static RestTemplate getRestTemplate() {
        return REST_TEMPLATE;
    }

}
