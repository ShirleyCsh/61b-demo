package aoa.choosers;

import aoa.utils.FileUtils;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RandomChooserTest {

    public static final String DICTIONARY_FILE = "data/sorted_scrabble.txt";
    public static final String EXAMPLE_FILE = "data/example.txt";

    @Test
    public void testBasic() {
        RandomChooser rc = new RandomChooser(4, EXAMPLE_FILE);
        List<String> words = FileUtils.readWordsOfLength(EXAMPLE_FILE, 4);
        String word = rc.getWord();

        // Tests whether the word is in the list or not and checks for the correct word length.
        assertThat(word).isIn(words);
        assertThat(word.length()).isEqualTo(4);
        String pattern = rc.getPattern();

        //Tests the initial pattern.
        assertThat(pattern).isEqualTo("----");
    }

    @Test
    public void testWrongGuess() {
        RandomChooser rc = new RandomChooser(4, EXAMPLE_FILE);
        int i = rc.makeGuess('u');
        String pattern = rc.getPattern();

        // Tests whether there is a word that has letter 'u' in it.
        assertThat(i).isEqualTo(0);
        assertThat(pattern).isEqualTo("----");
    }

    @Test
    public void testCorrectGuesses() {
        RandomChooser rc = new RandomChooser(4, EXAMPLE_FILE);
        String word = rc.getWord();

        // Make first guess, either first or second letter of word
        int i = StdRandom.uniform(2);
        char firstGuess = word.charAt(i);
        int numRevealed = rc.makeGuess(firstGuess);

        assertThat(numRevealed).isGreaterThan(0);
        String pattern = rc.getPattern();
        assertThat(pattern.charAt(i)).isEqualTo(firstGuess);

        // Make second guess, either second or third letter of word
        char secondGuess = word.charAt(i + 1);
        numRevealed = rc.makeGuess(secondGuess);

        assertThat(numRevealed).isGreaterThan(0);
        pattern = rc.getPattern();
        assertThat(pattern.charAt(i + 1)).isEqualTo(secondGuess);
    }

    @Test
    public void testReturnedOccurrences() {
        RandomChooser rc = new RandomChooser(4, "data/example-ea.txt");

        // Check occurrences and pattern after guessing e
        int occurencesOfE = rc.makeGuess('e');
        String firstPattern = rc.getPattern();
        assertThat(occurencesOfE).isEqualTo(1);
        assertThat(firstPattern).isEqualTo("-e--");

        // Check occurrences and pattern after guessing a
        int occurencesOfA = rc.makeGuess('a');
        String secondPattern = rc.getPattern();
        assertThat(occurencesOfA).isEqualTo(1);
        assertThat(secondPattern).isEqualTo("-ea-");

        // Check occurrences and pattern after guessing o (not in any of the words)
        int occurencesOfO = rc.makeGuess('o');
        String thirdPattern = rc.getPattern();
        assertThat(occurencesOfO).isEqualTo(0);
        assertThat(thirdPattern).isEqualTo("-ea-");
    }

    @Test
    @Timeout(1)
    public void testRCNegativeLength() {
        assertThrows(IllegalArgumentException.class, () -> new RandomChooser(-1, DICTIONARY_FILE));
    }

    @Test
    @Timeout(1)
    public void testRCLargeLength() {
        assertThrows(IllegalStateException.class, () -> new RandomChooser(Integer.MAX_VALUE, DICTIONARY_FILE));
    }

    @Test
    @Timeout(1)
    public void testRCMedLength() {
        // No words of length 26, but there's longer word(s) ("floccinaucinihilipilification")
        assertThrows(IllegalStateException.class, () -> new RandomChooser(26, DICTIONARY_FILE));
    }
}
