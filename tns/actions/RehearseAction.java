package tns.actions;
import tns.frames.*;
import tns.agents.*;
import tns.roles.*;
import tns.goals.*;
import tns.relationships.*;
import tns.tickets.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This action increments a mission rehearsal counter.  If the counter exceeds 
 * the required number of rehearsal turns for the target, then the mission 
 * rehearsal goal is marked complete.
 * @author  Rob Michael and Zac Staples
 */
public class RehearseAction implements Frame {
    
    /**
     * The role performing the action.
     */
    private Role role;
    
    /**
     * Creates a new instance of RehearseAction 
     * @param role The role performing the action.
     */
    public RehearseAction(Role role) {
        this.role = role;
    } // end RehearseAction constructor
    
    /**
     * The execute method increments the number of rehearse turns for the agent
     * in the mission (information kept in the agent's role(s)).  If the number
     * of rehearse turns exceeds the required turns for the mission, then
     * a frame is added to mark the mission rehearsal related goal complete.
     */
    public void execute() {
        if (role instanceof OperatorRole) {
            ((OperatorRole)role).incrementRehearseTurns();
//            System.out.println(((TNSRole)role).getAgent().getEntityName() + " rehearsed one turn.  Total rehearse turns is " + ((OperatorRole)role).getRehearseTurns());
            // Get the number of turns the Operator needs to rehearse for the mission
            int threshold = ((OperatorRole)role).getCurrentTarget().getRehearseTime();
            if (((OperatorRole)role).getRehearseTurns() >= threshold) {
//                System.out.println(((TNSRole)role).getAgent().getEntityName() + " finished rehearsing!");
                Vector tickets = ((TNSRole)role).getTickets();
                Enumeration e = tickets.elements();
                while (e.hasMoreElements()) {
                    Ticket t = (Ticket)e.nextElement();
                    if (t instanceof RehearseMissionTicket) {
                        TerroristAgent ta = (TerroristAgent)((TNSRole)role).getAgent();
                        t.addFrame(new MarkGoalCompleteAction((TNSGoal)ta.getGoalList().get("RehearseMissionGoal")));
                    } // end if
                } // end while
            } // end if
        } else if (role instanceof LeaderRole) {
            ((LeaderRole)role).getMission().incrementRehearseTurns();
//            System.out.println(((TNSRole)role).getAgent().getEntityName() + " rehearsed one turn.  Total rehearse turns is " + ((LeaderRole)role).getMission().getRehearseTurns());
            // Get the number of turns the Operator needs to rehearse for the mission
            if (((LeaderRole)role).getMission().rehearsed()) {
//                System.out.println(((TNSRole)role).getAgent().getEntityName() + " finished rehearsing!");
                Vector tickets = ((TNSRole)role).getTickets();
                Enumeration e = tickets.elements();
                while (e.hasMoreElements()) {
                    Ticket t = (Ticket)e.nextElement();
                    if (t instanceof LeadRehearsalTicket) {
                        TerroristAgent ta = (TerroristAgent)((TNSRole)role).getAgent();
                        t.addFrame(new MarkGoalCompleteAction((TNSGoal)ta.getGoalList().get("LeadRehearsalGoal")));
                    } // end if
                } // end while
            } // end if
        } // end if-else
    } // end execute
    
} // end class RehearseAction
