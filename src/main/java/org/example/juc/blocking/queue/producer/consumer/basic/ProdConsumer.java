package org.example.juc.blocking.queue.producer.consumer.basic;

/**
 * Created by zhenfei7 on 2024/6/13.
 */

/**
 * 题目：一个初始值为零的变量，两个线程对其交替操作，一个加1，一个减1，来5轮
 */
class ShareData {
    private int num = 0;

    public synchronized void increment() throws InterruptedException {
        //1.判断
        System.out.println(Thread.currentThread().getName() + ":" + "increment come in...");
        while (num > 0) {
            this.wait();
            System.out.println(Thread.currentThread().getName() + ":" + "increment sleeping...");
            Thread.sleep(1 * 1000l);
            System.out.println(Thread.currentThread().getName() + ":" + "increment sleeping over...");
        }

        //2.干活
        num++;
        System.out.println(Thread.currentThread().getName() + ":" + num);
        //3.通知
        this.notifyAll();
        System.out.println(Thread.currentThread().getName() + ":" + "increment come out...");
    }

    public synchronized void decrement() throws InterruptedException {
        //
        while (num == 0) {
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + ":" + num);
        this.notifyAll();
    }
}

public class ProdConsumer {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread-A").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread-B").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread-C").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Thread-D").start();
    }

}
