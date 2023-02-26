package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    private class Node {
        private T value;
        private Node next, prev;

        Node(T value) {
            this.value = value;
        }
    }

    public void addFirst(T item) {
        Node oldFront = sentinel.next;
        Node newNode = new Node(item);

        sentinel.next = newNode;
        newNode.prev = sentinel;

        oldFront.prev = newNode;
        newNode.next = oldFront;

        size += 1;
    }

    public void addLast(T item) {
        Node oldBack = sentinel.prev;
        Node newNode = new Node(item);

        sentinel.prev = newNode;
        newNode.next = sentinel;

        oldBack.next = newNode;
        newNode.prev = oldBack;

        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        Node oldFront = sentinel.next;
        Node newFront = sentinel.next.next;

        sentinel.next = newFront;
        newFront.prev = sentinel;

        size -= 1;
        return oldFront.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }

        Node oldBack = sentinel.prev;
        Node newBack = sentinel.prev.prev;

        sentinel.prev = newBack;
        newBack.next = sentinel;

        size -= 1;
        return oldBack.value;
    }

    public T get(int index) {
        if ((index > size) || (index < 0)) {
            return null;
        }

        Node p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index = index - 1;
        }

        return p.value;
    }

    private T getItemRecursive(Node front, int index) {
        if(index == 0) {
            return front.value;
        }
        return getItemRecursive(front.next, index - 1);
    }

    public T getRecursive(int index) {
        if(index >= size || index < 0) {
            return null;
        }
        return getItemRecursive(sentinel.next, index);
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public List<T> toList() {
        ArrayList<T> rList = new ArrayList<>();
        Node n = sentinel;
        while (n.next != sentinel) {
            n = n.next;
            rList.add(n.value);
        }
        return rList;
    }

    @Override
    public String toString() {
        return toList().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> otherDeque = (Deque<T>) o;
        if (this.size() != otherDeque.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i += 1) {
            if (!this.get(i).equals(otherDeque.get(i))) {
                return false;
            }
        }
        return true;
    }

    private class DequeIterator implements Iterator<T> {
        int idx;

        public DequeIterator() {
            idx = 0;
        }

        public boolean hasNext() {
            return idx < size();
        }

        public T next() {
            T returnVal = get(idx);
            idx = idx + 1;
            return returnVal;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }
}