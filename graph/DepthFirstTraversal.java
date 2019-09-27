package graph;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.NoSuchElementException;


/* See restrictions in Graph.java. */

/** Implements a depth-first traversal of a graph.  Generally, the
 *  client will extend this class, overriding the visit and
 *  postVisit methods, as desired (by default, they do nothing).
 *  @author Wenhan Jin
 */
public class DepthFirstTraversal extends Traversal {

    /** A depth-first Traversal of G. */
    protected DepthFirstTraversal(Graph G) {
        super(G, Collections.asLifoQueue(new ArrayDeque<>()));
        _G = G;
        pv = new boolean[_G.maxVertex()];
    }

    @Override
    protected boolean visit(int v) {
        return super.visit(v);
    }

    @Override
    protected boolean postVisit(int v) {
        pv[v - 1] = true;
        for (Integer successor : _G.successors(v)) {
            if (marked(v) && !pv[successor - 1]) {
                throw new NoSuchElementException("Error: circular dependency");
            }
        }
        return super.postVisit(v);
    }

    @Override
    protected boolean shouldPostVisit(int v) {
        return true;
    }

    @Override
    protected boolean reverseSuccessors(int v) {
        return true;
    }


    /** pv represents the state of whether each post visited node. */
    private boolean[] pv;

    /** Graph _G. */
    private Graph _G;
}
