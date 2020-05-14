package org.heatwave.codebase.lock;

import org.junit.Test;

public class Client {
    private static final int COUNT = 100000;
    private int number = 0;

    private void read() {
        System.out.println("number = " + number);
    }

    private void write(int change) {
        number += change;
    }

    @Test
    public void test() throws InterruptedException {
        new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                write(1);
            }
            System.out.println("add " + COUNT + " times complete");
        }).start();

        new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                write(-1);
            }
            System.out.println("minus " + COUNT + " times complete");
        }).start();

        Thread.sleep(1000);
        read();
    }
}
