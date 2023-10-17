import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int ACCURACY = 10000;
    private static double sum = 0.0;
    private static ExecutorService pool;

    public static void main(String[] args) {


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Ctrl+C");

            try {
                if (pool.awaitTermination(3, TimeUnit.SECONDS)) {
                    System.out.println("Result value(SIGINT CAPTURED): " + sum);
                } else {
                    System.out.println("Result value(FORCED TERMINATION): " + sum);
                }
            } catch (InterruptedException e) {
                System.out.println(pool);
            }
        }));

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter thread number: ");

            int number = scanner.nextInt();
            int iterationNum = (number * 2) * ACCURACY;
            int pivot = iterationNum / (number * 2);
            pool = Executors.newFixedThreadPool(number);

            for (int i = 0; i < number; i++) {
                Future<Double> result = pool.submit(new Calculator(pivot * i, pivot * (i + 1)));
                Thread.sleep(1000);
                sum += result.get();
                System.out.println(result.get());
            }

            pool.shutdown();
            System.out.println("Result value: " + sum);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}