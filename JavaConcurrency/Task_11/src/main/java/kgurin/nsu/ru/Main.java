package kgurin.nsu.ru;

import java.util.concurrent.Semaphore;

public class Main {
    static final int NUMBER_OF_STRING_TO_PRINT = 10;

    public static void main(String[] args) {

        Semaphore semParent = new Semaphore(1);
        Semaphore semChild = new Semaphore(0);

        Thread parent = new Thread(() -> {
            for (int i = 0; i < NUMBER_OF_STRING_TO_PRINT / 2; i++) {
                try {
                    semParent.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Parent");
                semChild.release();
            }
        });

        Thread child = new Thread(() -> {
            for (int i = 0; i < NUMBER_OF_STRING_TO_PRINT / 2; i++) {
                try {
                    semChild.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Child");
                semParent.release();
            }
        });

        parent.start();
        child.start();
    }
}