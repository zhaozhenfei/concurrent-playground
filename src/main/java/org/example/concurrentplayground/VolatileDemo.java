package org.example.concurrentplayground;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhenfei7 on 2024/4/10.
 */
public class VolatileDemo {

    static boolean flag = true;

//    static volatile boolean flag = true;

    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "------come in");
            while (flag){
                /**
                 * 这里应该是涉及到CPU计算时候的一个问题
                 * 这里如果没有任何执行语句，那么这个变量flag一直不会去主内存中重新获取值;
                 * 但只要有任何输出语句，或者其他语句应该也可以，就会发生 去主内存中重新获取flag变量的值。
                 */
//                System.out.println(System.currentTimeMillis()/1000);
            }
            System.out.println(Thread.currentThread().getName() + "------out");
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        flag = false;
    }
}
