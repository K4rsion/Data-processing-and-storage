package ru.nsu.kgurin;

import java.util.LinkedList;
import java.util.Scanner;

public class Parent {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        Thread thread = new Thread(new Child(list));
        thread.start();
        while (true) {
            System.out.print("Введите строку (или нажмите Enter для вывода текущего состояния списка): ");
            String input = scanner.nextLine();
            String[] lines = input.split("(?<=\\G.{80})");
            for (var line : lines) {
                synchronized (list) {
                    if (line.isEmpty()) {
                        list.forEach(System.out::println);
                    } else {
                        list.addFirst(line);
                    }
                }
            }
        }
    }
}