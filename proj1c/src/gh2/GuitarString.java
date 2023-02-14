package gh2;

// TODO: uncomment the following import once you're ready to start this portion
import deque.Deque;
import deque.ArrayDeque;
// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */

     private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int N = (int) Math.round(SR / frequency);
        // set up the buffer of a string at rest
        buffer = new ArrayDeque<Double>();
        for (int i = 0; i < N; i++) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        //
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        int N = buffer.size();
        // random numbers from -1/2 to +1/2
        for (int i = 0; i < N; i++) {
            double r = Math.random() - 0.5;
            buffer.removeLast();
            buffer.addFirst(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double ave = (buffer.removeLast() + buffer.get(buffer.size() - 1)) / 2;
        // apply decay and replace the previously first sample
        buffer.addFirst(ave * DECAY);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(buffer.size() - 1);
    }
}
