package ru.nsu.kgurin;

public class Parent {
    public static void main(String[] args) throws InterruptedException {
        Child child = new Child();
        Thread thread = new Thread(child);
        thread.start();
        Thread.sleep(2000);
        System.out.println("Interrupting...");
        thread.interrupt();
        System.out.println("Interrupted");
    }
}
