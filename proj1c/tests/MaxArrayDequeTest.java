import deque.MaxArrayDeque;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    private class TestObject {
        int value;
        TestObject(int value) {
            this.value = value;
        }

        public boolean equals(Object o) {
            // self check
            if (this == o)
                return true;
            // null check
            if (o == null)
                return false;
            // type check and cast
            if (getClass() != o.getClass())
                return false;
            TestObject t = (TestObject) o;
            // field comparison
            return t.value == this.value;
        }

        public String toString() {
            return "TestObject(" + this.value + ")";
        }
    }

    private class TestObjectComparator implements Comparator<TestObject> {
        public TestObjectComparator() {

        }
        public int compare(TestObject a, TestObject b) {
            return a.value - b.value;
        }
    }

    @Test
    public void testBasicComparator() {
        System.out.println("Testing MaxArrayDeque with our own custom comparator");
        MaxArrayDeque<TestObject> student = new MaxArrayDeque<TestObject>(new TestObjectComparator());

        for (int i = 0; i < 10; i++) {
            TestObject next = new TestObject(i);
            student.addFirst(next);
        }

        String errorMsg = "Max Array Deque did not correctly return the Max Value. ";
        errorMsg +=  "Try testing with your own custom objects and comparators\n";

        assertWithMessage(errorMsg).that(student.max()).isEqualTo(new TestObject(9));
    }

}