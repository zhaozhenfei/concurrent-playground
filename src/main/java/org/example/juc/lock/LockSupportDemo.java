package org.example.juc.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by zhenfei7 on 2024/5/4.
 */
public class LockSupportDemo {
    public static void main(String[] args) {

        LockSupport.park();
    }
}
