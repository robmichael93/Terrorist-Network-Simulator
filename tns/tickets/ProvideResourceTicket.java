package tns.tickets;
import mil.navy.nps.relate.*;
import tns.actions.*;

/**
 * When a specialist hears a connector from a leader requesting a resource, the 
 * specialist looks at his current level of resource before trying to provide 
 * the resource.  The specialist provides resources to the leader up to either 
 * the amount requested by the leader or the amount the specialist has on hand, 
 * which ever is smaller.  The specialist then receives either a point of 
 * experience or influence for every point of resource that was exchanged.  The
 * specialist then sets a Boolean latch that indicates that the specialist
 * provided resources during the turn.  This latch is used in the evaluation of 
 * the produce a resource goal.
 * @author  Rob Michael and Zac Staples
 */
public class ProvideResourceTicket extends Ticket {
    
    /** 
     * Creates a new instance of ProvideResourceTicket 
     * @param requester The leader requesting a resource.
     * @param provider The specialist providing a resource.
     */
    public ProvideResourceTicket(Role requester, Role provider) {
        super(provider, "ProvideResourceTicket");
        addFrame(new ResourceExchangeAction(requester, provider, this));
        addFrame(new SetProvideLatchAction(provider));
        addFrame(new InterruptRequesterTicketAction(requester, provider));
        addFrame(new ResetStuckCounterAction(requester));
        addFrame(new ResetStuckCounterAction(provider));
    } // end ProvideResourceTicket constructor
    
    /** 
     * Creates a new instance of ProvideResourceTicket 
     * @param requester The leader requesting a resource.
     * @param provider The specialist providing a resource.
     * @param ticket The leader's ticket to be interrupted.
     */
    public ProvideResourceTicket(Role requester, Role provider, Ticket ticket) {
        super(provider, "ProvideResourceTicket");
        addFrame(new ResourceExchangeAction(requester, provider, this));
        addFrame(new SetProvideLatchAction(provider));
//        addFrame(new InterruptRequesterTicketAction(requester, provider));
        addFrame(new InterruptTicketAction(ticket));
        addFrame(new ResetStuckCounterAction(requester));
        addFrame(new ResetStuckCounterAction(provider));
    } // end ProvideResourceTicket constructor

} // end class ProvideResourceTicket
