package org.example.juc.lock;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhenfei7 on 2024/5/4.
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (LockSupportDemo.class) {
                System.out.println(Thread.currentThread().getName() + "come in");
            }
        }, "thread-A").start();

        new Thread(() -> {
            synchronized (LockSupportDemo.class) {
                System.out.println(Thread.currentThread().getName() + "come in");
                try {
                    Thread.sleep(200 * 1000l);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "thread-B").start();
    }

    public void test() {
        Thread thread = new Thread();

        LockSupport.park();
        LockSupport.park(new Object());

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }


}
