package tns.util;
import simkit.*;
/**
 * A simple class to introduce delay in the visual rendering of a discrete
 * event simulation.
 * @author  Rob Michael and Zac Staples
 */
public class TurnTiming extends SimEntityBase {
    
    /**
     * A thread object that sleep() can be called on to create the delay.
     */
    private Thread thread;
    /** Creates a new instance of TurnTiming */
    public TurnTiming() {
        thread = new Thread();
    } // end TurnTiming constructor
    
    /**
     * Simply calls thread.sleep() for a specified delay period.
     */
    public void doTurn() {
        try {
            thread.sleep(1500);
        } catch (InterruptedException ie) {
            System.out.println("InterruptedException: " + ie.toString());
        } // end try-catch
    } // end doTurn

} // end class TurnTiming
