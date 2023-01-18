package aoa.choosers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EvilChooserTest {

    public static final String DICTIONARY_FILE = "data/sorted_scrabble.txt";
    public static final String EXAMPLE_FILE = "data/example.txt";

    @Test
    public void testBasic() {
        EvilChooser ec = new EvilChooser(4, EXAMPLE_FILE);

        //Tests constructor and initial pattern.
        String pattern = ec.getPattern();
        assertThat(pattern).isEqualTo("----");
    }

    @Test
    public void testSpec() {
        // Tests the case that is detailed in the spec.
        EvilChooser ec = new EvilChooser(4, EXAMPLE_FILE);

        int first = ec.makeGuess('e');
        assertThat(ec.getPattern()).isEqualTo("----");
        assertThat(first).isEqualTo(0);

        int second = ec.makeGuess('o');
        assertThat(ec.getPattern()).isEqualTo("-oo-");
        assertThat(second).isEqualTo(2);

        int third = ec.makeGuess('t');
        assertThat(ec.getPattern()).isEqualTo("-oo-");
        assertThat(third).isEqualTo(0);

        int fourth = ec.makeGuess('d');
        assertThat(ec.getPattern()).isEqualTo("-oo-");
        assertThat(fourth).isEqualTo(0);

        int last = ec.makeGuess('c');
        assertThat(ec.getPattern()).isEqualTo("coo-");
        assertThat(last).isEqualTo(1);
    }

    @Test
    @Timeout(1)
    public void testECNegativeLength() {
        assertThrows(IllegalArgumentException.class, () -> new EvilChooser(-1, DICTIONARY_FILE));
    }

    @Test
    @Timeout(1)
    public void testECLargeLength() {
        assertThrows(IllegalStateException.class, () -> new EvilChooser(Integer.MAX_VALUE, DICTIONARY_FILE));
    }

    @Test
    @Timeout(1)
    public void testECMedLength() {
        assertThrows(IllegalStateException.class, () -> new EvilChooser(26, DICTIONARY_FILE));
    }
}
