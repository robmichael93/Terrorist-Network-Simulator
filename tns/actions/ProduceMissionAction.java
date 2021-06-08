package tns.actions;
import mil.navy.nps.relate.*;
import tns.frames.*;
import tns.agents.*;
import tns.roles.*;

/**
 * This action creates a target and then creates a mission, associating the 
 * target with the mission.
 * @author  Rob Michael and Zac Staples
 */
public class ProduceMissionAction implements Frame {
    
    /**
     * The role performing the action.
     */
    private Role role;
    
    /** 
     * Creates a new instance of ProduceTargetAction 
     * @param role The role performing the action.
     */
    public ProduceMissionAction(Role role) {
        this.role = role;
    } // end ProduceMissionAction contructor
    
    /**
     * The execute method creates a new target based on characteristics of the
     * leader role who will own the target.  A new mission is then created and
     * the target is associated with the mission.  Lastly, the mission is
     * assigned to the leader.
     */
    public void execute() {
        Target newTarget = new Target(role);
        Mission newMission = new Mission(newTarget);
        ((LeaderRole)role).assignMission(newMission);
    } // end execute
    
} // end class ProduceMissionAction
