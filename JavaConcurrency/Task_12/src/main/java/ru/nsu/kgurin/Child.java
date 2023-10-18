package ru.nsu.kgurin;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Child extends Thread {
    private final LinkedList<String> list;

    public Child(LinkedList<String> list) {
        this.list = list;
    }

    private void bubbleSort() {
        String temp;
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                if (list.get(j).compareTo(list.get(j + 1)) > 0) {
                    temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized(list) {
                    bubbleSort();
                }
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            }
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
