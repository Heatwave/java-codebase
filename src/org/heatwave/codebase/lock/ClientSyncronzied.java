package org.heatwave.codebase.lock;

import org.junit.Test;

public class ClientSyncronzied {
    private static final int COUNT = 10000;
    private int number = 0;

    private final Object lock = new Object();

    private boolean writeComplete = false;

    private void read() {
        synchronized (lock) {
            while (!writeComplete) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("number = " + number);
        }
    }

    private void write(int change) {
        synchronized (lock) {
            number += change;
            System.out.println("write " + number);
        }
    }

    @Test
    public void test() throws InterruptedException {
        new Thread(() -> {
            writeComplete = false;
            for (int i = 0; i < COUNT; i++) {
                write(1);
            }
            writeComplete = true;
            synchronized (lock) {
                lock.notify();
//                lock.notifyAll();
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                read();
            }
        }).start();

        Thread.sleep(1000);
    }
}
