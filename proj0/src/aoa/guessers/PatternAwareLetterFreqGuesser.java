package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.List;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    public char getGuess(String pattern, List<Character> guesses) {
        return '?';
    }

    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser lfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(lfg.getGuess("-e--", List.of('e')));
    }
}