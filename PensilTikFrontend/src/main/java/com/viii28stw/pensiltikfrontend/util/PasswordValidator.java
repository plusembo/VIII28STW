package com.viii28stw.pensiltikfrontend.util;

/**
 * @author Plamedi L. Lusembo
 */
public class PasswordValidator {
    public static boolean isValidPassword(String password) {
        //Can not be null
        if(password == null) return false;
        //Maximum length of 8 characters and Maximum length of 8 characters
        if(password.length() < 8 || password.length() > 10){
            return false;
        }
        //Must have at least 2 letters and 2 numbers in the password
        int numbers = 0;
        int letters = 0;

        for(int i = 0; i < password.length(); i++){
            Character letra = password.charAt(i);
            try{
                Integer.valueOf(letra.toString());
                numbers++;
            }catch (Exception e) {
                letters++;
                continue;
            }
        }
        if(numbers < 2 || letters < 2){
            return false;
        }
        return true;
    }
}