package ru.nsu.kgurin;

import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    static final int NUMBER_OF_STRING_TO_PRINT = 10;
    public static void main(String[] args) {
        final Object lock = new Object();
        AtomicBoolean flag = new AtomicBoolean(true);

        Thread parent = new Thread(() -> {
            synchronized (lock) {
                try {
                    for (int i = 0; i < NUMBER_OF_STRING_TO_PRINT/2; i++) {
                        while (!flag.get()) {
                            lock.wait();
                        }
                        System.out.println("Parent");
                        flag.set(false);
                        lock.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread child = new Thread(() -> {
            synchronized (lock) {
                try {
                    for (int i = 0; i < NUMBER_OF_STRING_TO_PRINT/2; i++) {
                        while (flag.get()) {
                            lock.wait();
                        }
                        System.out.println("Child");
                        flag.set(true);
                        lock.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        child.start();
        parent.start();
    }
}
