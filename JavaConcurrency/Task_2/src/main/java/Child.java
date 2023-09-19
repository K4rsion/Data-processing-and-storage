public class Child implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < Parent.numberOfStringsToPrint; i++) {
            System.out.println("Hello from Child. Counter: " + (i+1));
        }
    }
}
