package kgurin.nsu;

public class Parent {
    public static void main(String[] args) {
        Child child = new Child(10);
        Thread thread = new Thread(child);
        thread.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello from Parent Counter: " + (i + 1));
        }
    }
}
