package ngordnet.ngrams;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Returns the years in which this time series is valid. Doesn't really
     * need to be a NavigableSet. */
    private NavigableSet<Integer> validYears(int startYear, int endYear) {
        return navigableKeySet().subSet(startYear, true, endYear, true);
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        Set<Integer> validYears = ts.validYears(startYear, endYear);

        for (int year : validYears) {
            put(year, ts.get(year));
        }
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries ts) {
        this(ts, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        ArrayList<Integer> a = new ArrayList<>();
        for (int year : keySet()) {
            a.add(year);
        }
        return a;
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        ArrayList<Double> a = new ArrayList<>();
        for (Double value : values()) {
            a.add(value);
        }
        return a;
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries sumTS = new TimeSeries();

        Set<Integer> myYears = keySet();
        Set<Integer> theirValidYears = ts.keySet();
        TreeSet<Integer> years = new TreeSet<>();
        years.addAll(myYears);
        years.addAll(theirValidYears);

        for (int year : years) {
            double sum = 0;
            if (this.containsKey(year)) {
                sum += this.get(year).doubleValue();
            }

            if (ts.containsKey(year)) {
                sum += ts.get(year).doubleValue();
            }
            sumTS.put(year, sum);
        }

        return sumTS;
    }

     /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
     public TimeSeries dividedBy(TimeSeries ts) {
         TimeSeries quotient = new TimeSeries();

         Set<Integer> myValidYears = keySet();
         Set<Integer> theirValidYears = ts.keySet();
         if (!theirValidYears.containsAll(myValidYears)) {
             throw new IllegalArgumentException("Divisor must have all years in dividend.");
         }

         for (int year : myValidYears) {
             double myValue = get(year).doubleValue();
             double theirValue = ts.get(year).doubleValue();
             quotient.put(year, myValue / theirValue);
         }
         return quotient;
    }
}
