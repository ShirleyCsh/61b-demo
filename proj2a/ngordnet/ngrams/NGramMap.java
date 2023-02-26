package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    private HashMap<String, TimeSeries> wordHistories;

    private TimeSeries totalCounts;

    public NGramMap(String wordsFilename, String countsFilename) {
        In in = new In(wordsFilename);
        wordHistories = new HashMap<String, TimeSeries>();
        totalCounts = new TimeSeries();

        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] tokens = line.split("\t");
            String word = tokens[0];
            int year = Integer.parseInt(tokens[1]);
            double count = (double) Integer.parseInt(tokens[2]);

            if (!wordHistories.containsKey(word)) {
                TimeSeries wordHistory =
                        new TimeSeries();
                wordHistories.put(word, wordHistory);
            }

            TimeSeries wordHistory = wordHistories.get(word);
            wordHistory.put(year, count);
        }

        in = new In(countsFilename);
        totalCounts = new TimeSeries();

        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            int year = Integer.parseInt(tokens[0]);
            Double count = (double) Long.parseLong(tokens[1]);
            totalCounts.put(year, count);
        }

    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     * changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries countHistory = wordHistories.get(word);

        return new TimeSeries(countHistory, startYear, endYear);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalCounts);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries wordHistory = wordHistories.get(word);
        TimeSeries weightHistory = wordHistory.dividedBy(totalCounts);

        return new TimeSeries(weightHistory, startYear, endYear);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summedWeight = new TimeSeries();

        for (String word : words) {
            if (!wordHistories.containsKey(word)) {
                continue;
            }

            TimeSeries thisWord = weightHistory(word, startYear, endYear);
            summedWeight = summedWeight.plus(thisWord);
        }

        return summedWeight;
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

    /** Returns the k most popular words over the given period. **/
    public List<String> kMostPopular(int startYear, int endYear, int k) {
        MinPQ<WordWithCount> mpq = new MinPQ<>();
        for (String s : wordHistories.keySet()) {
            Double count = occurences(s, startYear, endYear);
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

    /** Returns the k most popular words over the given period. **/
    public List<String> kMostPopular(Collection<String> words, int startYear, int endYear, int k) {
        MinPQ<WordWithCount> mpq = new MinPQ<>();
        for (String s : words) {
            Double count = occurences(s, startYear, endYear);
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
        for (int i = startYear; i <= endYear; i += 1) {
            count += occurences(s, i);
        }
        return count;
    }

    /** Returns total number of occurrences in given year. */
    private double occurences(String s, int year) {
        double count = 0.0;
        if (wordHistories.containsKey(s)) {
            TimeSeries recordsForS = wordHistories.get(s);
            if (recordsForS.containsKey(year)) {
                return recordsForS.get(year);
            }
        }

        return count;
    }


}
