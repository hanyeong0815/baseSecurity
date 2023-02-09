package com.second.security.common.validator;

public class UserValidator {
    public static final String username = "^[a-z0-9_\\-]{6,20}$";
    public static final String password = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[*.!@#$%^&(){}\\[\\]:;<>,?/~_+\\-=\\\\|]).{8,32}$";
    public static final String name = "^[a-zA-Zㄱ-힣][a-zA-Zㄱ-힣 ]{3,19}$";
}
