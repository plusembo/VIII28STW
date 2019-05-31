package com.viii28stw.pensiltikfrontend.util;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.ResourceBundle;

@NoArgsConstructor
@Getter
public class I18nFactory {

    private Locale locale;
    private ResourceBundle resourceBundle;
    private final static String RESOURCE_BUNDLE_BASE_NAME = "strings";

    private static I18nFactory uniqueInstance;

    public static synchronized I18nFactory getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new I18nFactory();
        }
        return uniqueInstance;
    }

    public void setLocale(String language, String country) {
        locale = new Locale(language, country);
        setResourceBundle();
    }

    private void setResourceBundle() {
        resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME, locale);
    }
}
