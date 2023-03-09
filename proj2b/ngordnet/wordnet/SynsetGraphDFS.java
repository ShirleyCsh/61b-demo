package ngordnet.wordnet;

import java.util.HashSet;
import java.util.Set;

public class SynsetGraphDFS {
    /** Finds all vertices reachable from start. */
    private HashSet<Integer> marked;

    public SynsetGraphDFS(SynsetGraph sg, int start) {
        marked = new HashSet<>();
        dfs(sg, start);
    }

    private void dfs(SynsetGraph sg, int v) {
        marked.add(v);
        for (int n : sg.neighbors(v)) {
            if (!marked.contains(n)) {
                marked.add(n);
                dfs(sg, n);
            }
        }
    }

    public Set<Integer> reachable() {
        return marked;
    }
}