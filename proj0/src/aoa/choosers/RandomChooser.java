package aoa.choosers;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        chosenWord = ""; // TODO: Change this line
    }

    @Override
    public int makeGuess(char letter) {
        return 0;
    }

    @Override
    public String getPattern() {
        return "";
    }

    @Override
    public String getWord() {
        return "";
    }
}
