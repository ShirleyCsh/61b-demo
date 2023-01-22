import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

public class MapExercisesTest {

    @Test
    public void testLetterToNum() {
        Map<Character, Integer> map = MapExercises.letterToNum();

        assertThat(map.size()).isEqualTo(26);
        for (int i = 0; i < 26; i++) {
            assertThat(map.get((char) ('a' + i))).isEqualTo(i + 1);
        }
        assertThat(map.containsKey('g')).isTrue();
    }

    @Test
    public void testSquares() {
        List<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(3);
        lst.add(6);
        lst.add(7);

        Map<Integer, Integer> map = MapExercises.squares(lst);

        assertThat(map.size()).isEqualTo(4);
        assertThat(map.get(1)).isEqualTo(1);
        assertThat(map.get(3)).isEqualTo(9);
        assertThat(map.get(6)).isEqualTo(36);
        assertThat(map.get(7)).isEqualTo(49);
        assertThat(map.get(2)).isNull();
        assertThat(map.get(8)).isNull();
    }

    @Test
    public void testCountWords() {
        List<String> lst = new ArrayList<>();
        lst.add("hug");
        lst.add("hug");
        lst.add("hug");
        lst.add("hug");
        lst.add("shreyas");
        lst.add("shreyas");
        lst.add("shreyas");
        lst.add("ergun");
        lst.add("ergun");
        lst.add("cs61b");

        Map<String, Integer> map = MapExercises.countWords(lst);

        assertThat(map.size()).isEqualTo(4);
        assertThat(map.containsKey("hug")).isTrue();
        assertThat(map.get("hug")).isEqualTo(4);
        assertThat(map.containsKey("shreyas")).isTrue();
        assertThat(map.get("shreyas")).isEqualTo(3);
        assertThat(map.containsKey("ergun")).isTrue();
        assertThat(map.get("ergun")).isEqualTo(2);
        assertThat(map.containsKey("cs61b")).isTrue();
        assertThat(map.get("cs61b")).isEqualTo(1);
        assertThat(map.get("laksith")).isNull();
    }
}
