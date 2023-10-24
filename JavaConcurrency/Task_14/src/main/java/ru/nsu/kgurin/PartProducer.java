package ru.nsu.kgurin;

import java.util.concurrent.Semaphore;

public class PartProducer implements Runnable {
    String name;
    Semaphore semaphore;
    int timeToProduce;
    int index = 0;


    public PartProducer(String name, Semaphore semaphore, int timeToProduce) {
        this.name = name;
        this.semaphore = semaphore;
        this.timeToProduce = timeToProduce;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(timeToProduce);
                index++;
                System.out.println("Part - " + name + ", №" + index + " is produced");
                semaphore.release();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}