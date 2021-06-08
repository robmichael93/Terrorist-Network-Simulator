package tns.actions;
import tns.frames.*;
import tns.roles.*;
import mil.navy.nps.relate.*;

/**
 * This class simply increments a "stuck" counter related to finding
 * operators for a Leader.
 * @author  Rob Michael and Zac Staples
 */
public class IncrementGetOperatorsStuckCounterAction implements Frame {
    
    /**
     *  The role performing the action.
     */
    private Role role;
    
    /** 
     * Creates a new instance of IncrementGetOperatorsStuckCounterAction
     * @param role The role performing the action.
     */
    public IncrementGetOperatorsStuckCounterAction(Role role) {
        this.role = role;
    } // end IncrementGetOperatorsStuckCounterAction
    
    /**
     * Increments a Leader's stuck counter for finding operators.
     */
    public void execute() {
        ((LeaderRole)role).incrementGetOperatorsStuckCounter();
    } // end execute
    
} // end class IncrementGetOperatorsStuckCounterAction
