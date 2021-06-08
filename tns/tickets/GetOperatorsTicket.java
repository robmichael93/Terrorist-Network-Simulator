package tns.tickets;
import mil.navy.nps.relate.*;
import tns.actions.*;

/**
 * This ticket is used simply to increment a stuck counter for getting
 * operators.
 * @author  Rob Michael and Zac Staples
 */
public class GetOperatorsTicket extends Ticket {
    
    /** 
     * Creates a new instance of GetOperatorsTicket 
     * @param role The role that owns this ticket.
     */
    public GetOperatorsTicket(Role role) {
        super(role, "GetOperatorsTicket");
        addFrame(new IncrementGetOperatorsStuckCounterAction(role));
    } // end GetOperatorsTicket constructor
    
} // end class GetOperatorsTicket
