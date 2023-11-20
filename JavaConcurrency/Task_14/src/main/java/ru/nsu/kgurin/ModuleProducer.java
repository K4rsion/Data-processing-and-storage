package ru.nsu.kgurin;

import java.util.concurrent.Semaphore;

public class ModuleProducer implements Runnable {

    Semaphore partA;
    Semaphore partB;
    Semaphore module;
    int index = 0;

    public ModuleProducer(Semaphore partA, Semaphore partB, Semaphore module) {
        this.partA = partA;
        this.partB = partB;
        this.module = module;
    }


    @Override
    public void run() {
        try {
            while (true) {
                partA.acquire();
                partB.acquire();
                index++;
                System.out.println("Module №" + index + " is produced");
                module.release();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
