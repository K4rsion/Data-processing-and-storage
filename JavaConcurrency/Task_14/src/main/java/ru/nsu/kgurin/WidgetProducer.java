package ru.nsu.kgurin;

import java.util.concurrent.Semaphore;

public class WidgetProducer implements Runnable {

    Semaphore partC;
    Semaphore module;
    int index = 0;

    public WidgetProducer(Semaphore partC, Semaphore module){
        this.partC = partC;
        this.module = module;
    }

    @Override
    public void run() {
        try{
            module.acquire();
            partC.acquire();
            index++;
            System.out.println("Widget №" + index + " is produced");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
