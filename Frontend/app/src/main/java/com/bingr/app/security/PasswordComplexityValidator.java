package com.bingr.app.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordComplexityValidator{

    public static final int MIN_PASS_LENGTH = 8;

    /**
     * Tests password form for required complexity. To be complex, the password must
     * contain a number, capital and lowercase letters, and a symbol.
     * @param str the password to test
     * @return true if complex, otherwise false
     */
    public static boolean validate(String str) {
        // if the string has an uppercase it will not be equal to an all lowercase version
        int count = 0;
        if(!str.equals(str.toLowerCase())) {
            count++;
        }
        // same logic applies
        if(!str.equals(str.toUpperCase())) {
            count++;
        }
        Pattern p = Pattern.compile(".*[!@#$%^&*_+~].*");
        Matcher m = p.matcher(str);
        if(m.matches()) {
            count++;
        }
        p = Pattern.compile(".*\\d.*");
        m = p.matcher(str);
        if(m.matches()) {
            count++;
        }

        return count >= 3;
    }
}
