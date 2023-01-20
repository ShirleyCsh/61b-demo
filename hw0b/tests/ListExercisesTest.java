package hw0b.basics;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ListExercisesTest {

    @Test
    public void testSum() {
        List<Integer> lst1 = new ArrayList<>();
        List<Integer> lst2 = new ArrayList<>();
        lst1.add(1);
        lst1.add(2);
        lst1.add(3);
        lst1.add(4);

        assertThat(ListExercises.sum(lst1)).isEqualTo(10);
        assertThat(ListExercises.sum(lst2)).isEqualTo(0);
    }

    @Test
    public void testEvens() {
        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        lst.add(3);
        lst.add(4);
        lst.add(5);
        lst.add(6);

        List<Integer> lstExpected = new ArrayList<>();
        lstExpected.add(2);
        lstExpected.add(4);
        lstExpected.add(6);

        List<Integer> res = ListExercises.evens(lst);

        assertThat(lstExpected).isEqualTo(res);
    }

    @Test
    public void testCommon() {
        List<Integer> lst1 = new ArrayList<>();
        lst1.add(1);
        lst1.add(2);
        lst1.add(3);
        lst1.add(4);
        lst1.add(5);
        lst1.add(6);

        List<Integer> lst2 = new ArrayList<>();
        lst2.add(4);
        lst2.add(5);
        lst2.add(6);
        lst2.add(7);
        lst2.add(8);
        lst2.add(9);

        List<Integer> lst3 = new ArrayList<>();

        List<Integer> lstExpected = new ArrayList<>();
        lstExpected.add(4);
        lstExpected.add(5);
        lstExpected.add(6);

        List<Integer> res1 = ListExercises.common(lst1, lst2);
        List<Integer> res2 = ListExercises.common(lst2, lst3);

        assertThat(lstExpected).isEqualTo(res1);
        assertThat(res2).isEmpty();
    }

    @Test
    public void testCountOccurrencesOfC() {
        List<String> lst = new ArrayList<>();
        lst.add("hello");
        lst.add("world");
        lst.add("welcome");

        assertThat(ListExercises.countOccurrencesOfC(lst,'o')).isEqualTo(3);
        assertThat(ListExercises.countOccurrencesOfC(lst,'a')).isEqualTo(0);
    }
}
