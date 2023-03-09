package ngordnet.ngrams;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NGramMapWithPopularity extends NGramMap {
    public NGramMapWithPopularity(String wordsFilename, String countsFilename) {
        super(wordsFilename, countsFilename);
    }

    private class WordWithCount implements Comparable<WordWithCount> {
        private String w;
        private Double cnt;

        public WordWithCount(String s, Double c) {
            w = s;
            cnt = c;
        }

        public int compareTo(WordWithCount wwc) {
            return (int) (cnt - wwc.cnt);
        }
    }

    /** Returns the k most popular words over the given period in decreasing order of popularity. **/
    public List<String> kMostPopular(Collection<String> words, int startYear, int endYear, int k) {
        MinPQ<WordWithCount> mpq = new MinPQ<>();
        for (String s : words) {
            double count = occurences(s, startYear, endYear);
            if (count == 0) {
                continue;
            }
            mpq.insert(new WordWithCount(s, count));
            if (mpq.size() > k) {
                mpq.delMin();
            }
        }
        ArrayList<String> results = new ArrayList<>();
        while (!mpq.isEmpty()) {
            results.add(mpq.delMin().w);
        }
        return results;
    }

    /** Returns total number of occurrences in given years. */
    private double occurences(String s, int startYear, int endYear) {
        double count = 0.0;
        TimeSeries historyOfS = countHistory(s);

        for (int i = startYear; i <= endYear; i += 1) {
            if (historyOfS.containsKey(i)) {
                count += historyOfS.get(i);
            }
        }
        return count;
    }

    /** Returns total number of occurrences in given year. */
    private double occurences(String s, int year) {
        TimeSeries historyOfS = countHistory(s);
        if (historyOfS.containsKey(year)) {
            return historyOfS.get(year);
        }
        return 0.0;
    }

}