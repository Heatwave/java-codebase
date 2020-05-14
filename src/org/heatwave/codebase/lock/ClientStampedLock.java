package org.heatwave.codebase.lock;

import org.junit.Test;

import java.util.concurrent.locks.StampedLock;

public class ClientStampedLock {
    private static final int COUNT = 10000;
    private int number = 0;

    private final StampedLock lock = new StampedLock();

    private void read() {
        long stamp = lock.tryOptimisticRead();
        int readNumber = number;
        System.out.println("optimistic read number=" + readNumber);
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            System.out.println("optimistic read number is wrong, use read lock number=" + number);
            lock.unlockRead(stamp);
        }
    }

    private void write(int change) {
        long stamp = lock.writeLock();
        number += change;
        System.out.println("write " + number);
        lock.unlockWrite(stamp);
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
