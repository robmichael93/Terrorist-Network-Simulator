package tns;
import tns.util.*;
import simkit.*;
import simkit.random.*;

/**
 * This class runs the simulation.
 * @author  Rob Michael and Zac Staples
 */
public class TNSMain {
    
    /** Creates a new instance of TNSMain */
    public TNSMain() {}
    
    /**
     * The main method.  It sets up the two arrival processes, one for contacts
     * and the other for recruiters.  It also sets up the simulation event
     * listeners within Simkit.  Lastly, it determines when the simulation
     * ends, resets it, and then runs it.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ContactArrivalProcess contactArrival = new ContactArrivalProcess(
                                               "simkit.random.ExponentialVariate",
                                               new Object[] {new Double(5.0)}, CongruentialSeeds.SEED[(int)(Math.random() * 10)]);
        RecruiterArrivalProcess recruiterArrival = new RecruiterArrivalProcess(
                                               "simkit.random.ExponentialVariate",
                                               new Object[] {new Double(50.0)}, CongruentialSeeds.SEED[(int)(Math.random() * 10)]);
        TNSMainSimulation simulation = new TNSMainSimulation();
        TurnTiming timing = new TurnTiming();
        contactArrival.addSimEventListener(simulation);
        recruiterArrival.addSimEventListener(simulation);
        simulation.addSimEventListener(timing);
        Schedule.setVerbose(false);
        Schedule.stopOnEvent("Turn", 500);
        Schedule.reset();
        Schedule.startSimulation();
    } // end main
    
} // end class TNSMain
