package org.example.juc.blocking.queue.producer.consumer.queue;

/**
 * Created by zhenfei7 on 2024/6/13.
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 题目：一个初始值为零的变量，两个线程对其交替操作，一个加1，一个减1，来5轮
 */

class MyResource {
    BlockingQueue<Integer> blockingQueue = null;
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public MyResource(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void produce() throws InterruptedException {
        while (flag) {
            Integer data = atomicInteger.incrementAndGet();
            boolean retValue = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "大老板叫停了，flag=" + flag + ",生产结束");
    }

    public void consume() throws InterruptedException {
        while (flag) {
            Integer data = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (data != null) {
                System.out.println(Thread.currentThread().getName() + "消费数据" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "消费数据失败");
            }
        }
    }

    public void stop() {
        flag = false;
    }
}

public class ProdConsumer {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new LinkedBlockingQueue<>(10));

        new Thread(() -> {
            try {
                myResource.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Thread-Producer").start();

        new Thread(() -> {
            try {
                myResource.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "Thread-Consumer").start();

        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        myResource.stop();
    }
}
