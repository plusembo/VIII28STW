package com.viii28stw.pensiltikfrontend.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Plamedi L. Lusembo
 */
public class EmailValidator {
    public static boolean isValidEmail(String email) {
        Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(email);
        return m.find();
    }
}