package org.example.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhenfei7 on 2024/4/29.
 */
public class AtomicTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
//        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.getAndSet(2333));
        System.out.println(atomicInteger.get());
    }
}
