package aoa.choosers;

import java.util.List;

public class EvilChooser implements Chooser {
    private String pattern;
    private List<String> wordPool;

    public EvilChooser(int wordLength, String dictionaryFile) {

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
