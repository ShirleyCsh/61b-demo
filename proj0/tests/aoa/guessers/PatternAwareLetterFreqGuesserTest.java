package aoa.guessers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class PatternAwareLetterFreqGuesserTest {
    @Test
    public void testGetGuessBlankPattern() {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");

        // check that the first guess is e, the most common letter in the dictionary.
        char guess = palfg.getGuess("----", List.of());
        assertThat(guess).isEqualTo('e');

        // check that the next guess is l if someone has already guessed e.
        guess = palfg.getGuess("----", List.of('e'));
        assertThat(guess).isEqualTo('l');

        // check that the next guess is b if someone has already guessed l, o, x, a, e (in that order).
        guess = palfg.getGuess("----", List.of('l', 'o', 'x', 'a', 'e'));
        assertThat(guess).isEqualTo('b');
    }

    @Test
    public void testGetGuess_o__Pattern() {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");

        // check that the first guess is c, the most common letter in the dictionary if the 2nd letter is o.
        char guess = palfg.getGuess("-o--", List.of('o'));
        assertThat(guess).isEqualTo('c');

        // check that the next guess is d.
        guess = palfg.getGuess("-o--", List.of('o', 'c'));
        assertThat(guess).isEqualTo('d');

        // check that the next guess is e, if the previous guessers were d, c, x, o.
        guess = palfg.getGuess("-o--", List.of('d', 'c', 'x', 'o'));
        assertThat(guess).isEqualTo('e');
    }

    @Test
    public void testGetGuess___lPattern() {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");

        // check that the first guess is a, the most common letter in the dictionary if the last letter is l.
        char guess = palfg.getGuess("---l", List.of('l'));
        assertThat(guess).isEqualTo('o');

        // check that the next guess is c.
        guess = palfg.getGuess("---l", List.of('l', 'o'));
        assertThat(guess).isEqualTo('a');

        // check that the next guess is e, if the previous guessers were l, o, a.
        guess = palfg.getGuess("--al", List.of('l', 'o', 'a'));
        assertThat(guess).isEqualTo('d');
    }
}
