package tns.actions;
import tns.frames.*;
import tns.goals.*;
import tns.roles.*;
import tns.agents.*;
import mil.navy.nps.relate.*;

/**
 * This goal does exactly what its name suggests.
 * @author  Rob Michael and Zac Staples
 */
public class MarkGoalCompleteAction implements Frame {
    
    /**
     * The goal to be marked complete.
     */
    private TNSGoal goal;
    
    /** 
     * Creates a new instance of MarkGoalCompleteAction 
     * @param goal The goal to be marked complete.
     */
    public MarkGoalCompleteAction(TNSGoal goal) {
        this.goal = goal;
    } // end MarkGoalCompleteAction constructor
    
    /**
     * The execute method simply sets the completed flag for the goal and then
     * notifies any of the agent's state change listeners of the change.
     */
    public void execute() {
        goal.markGoalComplete();
        TerroristAgent ta = (TerroristAgent)((TNSRole)((TNSGoal)goal).getRole()).getAgent();
        ta.changeState(ta);
    } // end execute
    
} // end class MarkGoalCompleteAction
