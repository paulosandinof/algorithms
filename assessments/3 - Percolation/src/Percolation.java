import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size; // Size of the grid
    private final WeightedQuickUnionUF unionFind;
    private final WeightedQuickUnionUF unionFindFull;
    private final int virtualTop;
    private final int virtualBottom;
    private boolean[] sites;
    private int count;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        size = n;
        unionFind = new WeightedQuickUnionUF(n * n + 2);
        unionFindFull = new WeightedQuickUnionUF(n * n + 1);
        sites = new boolean[n * n];
        count = 0;

        virtualTop = n * n;
        virtualBottom = n * n + 1;

        for (int i = 0; i < n * n; i++) {
            sites[i] = false;
        }
    }

    private void validate(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            throw new IllegalArgumentException();
        }
    }

    private int convert(int row, int col) {
        return (row - 1) * size + (col - 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        int current = convert(row, col);

        sites[current] = true;

        if (row == 1) {
            unionFind.union(virtualTop, current);
            unionFindFull.union(virtualTop, current);
        }

        if (row == size) {
            unionFind.union(virtualBottom, current);
        }

        if ((col > 1) && isOpen(row, col - 1)) {
            unionFind.union(current, convert(row, col - 1));
            unionFindFull.union(current, convert(row, col - 1));
        }

        if ((col < size) && isOpen(row, col + 1)) {
            unionFind.union(current, convert(row, col + 1));
            unionFindFull.union(current, convert(row, col + 1));
        }

        if ((row > 1) && isOpen(row - 1, col)) {
            unionFind.union(current, convert(row - 1, col));
            unionFindFull.union(current, convert(row - 1, col));
        }

        if ((row < size) && isOpen(row + 1, col)) {
            unionFind.union(current, convert(row + 1, col));
            unionFindFull.union(current, convert(row + 1, col));
        }

        count++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        int current = convert(row, col);

        return sites[current];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            return false;
        }

        int current = convert(row, col);

        return unionFindFull.find(current) == unionFindFull.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(virtualTop) == unionFind.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 3;

        Percolation p = new Percolation(n);

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