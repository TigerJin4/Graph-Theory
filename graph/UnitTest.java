package graph;

import ucb.junit.textui;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.nCopies;

import org.junit.Test;
import static org.junit.Assert.*;

/* You MAY add public @Test methods to this class.  You may also add
 * additional public classes containing "Testing" in their name. These
 * may not be part of your graph package per se (that is, it must be
 * possible to remove them and still have your package work). */

/** Unit tests for the graph package.  This class serves to dispatch
 *  other test classes, which are listed in the argument to runClasses.
 *  @author Wenhan Jin
 */
public class UnitTest {

    /** infinity. */
    static final double INF = Double.POSITIVE_INFINITY;

    /** DirectedGraph. */
    private static class DG extends DirectedGraph {
    }

    /** UndirectedGraph. */
    private static class UG extends UndirectedGraph {
    }

    /** Vertex count 1. */
    private static final int NV1 = 8;
    /** Edges 1. */
    private static final Object[][] E1 = {
            { 1, 2, 2.0 },
            { 1, 3, 5.0 },
            { 1, 4, 3.0 },
            { 1, 7, 7.0 },
            { 3, 2, 4.0 },
            { 2, 4, 5.0 },
            { 2, 5, 3.0 },
            { 5, 3, 2.0 },
            { 6, 3, 2.0 },
            { 7, 4, 3.0 },
            { 8, 4, 6.0 },
            { 4, 5, 4.0 },
            { 5, 8, 2.0 },
            { 5, 6, 1.0 },
            { 7, 8, 1.0 },
    };

    /** Map Data. */
    private static final int NV2 = 20;
    private static final Object[][] E2 = {
            { 13, 20, 71.0 },
            { 20, 1, 75.0 },
            { 13, 16, 151.0 },
            { 1, 16, 140.0 },
            { 1, 17, 118.0 },
            { 17, 10, 111.0 },
            { 10, 11, 70.0 },
            { 11, 4, 75.0 },
            { 4, 3, 120.0 },
            { 16, 15, 80.0 },
            { 15, 3, 146.0 },
            { 16, 6, 99.0 },
            { 15, 14, 97.0 },
            { 3, 14, 138.0 },
            { 6, 2, 211.0 },
            { 14, 2, 101.0 },
            { 2, 7, 90.0 },
            { 2, 18, 85.0 },
            { 18, 19, 142.0 },
            { 19, 9, 92.0 },
            { 9, 12, 87.0 },
            { 18, 8, 98.0 },
            { 8, 5, 161.0 },
    };

    /** Shortest distances, directed. */
    private static final Double[] DG_W1 = {
        INF, 0.0, 2.0, 5.0, 3.0, 5.0, 6.0, 7.0, 7.0
    };
    /** Predecessors, directed. */
    private static final Integer[] DG_P1 = {
        0, 0, 1, 1, 1, 2, 5, 1, 5
    };
    /** Path from 1 to 8, directed. */
    private static final Integer[] DG_P1_8 = {
        1, 2, 5, 8
    };

    /** Shortest distances, undirected. */
    private static final Double[] UG_W1 = {
        INF, 0.0, 2.0, 5.0, 3.0, 5.0, 6.0, 6.0, 7.0
    };
    /** Predecessors, undirected. */
    private static final Integer[] UG_P1 = {
        0, 0, 1, 1, 1, 2, 5, 4, 5
    };


    /** Direct distances to 2. */
    private static final double[] H2 = {
        INF,
        366.0, 0.0, 160.0, 242.0, 161.0, 176.0, 77.0, 151.0,
        226.0, 244.0, 241.0, 234.0, 380.0,
        100.0, 193.0, 253.0, 329.0, 80.0, 199.0, 374.0,
    };
    private static final Integer[] P2_2 = {
        1, 16, 15, 14, 2
    };


    /** Set up _G with NV vertices and the edges given in EDGES, whose
     *  entries are (V1 index, V2 index, edge weight). */
    private void fillWeightedGraph(Graph G, int nv, Object[][] edges) {
        _G = G;
        _W = new ArrayList<>();
        _W.add(null);
        for (int i = 1; i <= nv; i += 1) {
            int v = _G.add();
            _W.add(new ArrayList<>(nCopies(nv + 1,
                    Double.POSITIVE_INFINITY)));
            assertEquals("Bad vertex number returned by add", i, v);
        }
        for (Object[] e : edges) {
            _G.add((Integer) e[0], (Integer) e[1]);
            _W.get((Integer) e[0]).set((Integer) e[1], (Double) e[2]);
            if (!G.isDirected()) {
                _W.get((Integer) e[1]).set((Integer) e[0], (Double) e[2]);
            }
        }
    }

    /** Return the list of weights from SP, indexed by vertex number
     *  (missing vertices are INF). The maximum vertex number is MV. */
    List<Double> getWeights(ShortestPaths sp, int mv) {
        ArrayList<Double> R = new ArrayList<>(nCopies(mv + 1, INF));
        for (int i = 1; i <= mv; i += 1) {
            R.set(i, sp.getWeight(i));
        }
        return R;
    }

