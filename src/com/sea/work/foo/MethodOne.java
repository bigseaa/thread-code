package com.sea.work.foo;

import java.util.concurrent.Semaphore;

/**
 * 以Semaphore控制三个线程顺序打印one two three
 */
public class MethodOne {
    private Semaphore semaphoreOne = new Semaphore(1);
    private Semaphore semaphoreTwo = new Semaphore(0);
    private Semaphore semaphoreThree = new Semaphore(0);

    public static void main(String[] args) {
        new MethodOne().test();
    }

    private void test() {
        new Thread(() -> {
            try {
                semaphoreThree.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("three");
            semaphoreOne.release();
        }, "thread-3").start();

        new Thread(() -> {
            try {
                semaphoreTwo.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("two");
            semaphoreThree.release();
        }, "thread-2").start();

        new Thread(() -> {
            try {
                semaphoreOne.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("one");
            semaphoreTwo.release();
        }, "thread-1").start();
    }
}
