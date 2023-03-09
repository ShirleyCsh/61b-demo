package ngordnet.wordnet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SynsetGraph {
    private class Node {
        String synsetText;
        HashSet<Integer> neighbors;
        Node(String s) {
            synsetText = s;
            neighbors = new HashSet<>();
        }
    }

    private HashMap<Integer, Node> nodes = new HashMap<>();
    private HashMap<String, Set<Integer>> stringToNodes = new HashMap<>();

    /** Create a node with the given index and text. */
    public void createNode(int index, String synsetText) {
        Node n = new Node(synsetText);
        nodes.put(index, n);

        String[] nouns = synsetText.split(" ");
        for (String noun : nouns) {
            if (!stringToNodes.containsKey(noun)) {
                stringToNodes.put(noun, new HashSet<>());
            }
            stringToNodes.get(noun).add(index);
        }
    }

    /** Get all nodes associated with the given word. */
    public Set<Integer> getNodes(String word) {
        Set<Integer> result = stringToNodes.get(word);
        if (result == null) {
            return new HashSet<>();
        }
        return result;
    }

    /** Adds an edge from the given node number to the given node number. */
    public void addEdge(int from, int to) {
        Node fromNode = nodes.get(from);
        fromNode.neighbors.add(to);
    }

    /** Return neighbors of v. */
    public Set<Integer> neighbors(int v) {
        return nodes.get(v).neighbors;
    }

    /** Return all words in this graph. */
    public Set<String> words() {
        return stringToNodes.keySet();
    }

    public Set<String> synsetIdToWords(int id) {
        Set<String> words = new HashSet<>();
        String synsetText = nodes.get(id).synsetText;
        String[] allWords = synsetText.split(" ");
        for (String synsetWord : allWords) {
            words.add(synsetWord);
        }
        return words;
    }

    public Set<String> synsetsIdsToWords(Set<Integer> ids) {
        HashSet<String> words = new HashSet<>();
        for (int id : ids) {
            words.addAll(synsetIdToWords(id));
        }
        return words;
    }
}