import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        double i = 1.0;
        String champion = "";
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            double probability = 1.0 / i;
            if (StdRandom.bernoulli(probability)) {
                champion = string;
            }
            i++;
        }
        StdOut.println(champion);
    }
}
