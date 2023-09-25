package ru.nsu.kgurin;

import java.util.ArrayList;

public class Child implements Runnable{
    private final ArrayList<String> stringsToPrint;
    public Child(ArrayList<String> stringsToPrint){
        this.stringsToPrint = stringsToPrint;
    }
    @Override
    public void run() {
        Parent.printText(stringsToPrint);
    }
}
