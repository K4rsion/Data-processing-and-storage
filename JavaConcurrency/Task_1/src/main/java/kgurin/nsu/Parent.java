package kgurin.nsu;

public class Parent {
    static final int numberOfStringsToPrint = 10;
    public static void main(String[] args) {
        Child child = new Child();
        Thread thread = new Thread(child);
        thread.start();
        for (int i = 0; i < numberOfStringsToPrint; i++) {
            System.out.println("Hello from parent. Counter: " + (i + 1));
        }
    }
}
