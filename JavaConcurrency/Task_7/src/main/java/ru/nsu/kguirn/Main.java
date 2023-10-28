package ru.nsu.kguirn;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final int ACCURACY = 10000;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter thread number: ");
        try {
            int number = scanner.nextInt();
            int iterationNum = (number * 2) * ACCURACY; //определяем число итераций
            int pivot = iterationNum / (number * 2);
            Calculator[] threads = new Calculator[number];

            for (int i = 0; i < number; i++) {
                threads[i] = new Calculator(pivot * i, pivot * (i + 1));
                threads[i].start();
            }

            double sum = 0.0;
            for (Calculator thread : threads) {
                thread.join();
                sum += thread.getResult();
            }

            System.out.println("Result value: " + sum);
        } catch (InputMismatchException ex) {
            System.out.println("Incorrect data format. Enter an integer.");
        }
    }
}
