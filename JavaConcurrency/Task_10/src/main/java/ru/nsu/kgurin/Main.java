package ru.nsu.kgurin;

public class Main {
    static final int NUMBER_OF_STRING_TO_PRINT = 10;

    public static void main(String[] args) {
        final Object lock = new Object();
        final boolean[] flag = {true};

        Thread child = new Thread(() -> {
            synchronized (lock) {
                try {
                    for (int i = 0; i < NUMBER_OF_STRING_TO_PRINT/2; i++) {
                        while (!flag[0]) {
                            lock.wait();
                        }
                        System.out.println("Child");
                        flag[0] = false;
                        lock.notify();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread parent = new Thread(() -> {
            synchronized (lock) {
                try {
                    for (int i = 0; i < NUMBER_OF_STRING_TO_PRINT/2; i++) {
                        while (flag[0]) {
                            lock.wait();
                        }
                        System.out.println("Parent");
                        flag[0] = true;
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
