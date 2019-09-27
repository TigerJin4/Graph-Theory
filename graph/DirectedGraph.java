package graph;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Wenhan Jin
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        if (!getallVertices().contains(v)) {
            return 0;
        }
        return findallpredecessors(v).size();
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        if (!contains(v)) {
            return Iteration.iteration(new ArrayList<>());
        } else {
            return Iteration.iteration(findallpredecessorsEdgesorder(v));
        }
    }


    /** Return an arraylist that contains all predecessors of V. */
    private ArrayList<Integer> findallpredecessors(int v) {
        ArrayList<Integer> allpred = new ArrayList<>();
        for (int node : getallVertices()) {
            if (contains(node, v)) {
                allpred.add(node);
            }
        }
        return allpred;
    }

    /** Return an arraylist that contains predecessors of V
     * in edge insertion order. */
    private ArrayList<Integer> findallpredecessorsEdgesorder(int v) {
        ArrayList<Integer> allpred = new ArrayList<>();
        if (isDirected()) {
            for (ArrayList<Integer> e : getallEdges()) {
                if (e.get(1).equals(v)) {
                    allpred.add(e.get(0));
                }
            }
            return allpred;
        } else {
            for (ArrayList<Integer> edge : getallEdges()) {
                if (edge.get(0).equals(v)) {
                    allpred.add(edge.get(1));
                } else if (edge.get(1).equals(v)) {
                    allpred.add(edge.get(0));
                }
            }
            return allpred;
        }
    }
}
