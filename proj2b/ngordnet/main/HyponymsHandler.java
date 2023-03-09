package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMapWithPopularity;
import ngordnet.wordnet.WordNet;

import java.util.*;

/**
 * Created by hug.
 */
public class HyponymsHandler extends NgordnetQueryHandler {
    private NGramMapWithPopularity ngm;
    private WordNet wn;
    public HyponymsHandler(NGramMapWithPopularity ngm, WordNet wn) {
        super();
        this.ngm = ngm;
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<String> sortedHyponyms = new ArrayList<>(hyponyms(q));
        Collections.sort(sortedHyponyms);
        return sortedHyponyms.toString();
    }

    public Set<String> hyponyms(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        Set<String> hyponyms = new TreeSet<>();
        hyponyms.addAll(wn.hyponymsOfAll(words));

        if (k == 0) {
            return hyponyms;
        } else {
            return new TreeSet<>(ngm.kMostPopular(hyponyms, startYear, endYear, k));
        }
    }

}