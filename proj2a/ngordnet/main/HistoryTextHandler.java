package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap ngm;

    public HistoryTextHandler(NGramMap ngm) {
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String returnString = "";

        for (String word : words) {
            returnString += word + ": ";
            returnString += ngm.weightHistory(word, startYear, endYear);
            returnString += "\n";
        }

        return returnString;
    }
}