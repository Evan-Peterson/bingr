package com.bingr.app.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator{

    /**
     * Tests for a valid email format.
     * @param str The email to be tested
     * @return true if valid, else false
     */
    public static boolean validate(String str) {
        Pattern p = Pattern.compile("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}");
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
