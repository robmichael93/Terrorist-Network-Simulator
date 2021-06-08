package tns.actions;
import tns.frames.*;
import tns.roles.*;
import tns.tickets.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This class interrupts an Operator's JoinLeaderTicket
 * @author  Rob Michael and Zac Staples
 */
public class InterruptJoinLeaderTicketAction implements Frame {
    
    /**
     * The role performing the action.
     */
    private Role operator;
    
    /** 
     * Creates a new instance of InterruptJoinLeaderTicketAction 
     * @param operator The operator role performing the action.
     */
    public InterruptJoinLeaderTicketAction(Role operator) {
        this.operator = operator;
    } // end InterruptJoinLederTicketAction constructor
    
    /**
     * The execute method simply finds the JoinLeaderTicket and sets the
     * stop execution flag for it.
     */
    public void execute() {
//        System.out.println("InterruptJoinLeaderTicketAction executing...");
        Vector tickets = ((TNSRole)operator).getTickets();
        Iterator i = tickets.iterator();
        while (i.hasNext()) {
            Ticket ticket = (Ticket)i.next();
            if (ticket instanceof JoinLeaderTicket) {
//                System.out.println("Stopping JoinLeaderTicket.");
                ticket.stopExecution();
            } // end if-else
        } // end while
    } // end execute
    
} // end class InterruptJoinLeaderTicketAction
