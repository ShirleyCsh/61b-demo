package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size = 0;
    private int nextFirst = 0;
    private int nextLast = 1;

    private static final int START_SIZE = 8;

    public ArrayDeque() {
        items = (T[]) new Object[START_SIZE];
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextFirst] = item;
        nextFirst = wrapIndex(nextFirst - 1);
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextLast] = item;
        nextLast = wrapIndex(nextLast + 1);
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public List<T> toList() {
        ArrayList<T> rList = new ArrayList<>();
        for (int i = 0; i < size; i += 1) {
            rList.add(get(i));
        }
        return rList;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (items.length > START_SIZE && size <= 0.25 * items.length) {
            resize(items.length / 2);
        }

        int index = wrapIndex(nextFirst + 1);
        T item = items[index];
        items[index] = null;
        nextFirst = index;
        size -= 1;
        return item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (items.length > START_SIZE && size <= 0.25 * items.length) {
            resize(items.length / 2);
        }

        int index = wrapIndex(nextLast - 1);
        T item = items[index];
        items[index] = null;
        nextLast = index;
        size -= 1;
        return item;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[wrapIndex(nextFirst + 1 + index)];
    }

    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    private int wrapIndex(int index) {
        return (index + items.length) % items.length;
    }

    private void resize(int newLength) {
        T[] newItems = (T[]) new Object[newLength];
        for (int i = 0; i < size; i += 1) {
            newItems[i] = get(i);
        }
        nextFirst = newLength - 1;
        nextLast = size;
        items = newItems;
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

    @Override
    public String toString() {
        return toList().toString();
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