package tns.tickets;
import mil.navy.nps.relate.*;
import tns.agents.*;
import tns.actions.*;
import tns.roles.*;
import tns.goals.*;
import java.util.*;

/**
 * The next step that a contact takes is to change roles to a recruit and mark 
 * the "become a recruit goal" complete.
 * @author  Rob Michael and Zac Staples
 */
public class BecomeRecruitTicket extends Ticket {
    
    /** 
     * Creates a new instance of BecomeRecruitTicket 
     * @param role The role that owns this ticket.
     */
    public BecomeRecruitTicket(Role role) {
        super(role, "BecomeRecruitTicket");
        Goal completedGoal = null;
        Vector goals = role.getGoalListVec();
        Iterator i = goals.iterator();
        while (i.hasNext()) {
            Goal goal = (Goal)i.next();
            if (goal instanceof BecomeRecruitGoal) {
                completedGoal = goal;
            } // end if
        } // end while
        addFrame(new MarkGoalCompleteAction((TNSGoal)completedGoal));
        addFrame(new ChangeRoleAction(role, "RecruitRole"));
    } // end BecomeRecruitTicket constructor
    
} // end class BecomeRecruitTicket
