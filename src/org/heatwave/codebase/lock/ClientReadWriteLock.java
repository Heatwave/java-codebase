package org.heatwave.codebase.lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ClientReadWriteLock {
    private static final int COUNT = 10000;
    private int number = 0;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private void read() {
        try {
            readLock.lock();
            System.out.println("number = " + number);
        } finally {
            readLock.unlock();
        }
    }

    private void write(int change) {
        try {
            writeLock.lock();
            number += change;
            System.out.println("write " + number);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    @Test
    public void test() throws InterruptedException {
        new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                write(1);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                read();
            }
        }).start();

        Thread.sleep(2000);
    }
}
