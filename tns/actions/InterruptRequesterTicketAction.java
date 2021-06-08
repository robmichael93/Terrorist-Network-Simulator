package tns.actions;
import tns.frames.*;
import tns.roles.*;
import tns.tickets.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This action interrupts the associated ticket the leader is using to
 * request a resource.  This action prevents a message being sent out by
 * the leader searching for a resource provider.
 * @author  Rob Michael and Zac Staples
 */
public class InterruptRequesterTicketAction implements Frame {
    
    /**
     * The requester of a resource.
     */
    private Role requester;
    /**
     * The provider of a resource.
     */
    private Role provider;
    
    /** 
     * Creates a new instance of SetRequestResponseFlagAction 
     * @param requester The requester of a resource.
     * @param provider The provider of a resource.
     */
    public InterruptRequesterTicketAction(Role requester, Role provider) {
        this.requester = requester;
        this.provider = provider;
    } // end InterruptRequesterTicketAction
    
    /**
     * The execute method checks to find the correct request ticket that matches
     * the provider's role (i.e. RequestArmsTicket & ArmsDealerRole), then
     * interrupts the ticket.
     */
    public void execute() {
//        System.out.println("InterruptRequesterTicketAction executing...");
        Vector tickets = ((TNSRole)requester).getTickets();
        Iterator i = tickets.iterator();
        while (i.hasNext()) {
            Ticket ticket = (Ticket)i.next();
            if (provider instanceof ArmsDealerRole && ticket instanceof RequestArmsTicket) {
//                System.out.println("Stopping RequestArmsTicket.");
                ticket.stopExecution();
            } else if (provider instanceof FinancierRole && ticket instanceof RequestFinancesTicket) {
//                System.out.println("Stopping RequestFinancesTicket.");
                ticket.stopExecution();
            } else if (provider instanceof LogisticianRole && ticket instanceof RequestLogisticsTicket) {
//                System.out.println("Stopping RequestLogisticsTicket.");
                ticket.stopExecution();
            } // end if-else
        } // end while
    } // end execute
    
} // end class InterruptRequesterTicketAction
