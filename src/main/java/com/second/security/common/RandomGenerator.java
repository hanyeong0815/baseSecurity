package com.second.security.common;

import java.util.Random;

public class RandomGenerator {
    static private final String NUM = "0123456789";
    static private final String NUM_OTN = "123456789";
    static private final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static private final String ALPHA_EXCEPT_IOS = "ABCDEFGHJKLMNPQRTUVWXYZ";

    static private String generate(String chars, int len) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    static public String NumAndAlpha(int len){
        return generate(NUM + ALPHA_EXCEPT_IOS, len);
    }

    static public String Num(int len){
        if (len > 0)
            return generate(NUM_OTN,1) + generate(NUM, len - 1);
        else
            return "";
    }

    static public String Alpha(int len){
        return generate(ALPHA, len);
    }
}
