package graph;

/* See restrictions in Graph.java. */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.Collections;
import java.util.List;
import java.util.AbstractQueue;
import java.util.Iterator;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Wenhan Jin
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        _comp = new CompareVertices();
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        ArrayList<Integer> visited = new ArrayList<>();
        TreeQueue<Integer> queue = new TreeQueue<>(_comp);
        for (int vertex: _G.vertices()) {
            if (vertex == _source) {
                setWeight(vertex, 0);
            } else {
                setWeight(vertex, Double.MAX_VALUE);
            }
            queue.add(vertex);
            setPredecessor(vertex, 0);
        }
        queue.add(_source);
        while ((!queue.isEmpty())) {
            int current = queue.poll();
            visited.add(current);
            if (current == _dest) {
                break;
            }
            for (int next: _G.successors(current)) {
                double oldcost = getWeight(next);
                double newcost = getWeight(current)
                        + getWeight(current, next);
                if ((visited.contains(next)) && (newcost > oldcost)) {
                    continue;
                } else if (newcost < oldcost) {
                    setWeight(next, newcost);
                    setPredecessor(next, current);
                    if (queue.contains(next)) {
                        queue.remove(next);
                        queue.add(next);
                    } else {
                        queue.add(next);
                    }
                }
            }
        }
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        ArrayList<Integer> path = new ArrayList<>();
        path.add(v);
        int pred = getPredecessor(v);
        while (pred != getSource() && pred != 0) {
            path.add(pred);
            pred = getPredecessor(pred);

        }
        path.add(getSource());
        Collections.reverse(path);
        return path;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }


    /** Implementation of class TreeQueue. */
    private class TreeQueue<T> extends AbstractQueue<T> {

        /** The tree structure. */
        private TreeSet<T> _tree;

        /** Returns the tree stucture. **/
        public TreeSet<T> get() {
            return _tree;
        }

        /** Constructor of TreeQueue from a comparator C. */
        private TreeQueue(Comparator<T> c) {
            _tree = new TreeSet<>(c);
        }

        @Override
        public boolean add(T elem) {
            return _tree.add(elem);
        }

        @Override
        public Iterator<T> iterator() {
            return _tree.iterator();
        }

        @Override
        public int size() {
            return _tree.size();
        }

        @Override
        public boolean offer(T elem) {
            return _tree.add(elem);
        }

        @Override
        public T poll() {
            return _tree.pollFirst();
        }

        @Override
        public T peek() {
            return _tree.pollLast();
        }
    }

    /** A comparator. */
    private class CompareVertices implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            double x = getWeight(a) + estimatedDistance(a);
            double y = getWeight(b) + estimatedDistance(b);
            if (y <= x) {
                return 1;
            }
            return -1;
        }
    }


    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** A comparator. */
    private Comparator<Integer> _comp;

}
