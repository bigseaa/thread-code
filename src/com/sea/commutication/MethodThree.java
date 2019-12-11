package com.sea.commutication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock实现线程通信
 */
public class MethodThree {
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
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(() -> {
            try {
                lock.lock();
                for(String str : letterStringArray) {
                    System.out.print(str);
                    condition2.signal();
                    condition1.await();
                }
                condition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                lock.lock();
                for(String str : numStringArray) {
                    System.out.print(str);
                    condition1.signal();
                    condition2.await();
                }
                condition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}
