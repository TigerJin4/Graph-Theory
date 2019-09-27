package graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Wenhan Jin
 */
abstract class GraphObj extends Graph {

    /**
     * A new, empty Graph.
     */
    GraphObj() {
        allVertices = new ArrayList<>();
        allEdges = new ArrayList<>();
    }

    @Override
    public int vertexSize() {
        return allVertices.size();
    }

    @Override
    public int maxVertex() {
        if (allVertices.isEmpty()) {
            return 0;
        }
        return Collections.max(allVertices);
    }

    /** Returns all of the vertices in this graph. */
    public ArrayList<Integer> getallVertices() {
        return allVertices;
    }
    /** Returns all of the edges in this graph. */
    public ArrayList<ArrayList<Integer>> getallEdges() {
        return allEdges;
    }

    @Override
    public int edgeSize() {
        return allEdges.size();
    }


    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (!allVertices.contains(v)) {
            return 0;
        }
        return findallsuccessors(v).size();
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return allVertices.contains(u);
    }

    @Override
    public boolean contains(int u, int v) {
        ArrayList<Integer> edge = new ArrayList<>();
        edge.add(u);
        edge.add(v);
        if ((allVertices.contains(u)) && (allVertices.contains(v))) {
            if (isDirected()) {
                for (ArrayList<Integer> i : allEdges) {
                    if (i.equals(edge)) {
                        return true;
                    }
                }
                return false;
            } else {
                ArrayList<Integer> edgeSame = new ArrayList<Integer>();
                edgeSame.add(v);
                edgeSame.add(u);
                for (ArrayList<Integer> i : allEdges) {
                    if ((i.equals(edge)) || (i.equals(edgeSame))) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public int add() {
        if (allVertices.size() == 0) {
            allVertices.add(1);
            return 1;
        } else {
            if (record.isEmpty()) {
                allVertices.add(maxVertex() + 1);
                return maxVertex();
            } else {
                int toadd = record.poll();
                allVertices.add(toadd);
                return toadd;
            }
        }
    }

    @Override
    public int add(int u, int v) {
        ArrayList<Integer> edge = new ArrayList<>();
        edge.add(u);
        edge.add(v);
        if (isDirected()) {
            if (!allEdges.contains(edge)) {
                allEdges.add(edge);
            }
            return edgeId(u, v);
        } else {
            ArrayList<Integer> edgeSame = new ArrayList<>();
            edgeSame.add(v);
            edgeSame.add(u);
            if (!allEdges.contains(edge) && (!allEdges.contains(edgeSame))) {
                allEdges.add(edge);
            }
            return edgeId(u, v);
        }
    }

    @Override
    public void remove(int v) {
        if (allVertices.contains(v)) {
            Integer a = v;
            allVertices.remove(a);
            record.add(v);
            ArrayList<ArrayList<Integer>> tester = new ArrayList<>(allEdges);
            for (ArrayList<Integer> e : tester) {
                if (e.contains(v)) {
                    allEdges.remove(e);
                }
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        ArrayList<Integer> edge = new ArrayList<>();
        edge.add(u);
        edge.add(v);
        if (isDirected()) {
            ArrayList<ArrayList<Integer>> copy
                    = new ArrayList<>(allEdges);
            for (ArrayList<Integer> i : copy) {
                if (i.equals(edge)) {
                    allEdges.remove(i);
                }
            }
        } else {
            ArrayList<Integer> edgeback = new ArrayList<>();
            edgeback.add(v);
            edgeback.add(u);
            ArrayList<ArrayList<Integer>> copy
                    = new ArrayList<>(allEdges);
            for (ArrayList<Integer> i : copy) {
                if ((i.equals(edge)) || (i.equals(edgeback))) {
                    allEdges.remove(i);
                }
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        return Iteration.iteration(allVertices);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        ArrayList<Integer> allsuccessors = new ArrayList<>();
        for (int[] edge : edges()) {
            if (isDirected()) {
                if (edge[0] == v) {
                    allsuccessors.add(edge[1]);
                }
            } else {
                if (edge[0] == v) {
                    allsuccessors.add(edge[1]);
                } else if (edge[1] == v) {
                    allsuccessors.add(edge[0]);
                }
            }
        }
        return Iteration.iteration(allsuccessors.iterator());
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);


    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> tempEdges = new ArrayList<>();
        for (ArrayList<Integer> edge : allEdges) {
            tempEdges.add(new int[]{edge.get(0), edge.get(1)});
        }
        return Iteration.iteration(tempEdges.iterator());
    }

    @Override
    protected void checkMyVertex(int v) {
        super.checkMyVertex(v);
    }

    @Override
    protected int edgeId(int u, int v) {
        if (!isDirected()) {
            int x = Math.max(u, v);
            int y = Math.min(u, v);
            return ((x + y) * (x + y + 1)) / 2 + y;
        }
        return ((u + v) * (u + v + 1)) / 2 + v;
    }




    /** Return an arraylist that contains successors of V. */
    private ArrayList<Integer> findallsuccessors(int v) {
        ArrayList<Integer> allsuccessors = new ArrayList<>();
        for (int[] edge : edges()) {
            if (isDirected()) {
                if (edge[0] == v) {
                    allsuccessors.add(edge[1]);
                }
            } else {
                if (edge[0] == v) {
                    allsuccessors.add(edge[1]);
                } else if (edge[1] == v) {
                    allsuccessors.add(edge[0]);
                }
            }
        }
        return allsuccessors;
    }

    /** All vertices. */
    private ArrayList<Integer> allVertices;

    /** All edges. */
    private ArrayList<ArrayList<Integer>> allEdges;

    /** A record. */
    private PriorityQueue<Integer> record = new PriorityQueue<>();
}
