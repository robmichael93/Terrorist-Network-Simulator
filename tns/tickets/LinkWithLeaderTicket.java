package tns.tickets;
import tns.tickets.*;
import tns.actions.*;
import tns.agents.*;
import tns.roles.*;
import mil.navy.nps.relate.*;

/**
 * This ticket allows recruiters and trainers to connect to leaders so that
 * leaders can get operators.
 * @author  Rob Michael and Zac Staples
 */
public class LinkWithLeaderTicket extends Ticket {
    
    /** 
     * Creates a new instance of LinkWithLeaderTicket 
     * @param role The role that owns this ticket.
     * @param agent The leader agent to link with.
     * @param ticket The leader's ticket to interrupt.
     */
    public LinkWithLeaderTicket(Role role, Agent agent, Ticket ticket) {
        super(role, "LinkWithLeaderTicket");
        TerroristAgent ta = (TerroristAgent)((TNSRole)role).getAgent();
        addFrame(new MakeDoubleLinkAction(ta, (TerroristAgent)agent));
        addFrame(new InterruptTicketAction(ticket));
    } // end LinkWithLeaderTicket constructor
    
} // end class LinkWithLeaderTicket