    /** Return the list of weights from SP, indexed by vertex number
     *  (missing vertices are INF). The maximum vertex number is MV. */
    List<Integer> getPreds(ShortestPaths sp, int mv) {
        ArrayList<Integer> R = new ArrayList<>(nCopies(mv + 1, 0));
        for (int i = 1; i <= mv; i += 1) {
            R.set(i, sp.getPredecessor(i));
        }
        return R;
    }


    class SP1 extends SimpleShortestPaths {

        SP1(int source) {
            super(UnitTest.this._G, source);
        }

        @Override
        public double getWeight(int u, int v) {
            return _W.get(u).get(v);
        }
    }

    /** Test all-points shortest paths with directed edges. */
    @Test
    public void directedShortestPaths() {
        fillWeightedGraph(new DG(), NV1, E1);
        ShortestPaths sp = new SP1(1);
        sp.setPaths();
        assertEquals("wrong weights", asList(DG_W1), getWeights(sp, NV1));
        assertEquals("wrong predecessors", asList(DG_P1), getPreds(sp, NV1));
        assertEquals("bad path", asList(DG_P1_8), sp.pathTo(8));
    }

    /** Test all-points shortest paths with undirected edges. */
    @Test
    public void undirectedShortestPaths() {
        fillWeightedGraph(new UG(), NV1, E1);
        ShortestPaths sp = new SP1(1);
        sp.setPaths();
        assertEquals("wrong weights", asList(UG_W1), getWeights(sp, NV1));
        assertEquals("wrong predecessors", asList(UG_P1), getPreds(sp, NV1));
        assertEquals("bad path", asList(DG_P1_8), sp.pathTo(8));
    }

    class SP2 extends SimpleShortestPaths {

        SP2(int source, int dest, double[] h) {
            super(UnitTest.this._G, source, dest);
            _h = h;
        }

        @Override
        public double getWeight(int u, int v) {
            return _W.get(u).get(v);
        }

        @Override
        protected double estimatedDistance(int v) {
            return _h[v];
        }

        private double[] _h;

    }

    /** Test A* search with directed edges.  Check that certain vertices are
     *  not visited. */
    @Test
    public void directedAStar() {
        fillWeightedGraph(new DG(), NV2, E2);
        ShortestPaths sp = new SP2(1, 2, H2);
        sp.setPaths();
        assertEquals("wrong path to Bucharest", asList(P2_2), sp.pathTo(2));
        assertEquals("looked too far", 0, sp.getPredecessor(18));
        assertEquals("heuristic didn't work", 0, sp.getPredecessor(10));
    }


    /** Test A* search with undirected edges.  Check that certain vertices are
     *  not visited. */
    @Test
    public void undirectedAStar() {
        fillWeightedGraph(new UG(), NV2, E2);
        ShortestPaths sp = new SP2(1, 2, H2);
        sp.setPaths();
        assertEquals("looked too far", 0, sp.getPredecessor(18));
        assertEquals("heuristic didn't work", 0, sp.getPredecessor(10));
        assertEquals("wrong path to Bucharest", asList(P2_2), sp.pathTo(2));
    }


    /** The test graph. */
    private Graph _G;
    private ArrayList<ArrayList<Double>> _W;

