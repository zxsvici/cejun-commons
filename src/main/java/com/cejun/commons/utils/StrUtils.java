package com.cejun.commons.utils;

public class StrUtils {

    public static String strFromCamel(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        char start = str.charAt(0);
        if(start >= 'a' && start <= 'z') {
            start -= 32;
        }
        sb.append(start);
        for (int i = 1; i < length; i++) {
            char ch = str.charAt(i);
            if(ch >= 'A' && ch <= 'Z') {
                sb.append("_");
                ch += 32;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    public static String strToCamel(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < length - 1; i++) {
            char ch = str.charAt(i);
            if(ch == '_') {
                flag = true;
                continue;
            }
            if(flag && ch >= 'a' && ch <= 'z') {
                ch -= 32;
                flag = false;
            }
            sb.append(ch);
        }
        sb.append(str.charAt(length - 1));
        return sb.toString();
    }
}
