import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation2 {
    private static final byte BLOCKED = 0b000;
    private static final byte FULL = 0b110;
    private static final byte OPEN = 0b100;
    private static final byte EMPTY = 0b101;
    private static final byte PERCOLATED = 0b111;

    private final int n; // Size of the grid
    private final WeightedQuickUnionUF unionFind;
    private byte[] sites;
    private int count;
    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        sites = new byte[n * n];
        count = 0;
        percolates = false;

        for (int i = 0; i < n * n; i++) {
            sites[i] = BLOCKED;
        }
    }

    private void validate(int row, int col) {
        if ((row < 1 || row > n) || (col < 1 || col > n)) {
            throw new IllegalArgumentException();
        }
    }

    private int convert(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    private void connect(int current, int adjacent) {
        int rootCurrent = unionFind.find(current);
        int rootAdjacent = unionFind.find(adjacent);

        unionFind.union(current, adjacent);

        int newRoot = unionFind.find(current);

        sites[newRoot] = (byte) (sites[rootCurrent] | sites[rootAdjacent]);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        int current = convert(row, col);

        sites[current] = OPEN;

        if (row == 1) {
            sites[current] = (byte) (sites[current] | FULL);
        }
        
        if (row == n) {
            sites[current] = (byte) (sites[current] | EMPTY);
        }

        if ((col > 1) && isOpen(row, col - 1)) {
            connect(current, convert(row, col - 1));
        }

        if ((col < n) && isOpen(row, col + 1)) {
            connect(current, convert(row, col + 1));
        }

        if ((row > 1) && isOpen(row - 1, col)) {
            connect(current, convert(row - 1, col));
        }

        if ((row < n) && isOpen(row + 1, col)) {
            connect(current, convert(row + 1, col));
        }

        int newRoot = unionFind.find(current);

        if (sites[newRoot] == PERCOLATED) {
            percolates = true;
        }

        count++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        int current = convert(row, col);

        return sites[current] != 0b000;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        int current = convert(row, col);

        int root = unionFind.find(current);

        return (sites[root] == FULL) || (sites[root] == PERCOLATED);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 3;

        Percolation2 p = new Percolation2(n);

        p.open(3, 2);
        p.open(2, 1);
        p.open(2, 2);
        p.open(2, 3);
        p.open(1, 3);

        StdOut.println(p.isFull(1, 1));
        StdOut.println(p.percolates());

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (p.isFull(i, j)) {
                    StdOut.print("\u001B[34m" + i + "," + j + "\u001B[0m ");
                } else if (p.isOpen(i, j)) {
                    StdOut.print("\u001B[32m" + i + "," + j + "\u001B[0m ");
                } else {
                    StdOut.print("\u001B[31m" + i + "," + j + "\u001B[0m ");
                }
            }
            StdOut.println();
        }
    }
}