package ru.nsu.kgurin;

public class Main {
    private static final int NUMBER_OF_DEPARTMENTS = 10;

    public static void main(String[] args) throws InterruptedException {
        Company doofenshmirtzEvilInc = new Company(NUMBER_OF_DEPARTMENTS);
        Founder founder = new Founder(doofenshmirtzEvilInc, NUMBER_OF_DEPARTMENTS);
        founder.start();
    }
}
