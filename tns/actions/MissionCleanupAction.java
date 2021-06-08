package tns.actions;
import tns.frames.*;
import tns.tickets.*;
import tns.roles.*;
import tns.goals.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This action removes the mission rehearsal and mission execute tickets from 
 * both the operators and the leader and replaces them with new ones since they 
 * were modified when the mission was completed by the “execute a mission” 
 * action above.  The action then resets all of the goals for the operators and 
 * the leader and lastly clears out the target information for the operators 
 * and clears out the mission and target information for the leader.
 * @author  Rob Michael and Zac Staples
 */
public class MissionCleanupAction implements Frame {
    
    /**
     * The role performing the action.
     */
    private Role role;
    
    /** 
     * Creates a new instance of MissionCleanupAction 
     * @param role The role performing the action.
     */
    public MissionCleanupAction(Role role) {
        this.role = role;
    } // end MissionCleanupAction
    
    /**
     * The execute mission removes the tickets associated with executing the
     * mission, specific to either the operator or leader role.  The
     * associated goal surrounding mission accomplishment are reset so the
     * agents may go on another mission.  Lastly, the target or mission is
     * dereferenced for the operator or leader respectively.
     */
    public void execute() {
        
        if (role instanceof OperatorRole) {
            Ticket rehearseMission = null;
            Ticket executeMission = null;
            Vector tickets = ((TNSRole)role).getTickets();
            Iterator i = tickets.iterator();
            while (i.hasNext()) {
                Ticket ticket = (Ticket)i.next();
                if (ticket instanceof RehearseMissionTicket) {
                    rehearseMission = ticket;
                } else if (ticket instanceof ExecuteMissionTicket) {
                    executeMission = ticket;
                } // end if-else
            } // end while
            tickets.remove(rehearseMission);
            tickets.remove(executeMission);
            tickets.add(new RehearseMissionTicket(role));
            tickets.add(new ExecuteMissionTicket(role));
            
            Vector goals = ((TNSRole)role).getGoalListVec();
            i = goals.iterator();
            while (i.hasNext()) {
                Goal goal = (Goal)i.next();
                if (goal instanceof JoinLeaderGoal ||
                    goal instanceof RehearseMissionGoal ||
                    goal instanceof ExecuteMissionGoal) {
                    ((TNSGoal)goal).resetGoal();
                } // end if
            } // end while
            
            ((OperatorRole)role).clearTarget();
        } else if (role instanceof LeaderRole) {
            Ticket leadRehearsal = null;
            Ticket leadMission = null;
            Vector tickets = ((TNSRole)role).getTickets();
            Iterator i = tickets.iterator();
            while (i.hasNext()) {
                Ticket ticket = (Ticket)i.next();
                if (ticket instanceof LeadRehearsalTicket) {
                    leadRehearsal = ticket;
                } else if (ticket instanceof LeadMissionTicket) {
                    leadMission = ticket;
                } // end if-else
            } // end while
            tickets.remove(leadRehearsal);
            tickets.remove(leadMission);
            tickets.add(new LeadRehearsalTicket(role));
            tickets.add(new LeadMissionTicket(role));
            
            Vector goals = ((TNSRole)role).getGoalListVec();
            i = goals.iterator();
            while (i.hasNext()) {
                Goal goal = (Goal)i.next();
                ((TNSGoal)goal).resetGoal();
            } // end while
            
            ((LeaderRole)role).clearMission();
        } // end if-else
    } // end execute
    
} // end class MissionCleanupAction
