package aoa.guessers;

import org.junit.jupiter.api.Test;
import java.util.List;
import static com.google.common.truth.Truth.assertThat;

public class PAGALetterFreqGuesserTest {
    @Test
    public void testGetGuessBlankThen_o__Pattern() {
        PAGALetterFreqGuesser nlfg = new PAGALetterFreqGuesser("data/example.txt");

        // check that the first guess is e, the most common letter in the dictionary.
        char guess = nlfg.getGuess("----", List.of());
        assertThat(guess).isEqualTo('e');

        // check that the next guess is o if someone has already guessed e.
        guess = nlfg.getGuess("----", List.of('e'));
        assertThat(guess).isEqualTo('o');

        // check that the next guess is c if someone has already guessed e and o.
        guess = nlfg.getGuess("-oo-", List.of('e', 'o'));
        assertThat(guess).isEqualTo('c');
    }
}
