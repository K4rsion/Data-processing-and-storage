package ru.nsu.kgurin;

import java.util.ArrayList;
import java.util.Arrays;

public class Parent {
    public static void main(String[] args) {
        Child child1 = new Child(new ArrayList<>(Arrays.asList("Hello ", "I am ", "child1")));
        Child child2 = new Child(new ArrayList<>(Arrays.asList("My ", "name is ", "child2")));
        Child child3 = new Child(new ArrayList<>(Arrays.asList("Nice to ", "meet you ", "child3")));
        Child child4 = new Child(new ArrayList<>(Arrays.asList("Goodbye ", "everybody ", "child4")));

        Thread thread1 = new Thread(child1);
        Thread thread2 = new Thread(child2);
        Thread thread3 = new Thread(child3);
        Thread thread4 = new Thread(child4);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    public static void printText(ArrayList<String> strings){
        strings.forEach(System.out::println);
    }
}
