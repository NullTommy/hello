package com.MyUtils;

public class StringUtils {
    public static boolean isNull(String str) {
        return str == null;
    }

    public static int getLength(String str) {
        if (str == null) {
            return 0;
        } else {
            return str.length();
        }
    }

}