    @Test
    public void undirectedGraphObjConst() {
        UndirectedGraph ug = new UndirectedGraph();
        assertTrue(ug.getallVertices().isEmpty());
        assertTrue(ug.getallEdges().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChecktMyVertex2() {
        DirectedGraph dGraph = new DirectedGraph();
        dGraph.add();
        dGraph.checkMyVertex(1);
        dGraph.checkMyVertex(2);
    }

    @Test
    public void directedGraphObjConst() {
        DirectedGraph dg = new DirectedGraph();
        assertTrue(dg.getallVertices().isEmpty());
        assertTrue(dg.getallEdges().isEmpty());
    }

    @Test
    public void ugextensiveadd() {
        UndirectedGraph ug1 = new UndirectedGraph();
        ug1.add();
        assertEquals(1, ug1.vertexSize());
        assertEquals(1, ug1.maxVertex());
        assertEquals(0, ug1.edgeSize());
        assertEquals(0, ug1.inDegree(1));
        assertEquals(0, ug1.inDegree(3));
        assertTrue(ug1.contains(1));
        assertFalse(ug1.contains(1, 3));
        UndirectedGraph ug2 = new UndirectedGraph();
        ug2.add();
        ug2.add();
        ug2.add();
        assertTrue(ug2.contains(1));
        assertFalse(ug2.contains(1, 3));
        assertTrue(ug2.contains(2));
        assertTrue(ug2.contains(3));
        assertFalse(ug2.contains(4));
        ug2.add();
        assertEquals(4, ug2.vertexSize());
        assertEquals(4, ug2.maxVertex());
        assertEquals(0, ug2.edgeSize());
        assertEquals(0, ug2.inDegree(1));
        assertEquals(0, ug2.inDegree(3));
    }

    @Test
    public void dgextensiveadd() {
        DirectedGraph dg1 = new DirectedGraph();
        dg1.add();
        assertEquals(1, dg1.vertexSize());
        assertEquals(1, dg1.maxVertex());
        assertEquals(0, dg1.edgeSize());
        assertEquals(0, dg1.inDegree(1));
        assertEquals(0, dg1.inDegree(3));
        assertTrue(dg1.contains(1));
        assertFalse(dg1.contains(1, 3));
        DirectedGraph dg2 = new DirectedGraph();
        dg2.add();
        dg2.add();
        dg2.add();
        assertTrue(dg2.contains(1));
        assertFalse(dg2.contains(1, 3));
        assertTrue(dg2.contains(2));
        assertTrue(dg2.contains(3));
        assertFalse(dg2.contains(4));
        dg2.add();
        assertEquals(4, dg2.vertexSize());
        assertEquals(4, dg2.maxVertex());
        assertEquals(0, dg2.edgeSize());
        assertEquals(0, dg2.inDegree(1));
        assertEquals(0, dg2.inDegree(3));
    }

    @Test
    public void ugremovetwo() {
        UndirectedGraph ug = new UndirectedGraph();
        ug.add();
        ug.add();
        ug.add(1, 2);
        ug.add();
        ug.add(1, 3);
        ug.add();
        ug.add(2, 3);
        ug.add(3, 4);
        assertTrue(ug.contains(3));
        ug.remove(3);
        assertFalse(ug.contains(3));
        ug.add();
        assertTrue(ug.contains(3));
        assertTrue(ug.contains(1, 2));
        assertTrue(ug.contains(2, 1));
        assertFalse(ug.contains(1, 3));
        assertFalse(ug.contains(2, 3));
        assertFalse(ug.contains(3, 4));
    }

    @Test
    public void dgremovetwo() {
        DirectedGraph ug = new DirectedGraph();
        ug.add();
        ug.add();
        ug.add(1, 2);
        ug.add();
        ug.add(1, 3);
        ug.add();
        ug.add(2, 3);
        ug.add(3, 4);
        assertTrue(ug.contains(3));
        ug.remove(3);
        assertFalse(ug.contains(3));
        ug.add();
        assertTrue(ug.contains(3));
        assertTrue(ug.contains(1, 2));
        assertFalse(ug.contains(1, 3));
        assertFalse(ug.contains(2, 3));
        assertFalse(ug.contains(3, 4));
        assertFalse(ug.contains(2, 1));
    }

    @Test
    public void tester() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        a.add(3);
        a.remove(1);
        System.out.println(a);
    }

    @Test
    public void testvertices() {
        UndirectedGraph ug = new UndirectedGraph();
        ug.add();
        ug.add();
        ug.add();
        ug.add();
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int x : ug.vertices()) {
            result.add(x);
        }
        int first = result.get(0);
        int second = result.get(1);
        int third = result.get(2);
        int forth = result.get(3);
        assertEquals(1, first);
        assertEquals(2, second);
        assertEquals(3, third);
        assertEquals(4, forth);
    }

    @Test
    public void testOutgoing() {
        UndirectedGraph ug = new UndirectedGraph();
        ug.add();
        ug.add();
        ug.add();
        ug.add();
        ug.add(1, 1);
        ug.add(1, 2);
        ug.add(1, 3);
        ug.add(1, 4);
        assertEquals(4, ug.outDegree(1));
        ug.remove(1, 1);
        assertEquals(3, ug.outDegree(1));
        ug.add(1, 1);
        ug.remove(1);
        assertEquals(0, ug.outDegree(1));
        assertEquals(0, ug.outDegree(2));
    }

    @Test
    public void addremove() {
        DirectedGraph ug = new DirectedGraph();
        ug.add();
        ug.add();
        ug.add();
        ug.add();
        ug.remove(1);
        assertFalse(ug.getallVertices().contains(1));
        ug.add();
        assertTrue(ug.getallVertices().contains(1));
        ug.remove(1);
        ug.remove(2);
        assertFalse(ug.getallVertices().contains(1));
        assertFalse(ug.getallVertices().contains(2));
        ug.add();
        assertTrue(ug.getallVertices().contains(1));
        assertFalse(ug.getallVertices().contains(2));
    }


    @Test
    public void ugcheckedge() {
        UndirectedGraph ug = new UndirectedGraph();
        ug.add();
        ug.add(1, 1);
        assertEquals(1, ug.edgeSize());
    }

    @Test
    public void dgcheckedge() {
        DirectedGraph dg = new DirectedGraph();
        dg.add();
        dg.add(1, 1);
        assertEquals(1, dg.edgeSize());
    }

    @Test
    public void testDegree() {
        UndirectedGraph uDGraph = new UndirectedGraph();
        assertEquals(0, uDGraph.degree(1));
        uDGraph.add();
        uDGraph.add();
        uDGraph.add();
        assertEquals(0, uDGraph.degree(2));
        uDGraph.add(1, 2);
        uDGraph.add(1, 3);
        assertEquals(2, uDGraph.degree(1));
        assertEquals(1, uDGraph.degree(3));
        uDGraph.add(3, 1);
        assertEquals(1, uDGraph.degree(3));
    }

        /** Run all JUnit tests in the graph package. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(graph.GraphTest.class));
    }

}
