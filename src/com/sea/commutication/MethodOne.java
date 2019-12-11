package com.sea.commutication;

/**
 * 使用一个成员变量与自旋锁实现线程通信，其中，成员变量需加上volatile关键字
 */
public class MethodOne {
    enum ThreadRunFlag {T1, T2}

    private volatile static ThreadRunFlag threadRunFlag = ThreadRunFlag.T1;

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
        new Thread(() -> {
           for(String str : letterStringArray) {
               while(threadRunFlag != ThreadRunFlag.T1) {}
               System.out.print(str);
               threadRunFlag = ThreadRunFlag.T2;
           }
        }, "t1").start();

        new Thread(() -> {
            for(String str : numStringArray) {
                while(threadRunFlag != ThreadRunFlag.T2) {}
                System.out.print(str);
                threadRunFlag = ThreadRunFlag.T1;
            }
        }, "t2").start();
    }
}
