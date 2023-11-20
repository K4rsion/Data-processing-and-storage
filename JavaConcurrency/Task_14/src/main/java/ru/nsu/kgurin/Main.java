package ru.nsu.kgurin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore partA = new Semaphore(0);
        Semaphore partB = new Semaphore(0);
        Semaphore partC = new Semaphore(0);
        Semaphore module = new Semaphore(0);

        List<Thread> conveyor = new ArrayList<>();
        conveyor.add(new Thread(new PartProducer("A", partA, 1000)));
        conveyor.add(new Thread(new PartProducer("B", partB, 2000)));
        conveyor.add(new Thread(new PartProducer("C", partC, 3000)));
        conveyor.add(new Thread(new ModuleProducer(partA, partB, module)));
        conveyor.add(new Thread(new WidgetProducer(partC, module)));

        conveyor.forEach(Thread::start);
    }
}