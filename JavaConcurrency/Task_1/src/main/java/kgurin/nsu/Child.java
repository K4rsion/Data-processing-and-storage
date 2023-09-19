package kgurin.nsu;

public class Child implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < Parent.numberOfStringsToPrint; i++) {
            System.out.println("Hello from child. Counter: " + (i + 1));
        }
    }
}
