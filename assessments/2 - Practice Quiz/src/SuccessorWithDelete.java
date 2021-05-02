/**
 * Successor with delete. Given a set of n integers S = { 0, 1, ... , n-1 } and
 * a sequence of requests of the following form:
 * <ul>
 * <li>Remove x from SS
 * <li>Find the successor of x: the smallest y in S such that y≥x.
 * </ul>
 * design a data type so that all operations (except construction) take
 * logarithmic time or better in the worst case.
 */
public class SuccessorWithDelete {
    private int n;
    private UnionFindWithEspecificCanonicalElement uf;

    public SuccessorWithDelete(int n) {
        this.n = n;
        uf = new UnionFindWithEspecificCanonicalElement(n);
    }

    public void remove(int x) {
        if (x < n-1) {
            uf.union(x, x+1);
        }
    }

    public int successor(int x) {
        return uf.find(x);
    }
}
