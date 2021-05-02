import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final double[] threshold;
    private final double mean;
    private final double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        threshold = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }

            threshold[i] = (double) percolation.numberOfOpenSites() / (double) (n * n);
        }

        mean = StdStats.mean(threshold);
        stddev = StdStats.stddev(threshold);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(threshold.length)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(threshold.length)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(n, trials);

        System.out.println("mean                    = " + p.mean());
        System.out.println("stddev                  = " + p.stddev());
        System.out.println("95% confidence interval = " + "[" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }
}
