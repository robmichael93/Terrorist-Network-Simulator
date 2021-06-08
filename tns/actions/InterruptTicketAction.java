package tns.actions;
import tns.frames.*;
import tns.tickets.*;

/**
 * This action simply takes a ticket and sets the stop execution flag, which
 * interrupts the ticket.
 * @author  Rob Michael and Zac Staples
 */
public class InterruptTicketAction implements Frame {
    
    /**
     * The ticket to be interrupted.
     */
    private Ticket ticket;
    
    /** 
     * Creates a new instance of InterruptTicketAction 
     * @param ticket The ticket to be interrupted.
     */
    public InterruptTicketAction(Ticket ticket) {
        this.ticket = ticket;
    } // InterruptTicketAction
    
    /**
     * The execute action simply sets the stop execution flag on the ticket.
     */
    public void execute() {
        ticket.stopExecution();
    } // end execute
    
} // end class InterruptTicketAction
