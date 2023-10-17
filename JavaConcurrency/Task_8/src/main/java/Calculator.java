import java.util.concurrent.Callable;

public class Calculator implements Callable<Double> {
    int from;
    int to;
    double result = 0.0;

    public Calculator(int s, int f) {
        from = s;
        to = f;
    }

    @Override
    public Double call() {
        for (int i = from; i < to; i++) {
            result += Math.pow(-1, i) / (2 * i + 1);
        }
        return result*4.0;
    }
}
