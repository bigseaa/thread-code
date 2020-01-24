package com.sea.work.foo;

/**
 * 以wait notify实现等待/通知按序打印one、two、three
 */
public class MethodThree {
    private final Object lock = new Object();
    private enum flagEnum  {ONE, TWO, THREE};
    private flagEnum flag = flagEnum.ONE;

    public static void main(String[] args) {
        new MethodThree().test();
    }

    private void test() {
        new Thread(() -> {
            synchronized (lock) {
                while(flag != flagEnum.THREE) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("three");
                lock.notifyAll();
            }
        }).start();

        new Thread(() -> {
            synchronized (lock) {
                while(flag != flagEnum.TWO) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("two");
                flag = flagEnum.THREE;
                lock.notifyAll();
            }
        }).start();

        new Thread(() -> {
            synchronized (lock) {
                while(flag != flagEnum.ONE) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("one");
                flag = flagEnum.TWO;
                lock.notifyAll();
            }
        }).start();
    }
}
