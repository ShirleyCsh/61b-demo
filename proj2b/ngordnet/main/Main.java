package ngordnet.main;

import ngordnet.browser.NgordnetServer;
import ngordnet.ngrams.NGramMapWithPopularity;
import ngordnet.wordnet.WordNet;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        String wordFile = "./data/ngrams/top_49887_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";

        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";

        NGramMapWithPopularity ngm = new NGramMapWithPopularity(wordFile, countFile);
        WordNet wn = new WordNet(synsetFile, hyponymFile);

        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
        hns.register("hyponyms", new HyponymsHandler(ngm, wn));
//        hns.register("hypohist", new HypohistHandler(ngm, wn));


        //hns.register("history", new DummyHistoryHandler());
        //hns.register("historytext", new DummyHistoryTextHandler());
    }
}
