package ru.nsu.kgurin;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
        List<String> stringList = new CopyOnWriteArrayList<>();
        stringList.add("Hello, I am Thread ");

        stringList.add("1");
        MyThread thread1 = new MyThread(stringList);
        thread1.start();

        stringList.add("2");
        MyThread thread2 = new MyThread(stringList);
        thread2.start();

        stringList.add("3");
        MyThread thread3 = new MyThread(stringList);
        thread3.start();
    }
}
