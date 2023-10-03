package ru.nsu.kgurin;

public class Child implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Hello!");
        }
    }
}
