package graph;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Wenhan Jin
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        _weights = new double[G.vertexSize()];
        _predecessors = new int[G.vertexSize()];

    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        if (!_G.contains(v)) {
            return Double.POSITIVE_INFINITY;
        }
        return _weights[v - 1];

    }

    @Override
    protected void setWeight(int v, double w) {
        _weights[v - 1] = w;
    }

    @Override
    public int getPredecessor(int v) {
        if (!_G.contains(v)) {
            return 0;
        }
        return _predecessors[v - 1];
    }

    @Override
    protected void setPredecessor(int v, int u) {
        _predecessors[v - 1] = u;
    }


    /** Edge weights matching. */
    private double[] _weights;
    /** Predecessor matching. */
    private int[] _predecessors;

}
