package tns.tickets;
import mil.navy.nps.relate.*;
import tns.actions.*;
import tns.goals.*;
import java.util.*;

/**
 *  This ticket creates a new target and a new mission for the leader.  This 
 * ticket is unique in that it is scheduled via a discrete event simulation 
 * mechanism discussed below so that the ticket actually takes place some number 
 * of turns later and the leader �blocks� on the plan mission goal, much the same
 * way network sockets block until they connect, until the leader creates a 
 * target and mission.  The purpose for this design decision was to allow
 * operators attached to a leader that just completed a mission the ability to
 * become mobile and potentially migrate to another leader.  If the leader did 
 * not take more than one turn to create a target and mission, the leader would 
 * always have the same operators in his missions and other leaders in the 
 * system might starve on the need for operators.  By allowing operators to
 * migrate between leaders, the system as a whole can progress further on 
 * accomplishing missions.  When the leader finishes planning a mission the 
 * �plan a mission� goal is marked complete.
 * @author  Rob Michael and Zac Staples
 */
public class PlanMissionTicket extends Ticket {
    
    /** 
     * Creates a new instance of PlanMissionTicket 
     * @param role The role that owns this ticket.
     */
    public PlanMissionTicket(Role role) {
        super(role, "PlanMissionTicket");
        addFrame(new ProduceMissionAction(role));
        Vector goals = role.getGoalListVec();
        Iterator i = goals.iterator();
        while (i.hasNext()) {
            TNSGoal g = (TNSGoal)i.next();
            if (g instanceof PlanMissionGoal) {
                addFrame(new MarkGoalCompleteAction(g));
            } // end if
        } // end while
    } // end PlanMissionTicet constructor
    
} // end class PlanMissionTicket
