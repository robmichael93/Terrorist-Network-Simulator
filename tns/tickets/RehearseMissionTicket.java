package tns.tickets;
import tns.actions.*;
import mil.navy.nps.relate.*;

/**
 * This ticket increases a counter for the number of turns the operator has 
 * rehearsed a mission.  If the counter reaches the required number of turns 
 * then the rehearse mission goal is marked complete.
 * @author  Rob Michael and Zac Staples
 */
public class RehearseMissionTicket extends Ticket {
    
    /** 
     * Creates a new instance of RehearseMissionTicket 
     * @param role The role that owns this ticket.
     */
    public RehearseMissionTicket(Role role) {
        super(role, "RehearseMissionTicket");
        addFrame(new RehearseAction(role));
    } // end RehearseMissionTicket constructor
    
} // end class RehearseMissionTicket
