package ru.nsu.kgurin;

public class Child implements Runnable {
    private int numberOfStringsToPrint;

    public Child(int n){
        numberOfStringsToPrint = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfStringsToPrint; i++) {
            System.out.println("Hello from child. Counter: " + (i + 1));
        }
    }
}
