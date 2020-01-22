package com.sea.work.foo;

/**
 * 以自旋锁的思想实现one two three顺序打印
 */
public class MethodTwo {
    enum FlagEnum {ONE, TWO, THREE, FOUR};

    private volatile FlagEnum printFlag = FlagEnum.ONE;
    private boolean oneFinish = false;
    private boolean twoFinish = false;
    private boolean threeFinish = false;

    public static void main(String[] args) {
        new MethodTwo().test();
    }

    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!threeFinish) {
                    if(printFlag == FlagEnum.THREE) {
                        System.out.println("three");
                        threeFinish = true;
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while(!twoFinish) {
                if(printFlag == FlagEnum.TWO) {
                    System.out.println("two");
                    twoFinish = true;
                    printFlag = FlagEnum.THREE;
                }
            }
        }).start();

        new Thread(() -> {
            while(!oneFinish) {
                if(printFlag == FlagEnum.ONE) {
                    System.out.println("one");
                    oneFinish = true;
                    printFlag = FlagEnum.TWO;
                }
            }
        }).start();
    }
}
