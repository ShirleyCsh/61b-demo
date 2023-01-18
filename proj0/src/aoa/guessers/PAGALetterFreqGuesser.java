package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.List;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    public char getGuess(String pattern, List<Character> guesses) {
        return '?';
    }

    public static void main(String[] args) {
        PAGALetterFreqGuesser lfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(lfg.getGuess("----", List.of('e')));
    }
}
