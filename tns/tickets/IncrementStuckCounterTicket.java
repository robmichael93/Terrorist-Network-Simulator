package tns.tickets;
import mil.navy.nps.relate.*;
import tns.actions.*;

/**
 * This ticket simply increments a stuck counter.
 * @author  Rob Michael and Zac Staples
 */
public class IncrementStuckCounterTicket extends Ticket {
    
    /** 
     * Creates a new instance of IncrementStuckCounterTicket 
     * @param role The role that owns this ticket.
     */
    public IncrementStuckCounterTicket(Role role) {
        super(role, "IncrementStuckCounterTicket");
        addFrame(new IncrementStuckCounterAction(role));
    } // end IncrementStuckCounterTicket constructor
    
} // end class IncrementStuckCounterTicket
