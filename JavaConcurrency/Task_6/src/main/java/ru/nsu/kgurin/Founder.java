package ru.nsu.kgurin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public final class Founder {
    private final List<Worker> workers;
    private final Company company;
    private final CountDownLatch countDownLatch;

    public Founder(final Company company, int numberOfWorkers) {
        countDownLatch = new CountDownLatch(numberOfWorkers);
        this.company = company;
        this.workers = new ArrayList<>(company.getDepartmentsCount());
        for (int i = 0; i < numberOfWorkers; i++) {
            workers.add(i, new Worker(company.getFreeDepartment(i), countDownLatch));
        }
    }

    public void start() throws InterruptedException {

        for (final Worker worker : workers) {
            new Thread(worker).start();
        }
        countDownLatch.await();
        company.showCollaborativeResult();
    }
}