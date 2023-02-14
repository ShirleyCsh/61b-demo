package deque;

import java.util.Comparator;

/**
 * Created by hug.
 */
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        comparator = c;
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        int biggest = 0;
        for (int i = 0; i < size(); i += 1) {
            int cmp = c.compare(get(i), get(biggest));
            if (cmp > 0) {
                biggest = i;
            }
        }
        return get(biggest);
    }

}