import java.util.*;
import java.util.stream.Collectors;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;
    private int size;

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    private Node getHelper(Node n, K key) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            return getHelper(n.left, key);
        } else if (cmp > 0) {
            return getHelper(n.right, key);
        } else {
            return n;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return getHelper(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node result = getHelper(root, key);
        if (result == null) {
            return null;
        }
        return result.value;
    }

    @Override
    public int size() {
        return size;
    }

    private Node putHelper(Node n, K key, V value) {
        if (n == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.left = putHelper(n.left, key, value);
        } else if (cmp > 0) {
            n.right = putHelper(n.right, key, value);
        } else {
            n.value = value;
        }
        return n;
    }

    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value);
    }

    public void printInOrder() {
        printInOrderHelper(root);
    }

    private void printInOrderHelper(Node n) {
        if (n == null) {
            return;
        }
        printInOrderHelper(n.left);
        System.out.println(n.key + " " + n.value);
        printInOrderHelper(n.right);
    }

    @Override
    public Set<K> keySet() {
        TreeSet<K> set = new TreeSet<>();
        for (K key : this) {
            set.add(key);
        }
        return set;
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        this.root = removeHelper(this.root, key);
        return value;
    }

    private Node removeHelper(Node n, K key) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.left = removeHelper(n.left, key);
        } else if (cmp > 0) {
            n.right = removeHelper(n.right, key);
        } else {
            return removeNode(n);
        }

        return n;
    }

    /** Removes Node n
     *  Precondition: n != null
     */
    private Node removeNode(Node n) {
        size--;

        /* Simple cases where there are 0 or 1 children. */
        if (n.left == null && n.right == null) {
            return null;
        } else if (n.left == null) {
            return n.right;
        } else if (n.right == null) {
            return n.left;
        }

        // Special case when tree looks like:
        //
        //       n
        //      /  \
        //   ...    node
        //           \
        //            ...
        if (n.right.left == null) {
            n.right.left = n.left;
            return n.right;
        }

        /* Search for the smallest valued node that is still
         * greater than the one we're deleting (Hibbard deletion). */
        Node s = n, t = n.right;
        while (t.left != null) {
            s = t;
            t = t.left;
        }

        t.left = n.left;
        s.left = t.right;
        t.right = n.right;
        return t;
    }

    private class BSTMapIterator implements Iterator<K> {
        int i;
        List<Node> keys;
        BSTMapIterator(Node n) {
            keys = new ArrayList<>();
            addKeys(n);
        }

        private void addKeys(Node n) {
            if (n == null) {
                return;
            }
            addKeys(n.left);
            keys.add(n);
            addKeys(n.right);
        }

        @Override
        public boolean hasNext() {
            return i < keys.size();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            K result = keys.get(i).key;
            i++;
            return result;
        }
    }

    /** Alternate solution (don't share because mt question lol):
     private class BSTMapIterator implements Iterator<K> {
     Iterator<K> iterLeft;
     K current;
     Iterator<K> iterRight;

     BSTMapIterator(Node n) {
     if (n == null) {
     iterLeft = Collections.emptyIterator();
     current = null;
     iterRight = Collections.emptyIterator();
     } else {
     iterLeft = new BSTMapIterator(n.left);
     current = n.key;
     iterRight = new BSTMapIterator(n.right);
     }
     }

     @Override
     public boolean hasNext() {
     return iterLeft.hasNext() || current != null || iterRight.hasNext();
     }

     @Override
     public K next() {
     if (!hasNext()) {
     throw new NoSuchElementException();
     }
     if (iterLeft.hasNext()) {
     return iterLeft.next();
     } else if (current != null) {
     K result = current;
     current = null;
     return result;
     } else {
     return iterRight.next();
     }
     }
     }
     */

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator(root);
    }

}