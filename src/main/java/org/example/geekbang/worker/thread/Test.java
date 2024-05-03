package org.example.geekbang.worker.thread;

/**
 * Created by zhenfei7 on 2024/5/3.
 */
public class Test {
    public static void main(String[] args) {
        //新建8个工人
        final PackageChannel channel = new PackageChannel(8);
        //开始工作
        channel.startWorker();

        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //为流水线添加包裹
        for(int i=0; i<1; i++) {
            Package packagereq = new Package();
            packagereq.setAddress("test");
            packagereq.setName("test");
            channel.put(packagereq);
        }
    }
}