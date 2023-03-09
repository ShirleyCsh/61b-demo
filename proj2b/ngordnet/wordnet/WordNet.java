package ngordnet.wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;

import static org.junit.Assert.assertEquals;

/** An object that stores the WordNet graph in a manner useful for extracting
 *  all hyponyms of a word.
 *  @author Josh Hug
 */
public class WordNet {
    /* Number of synsets, indexed from 1 to V */
    private int V;
    /* Digraph representing hyponyms. */
    private SynsetGraph G;

    /** Creates a WordNet using files form SYNSETFILENAME and HYPONYMFILENAME */
    public WordNet(String synsetFilename, String hyponymFilename) {
        G = new SynsetGraph();
        createNodes(synsetFilename);
        //  6829,Goofy,a cartoon character created by Walt Disney
        addEdges(hyponymFilename);
        // 11,12,13
    }

    private void createNodes(String synsetFilename) {
        In in = new In(synsetFilename);
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            int    id     = Integer.parseInt(tokens[0]);
            String synsetText = tokens[1];
            String defn   = tokens[2];
            // id, synset_text, definition
            // example: 36338,draftsman drawer,an artist skilled at drawing

            G.createNode(id, synsetText);
        }
    }

    /* Add the edges to the graph/ */
    private void addEdges(String hyponymsFile) {
        In in = new In(hyponymsFile);

        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            int from = Integer.parseInt(tokens[0]);
            for (int i = 1; i < tokens.length; i++) {
                int to = Integer.parseInt(tokens[i]);
                G.addEdge(from, to);
            }
        }
    }

    /** Get hyponyms as words of a given index. */
    public Set<String> hyponymsOfIndex(int index) {
        SynsetGraphDFS sgdfs = new SynsetGraphDFS(G, index);
        Set<Integer> reachableVertices = sgdfs.reachable();
        return G.synsetsIdsToWords(reachableVertices);
    }

    public Set<String> hyponyms(String word) {
        Set<Integer> vertices = G.getNodes(word);
        Set<String> allHyponyms = new HashSet<>();

        for (int vertex : vertices) {
            Set<String> hyponymsOfThisWord = hyponymsOfIndex(vertex);
            allHyponyms.addAll(hyponymsOfThisWord);
        }

        return allHyponyms;
    }

    /** Returns words which are hyponyms of all of the given words. */
    public Set<String> hyponymsOfAll(List<String> words) {
        if (words.size() == 0) {
            return new TreeSet<>();
        }
        Set<String> hyponymsOfAll;
        hyponymsOfAll = hyponyms(words.get(0));
        for (int i = 1; i < words.size(); i += 1) {
            hyponymsOfAll.retainAll(hyponyms(words.get(i)));
        }
        return hyponymsOfAll;
    }

    public SynsetGraph getGraph() {
        return G;
    }
}