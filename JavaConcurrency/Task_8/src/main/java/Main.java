import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import static java.lang.Thread.sleep;

public class Main {
    private static final int ACCURACY = 100000;
    private static Calculator[] threads;

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Ctrl+C");
            try {
                sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            List<Integer> iterations = new ArrayList<>();
            List<Double> results = new ArrayList<>();

            /*
            Create iterations list to find max iteration,
            create results list to determine current result for every thread
            and after that interrupt all threads.
             */
            for (var thread : threads) {
                iterations.add(thread.getIteration() % ACCURACY);
                results.add(thread.getResult());
                thread.interrupt();
            }

            /*
            Find max iteration number
             */
            int maxIteration = iterations.stream().max(Integer::compare).orElse(0);

            /*
            Take current thread result, calculate the rest of iteration and add it to result.
             */
            for (int i = 0; i < iterations.size(); i++) {
                Double temp = results.get(i);
                temp += calculate(iterations.get(i) + ACCURACY * i, maxIteration + ACCURACY * i, results.get(i));
                results.set(i, temp);
            }

            System.out.println("Result value (after Ctrl+C): " + results.stream()
                    .mapToDouble(Double::doubleValue)
                    .sum());
        }));

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter thread number: ");

            int number = scanner.nextInt();
            int pivot = ACCURACY;
            threads = new Calculator[number];

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


        } catch (InputMismatchException | InterruptedException ex) {
            System.out.println("Incorrect data format. Enter an integer.");
        }
    }

    private static double calculate(int from, int to, double result) {
        if (from != to) {
            for (int i = from; i < to; i++) {
                result += Math.pow(-1, i) / (2 * i + 1);
            }
            return result * 4.0;
        }
        return 0.0;
    }
}
