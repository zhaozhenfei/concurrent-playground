package org.example.geekbang.worker.thread;

import java.util.Arrays;

/**
 * Created by zhenfei7 on 2024/5/3.
 */
//流水线
public class PackageChannel {
    private final static int MAX_PACKAGE_NUM = 100;

    private final Package[] packageQueue;
    private final Worker[] workerPool;
    private int head;
    private int tail;
    private int count;

    public PackageChannel(int workers) {
        this.packageQueue = new Package[MAX_PACKAGE_NUM];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
        this.workerPool = new Worker[workers];
        this.init();
    }

    private void init() {
        for (int i = 0; i < workerPool.length; i++) {
            workerPool[i] = new Worker("Worker-" + i, this);
        }
    }

    /**
     * push switch to start all of worker to work
     */
    public void startWorker() {
        Arrays.asList(workerPool).forEach(Worker::start);
    }

    public synchronized void put(Package packagereq) {
        while (count >= packageQueue.length) {
            try {
                System.out.println(Thread.currentThread().getName() + "come in");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.packageQueue[tail] = packagereq;
        this.tail = (tail + 1) % packageQueue.length;
        this.count++;
        this.notifyAll();
    }

    public synchronized Package take() {
        while (count <= 0) {
            try {
                System.out.println(Thread.currentThread().getName() + "  wait start...");
                this.wait();
                System.out.println(Thread.currentThread().getName() + "  wait end...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Package request = this.packageQueue[head];
        this.head = (this.head + 1) % this.packageQueue.length;

        System.out.println(Thread.currentThread().getName() + "  exec take start");
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.count--;
        this.notifyAll();
        System.out.println(Thread.currentThread().getName() + "  exec take end");
        return request;
    }

}