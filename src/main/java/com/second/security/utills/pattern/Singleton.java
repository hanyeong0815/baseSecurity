package com.second.security.utills.pattern;

public class Singleton {

    static {
        System.out.println("singleton 이제 호출됨");
    }
    private Singleton() {int num = 1;}

    public static Singleton getInstance() {
        return LazyHolder.instance;
    }

    private static class LazyHolder {

        static {
            System.out.println("내부클래스인 LazyHolder 클래스가 이제 호출됨");
        }

        private static final Singleton instance = new Singleton();
    }

    public int getNum(int num) {
        return num;
    }
}
