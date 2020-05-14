package org.heatwave.codebase.lock;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ClientReentrantLock {
    private static final int COUNT = 10000;
    private int number = 0;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private boolean writeComplete = false;

    private void read() {
        try {
            lock.lock();
            while (!writeComplete) {
                condition.await();
            }
            System.out.println("number = " + number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void write(int change) {
        try {
            lock.lock();
            number += change;
            System.out.println("write " + number);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
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
            lock.lock();
            condition.signal();
//            condition.signalAll();
            lock.unlock();
        }).start();

        new Thread(() -> {
            for (int i = 0; i < COUNT; i++) {
                read();
            }
        }).start();

        Thread.sleep(1000);
    }
}
