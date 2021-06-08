package tns.tickets;
import mil.navy.nps.relate.*;
import tns.agents.*;
import tns.roles.*;
import tns.actions.*;
import tns.goals.*;
import java.util.*;

/**
 *  When a recruiter recruits a contact, the contact creates a permanent 
 * connector to the recruiter, or a link in the network, and the recruiter does 
 * the same with the contact.  They both also add each the other person to their
 * mental maps.  The contact then marks the make contact goal complete.
 * @author  Rob Michael and Zac Staples
 */
public class MakeContactTicket extends Ticket {
    
    /** 
     * Creates a new instance of MakeContactTicket 
     * @param role The role that owns this ticket.
     */
    public MakeContactTicket(Role role) {
        super(role, "MakeContactTicket");
        TerroristAgent ta = (TerroristAgent)((TNSRole)role).getAgent();
        addFrame(new ResetStuckCounterAction(role));
        addFrame(new MakeDoubleLinkAction(ta));
        Vector goals = role.getGoalListVec();
        Enumeration e = goals.elements();
        while (e.hasMoreElements()) {
            TNSGoal g = (TNSGoal)e.nextElement();
            if (g instanceof MakeContactGoal) {
                addFrame(new MarkGoalCompleteAction(g));
            } // end if
        } // end while
    } // end MakeContactTicket constructor
    
    /** 
     * Creates a new instance of MakeContactTicket 
     * @param role The role that owns this ticket.
     * @param agent The recruiter agent to make contact with.
     */
    public MakeContactTicket(Role role, Agent agent) {
        super(role, "MakeContactTicket");
        TerroristAgent ta = (TerroristAgent)((TNSRole)role).getAgent();
        addFrame(new ResetStuckCounterAction(role));
        addFrame(new MakeDoubleLinkAction(ta, (TerroristAgent)agent));
        Vector goals = role.getGoalListVec();
        Enumeration e = goals.elements();
        while (e.hasMoreElements()) {
            TNSGoal g = (TNSGoal)e.nextElement();
            if (g instanceof MakeContactGoal) {
                addFrame(new MarkGoalCompleteAction(g));
            } // end if
        } // end while
    } // end MakeContactTicket
    
} // end class MakeContactTicket
