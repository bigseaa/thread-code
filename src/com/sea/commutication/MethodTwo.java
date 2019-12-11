package com.sea.commutication;

/**
 * 使用synchronized与notify、wait方法实现线程通信
 */
public class MethodTwo {
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
        final Object obj = new Object();

        new Thread(() -> {
            synchronized (obj) {
                for(String str : letterStringArray) {
                    System.out.print(str);
                    try {
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 这里使用一个notify是因为在for循环的最后一次后会有一个线程wait
                obj.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (obj) {
                for(String str : numStringArray) {
                    System.out.print(str);
                    try {
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 这里使用一个notify是因为在for循环的最后一次后会有一个线程wait
                obj.notify();
            }
        }, "t2").start();
    }
}
