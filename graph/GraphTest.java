package graph;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author
 */
public class GraphTest {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

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
}
