package org.example.geekbang.worker.thread;

/**
 * Created by zhenfei7 on 2024/5/3.
 */
//包裹类
public class Package {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName()+" executed "+this);
    }
}
