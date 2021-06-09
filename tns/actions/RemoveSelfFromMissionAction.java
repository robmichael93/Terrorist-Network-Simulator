package tns.actions;
import tns.frames.*;
import tns.roles.*;
import tns.agents.*;
import tns.goals.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This action removes the operator from the mission, creating a vacancy that 
 * the leader now has to fill.  The operator's goals are all reset except for 
 * the advance goal and the target information is cleared out for the operator.
 * @author  Rob Michael and Zac Staples
 */
public class RemoveSelfFromMissionAction implements Frame {
    
    /**
     * The Operator performing the role.
     */
    private Role role;
    
    /** 
     * Creates a new instance of RemoveSelfFromMissionAction 
     * @param role The Operator performing the action.
     */
    public RemoveSelfFromMissionAction(Role role) {
        this.role = role;
    } // end RemoveSelfFromMissionAction constructor
    
    /**
     * The execute mission first checks to make sure the Operator's target is
     * not null.  If it is not, then the mission removes the Operator from it
     * and the Operator's goals related to the mission are reset.  Lastly, the
     * target is dereference from the Operator.
     */
    public void execute() {
        Target target = ((OperatorRole)role).getCurrentTarget();
        if (target != null) {
            Mission mission = target.getMission();
            Agent agent = ((TNSRole)role).getAgent();
            mission.removeOperator(agent);
            Vector goals = ((TNSRole)role).getGoalListVec();
            Iterator i = goals.iterator();
            while (i.hasNext()) {
                Goal goal = (Goal)i.next();
                if (goal instanceof JoinLeaderGoal ||
                    goal instanceof RehearseMissionGoal ||
                    goal instanceof ExecuteMissionGoal) {
                    ((TNSGoal)goal).resetGoal();
                } // end if
            } // end while
            ((OperatorRole)role).clearTarget();
        } // end if
    } // end execute
    
} // end class RemoveSelfFromMissionAction
