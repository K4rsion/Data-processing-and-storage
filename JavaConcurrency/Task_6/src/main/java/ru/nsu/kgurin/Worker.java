package ru.nsu.kgurin;

import java.util.concurrent.CountDownLatch;

public class Worker extends Thread {
    private final Department currentDepartment;
    CountDownLatch countDownLatch;

    public Worker(Department currentDepartment, CountDownLatch countDownLatch) {
        this.currentDepartment = currentDepartment;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        currentDepartment.performCalculations();
        System.out.println("Worker: " + Thread.currentThread().getName() +
                "\tDepartment: " + currentDepartment.getIdentifier() +
                "\tResult: " + currentDepartment.getCalculationResult());
        countDownLatch.countDown();
    }
}