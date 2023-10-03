package ru.nsu.kgurin;

class TestThread {
    public static void main(String[] args) {
        String a = "Hello ";
        String b = "there.";
        new PrintStringsThread(a, b);

        a = "How are ";
        b = "you?";
        new PrintStringsThread(a, b);

        a = "Thank you ";
        b = "very much!";
        new PrintStringsThread(a, b);

        a = "Good ";
        b = "Bye!";
        new PrintStringsThread(a, b);
    }
}