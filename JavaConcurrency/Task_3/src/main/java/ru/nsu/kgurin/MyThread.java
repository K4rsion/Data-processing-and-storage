package ru.nsu.kgurin;

import java.util.ArrayList;
import java.util.List;

class MyThread extends Thread {
    private final List<String> stringList;

    public MyThread(List<String> stringList) {
        this.stringList = new ArrayList<>(stringList);
    }

    @Override
    public void run() {
        stringList.forEach(System.out::println);
//        System.out.println(stringList + Thread.currentThread().getName());
    }
}
