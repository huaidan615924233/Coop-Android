package com.coop.android.activity;

/**
 * Created by MR-Z on 2019/3/15.
 */
public class Test1 {
    public static void main(String[] args) {
//        staticFunction();
        System.out.println(b);
    }

    public static void staticFunction() {
        System.out.println("4");
    }

//    static Test1 test1 = new Test1();
    int a = 110;
    static int b = 112;

    static {
        System.out.println("1");
    }

    static {
        System.out.println("2");
    }

    Test1() {
        System.out.println("3");
        System.out.println("a= " + a + ",b= " + b);
    }
}
