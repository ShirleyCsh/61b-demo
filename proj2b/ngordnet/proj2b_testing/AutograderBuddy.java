package ngordnet.proj2b_testing;

import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.ngrams.NGramMapWithPopularity;
import ngordnet.wordnet.WordNet;

public class AutograderBuddy {
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

        NGramMapWithPopularity ngm = new NGramMapWithPopularity(wordFile, countFile);
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        return new HyponymsHandler(ngm, wn);
    }
}