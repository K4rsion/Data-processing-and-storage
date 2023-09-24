package ru.nsu.kgurin;

public class Parent{
    static final int NUMBER_OF_STRING_TO_PRINT = 10;
    public static void main(String[] args) throws InterruptedException {
        Child child = new Child(NUMBER_OF_STRING_TO_PRINT);
        Thread thread = new Thread(child);
        thread.start();
        thread.join();
        for (int i = 0; i < NUMBER_OF_STRING_TO_PRINT; i++) {
            System.out.println("Hello from Parent. Counter: " + (i+1));
        }
    }
}
