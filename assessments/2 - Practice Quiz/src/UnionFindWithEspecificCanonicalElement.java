/**
 * Union-find with specific canonical element. Add a method {@code find()} to
 * the union-find data type so that {@code find(i)} returns the largest element
 * in the connected component containing {@code i}. The operations,
 * {@code union()}, {@code connected()}, and {@code find()} should all take
 * logarithmic time or better.
 * 
 * For example, if one of the connected components is {1,2,6,9}, then the
 * {@code find()} method should return 9 for each of the four elements in the
 * connected components.
 */

public class UnionFindWithEspecificCanonicalElement {
    private int[] array; // array[i] = parent of i
    private int[] size; // size[i] = number of elements in subtree rooted at i
    private int[] largest; // largest[i] = largest element in subtree rooted at i
    private int count; // number of components in the union

    public UnionFindWithEspecificCanonicalElement(int n) {
        count = n;
        array = new int[n];
        size = new int[n];
        largest = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;
            size[i] = 1;
            largest[i] = i;
        }
    }

    public int count() {
        return count;
    }

    public int root(int p) {
        validate(p);
        while (p != array[p]) {
            array[p] = array[array[p]];
            p = array[p];
        }
        return p;
    }

    public int find(int i) {
        return largest[root(i)];
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private void validate(int p) {
        int n = array.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        int largestP = largest[rootP];
        int largestQ = largest[rootQ];
        if (rootP == rootQ) {
            return;
        }

        if (size[rootP] < size[rootQ]) {
            array[rootP] = rootQ;
            size[rootQ] += size[rootP];

            if (largestP > largestQ) {
                largest[rootQ] = largestP;
            }
        } else {
            array[rootQ] = rootP;
            size[rootP] += size[rootQ];

            if (largestQ > largestP) {
                largest[rootP] = largestQ;
            }
        }
        count--;
    }
}