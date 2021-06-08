package tns.tickets;
import mil.navy.nps.relate.*;
import tns.agents.*;
import tns.roles.*;
import tns.actions.*;
import tns.goals.*;
import java.util.*;

/**
 * Like the prove allegiance ticket, the recruit uses this ticket to extend a 
 * connector for the recruiter so the recruit can get in touch with a trainer.
 * @author  Rob Michael and Zac Staples
 */
public class GetTrainedTicket extends Ticket {
    
    /** 
     * Creates a new instance of GetTrainedTicket 
     * @param role The role that owns this ticket.
     */
    public GetTrainedTicket(Role role) {
        super(role, "GetTrainedTicket");
        TerroristAgent ta = (TerroristAgent)((TNSRole)role).getAgent();
        addFrame(new MakeDoubleLinkAction(ta));
        Vector goals = role.getGoalListVec();
        Enumeration e = goals.elements();
        while (e.hasMoreElements()) {
            TNSGoal g = (TNSGoal)e.nextElement();
            if (g instanceof MakeContactGoal) {
                addFrame(new MarkGoalCompleteAction(g));
            } // end if
        } // end while
    } // end GetTrainedTicket constructor
    
} // end class GetTrainedTicket
