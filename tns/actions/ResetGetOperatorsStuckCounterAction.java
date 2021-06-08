package tns.actions;
import tns.frames.*;
import tns.roles.*;
import mil.navy.nps.relate.*;

/**
 * This class simply resets a "stuck" counter related to finding
 * operators for a Leader.
 * @author  Rob Michael and Zac Staples
 */
public class ResetGetOperatorsStuckCounterAction implements Frame {
    
    /**
     * The Leader performing the action.
     */
    private Role role;
    
    /** 
     * Creates a new instance of ResetGetOperatorsStuckCounterAction 
     * @param role The Leader performing the action.
     */
    public ResetGetOperatorsStuckCounterAction(Role role) {
        this.role = role;
    } // end ResetOperatorsStuckCounterAction constructor
    
    /**
     * The execute method simply resets the Leader's "get operators" stuck
     * counter.
     */
    public void execute() {
        ((LeaderRole)role).resetGetOperatorsStuckCounter();
    } // end execute
    
} // end class ResetGetOperatorsStuckCounterAction
