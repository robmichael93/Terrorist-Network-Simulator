package tns.tickets;
import tns.actions.*;
import mil.navy.nps.relate.*;

/**
 * Like the “rehearse a mission” ticket, this ticket increases a counter for the 
 * number of turns the operator has executed a mission.  When the counter 
 * reaches the required number of turns, then the mission execution goal is 
 * marked complete, the operator receives experience and influence from the 
 * mission, all of his goals are reset, and his current target is cleared out 
 * so he can join a new mission.
 * @author  Rob Michael and Zac Staples
 */
public class ExecuteMissionTicket extends Ticket {
    
    /** 
     * Creates a new instance of ExecuteMissionTicket 
     * @param role The role that owns this ticket.
     */
    public ExecuteMissionTicket(Role role) {
        super(role, "ExecuteMissionTicket");
        addFrame(new ExecuteAction(role));
    } // end ExecuteMissionTicket constructor
    
} // end class ExecuteMissionTicket
