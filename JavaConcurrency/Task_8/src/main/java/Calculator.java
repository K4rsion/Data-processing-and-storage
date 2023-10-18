
public class Calculator extends Thread {
    int from;
    int to;
    int iteration;
    double result = 0.0;

    public Calculator(int s, int f) {
        from = s;
        to = f;
    }

    public int getIteration() {
        return iteration + 1;
    }

    public double getResult() {
        return result;
    }

    @Override
    public void run() {
        for (int i = from; i < to; i++) {
            result += Math.pow(-1, i) / (2 * i + 1);
            iteration = i;
        }
        result *= 4.0;
        System.out.println(result);
    }
}
