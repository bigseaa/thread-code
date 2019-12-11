package com.sea.commutication;

import java.util.concurrent.locks.LockSupport;

/**
 * 使用LockSupport提供的方法实现线程通信
 */
public class MethodFour {
    private static Thread t1 = null;
    private static Thread t2 = null;

    public static void main(String[] args) {
        String[] letterStringArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"
                , "K", "L", "M", "N","O", "P", "Q", "R", "S", "T"
                , "U", "V", "W", "X", "Y", "Z"};
        String[] numStringArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
                , "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
                , "21", "22", "23", "24", "25", "26"};
        method(letterStringArray, numStringArray);
    }

    private static void method(String[] letterStringArray, String[] numStringArray) {
        t1 = new Thread(() -> {
            for(String str : letterStringArray) {
                System.out.print(str);
                LockSupport.unpark(t2);  // 叫醒t2
                LockSupport.park();  // 阻塞t1
            }
        }, "t1");

        t2 = new Thread(() -> {
            for(String str : numStringArray) {
                LockSupport.park(); // 阻塞t2
                System.out.print(str);
                LockSupport.unpark(t1); // 叫醒t1
            }
        },"t2");
        t1.start();
        t2.start();
    }
}
