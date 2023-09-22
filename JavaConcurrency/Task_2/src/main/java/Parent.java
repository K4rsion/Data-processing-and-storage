public class Parent{
    static final int numberOfStringsToPrint = 10;
    public static void main(String[] args) throws InterruptedException {
        Child child = new Child();
        Thread thread = new Thread(child);
        thread.start();
        thread.join();
        for (int i = 0; i < numberOfStringsToPrint; i++) {
            System.out.println("Hello from Parent. Counter: " + (i+1));
        }
    }
}
