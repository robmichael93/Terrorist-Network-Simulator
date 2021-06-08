package tns.actions;
import tns.frames.*;
import tns.roles.*;
import mil.navy.nps.relate.*;

/**
 * This class simply increments a "stuck" counter.
 * @author  Rob Michael and Zac Staples
 */
public class IncrementStuckCounterAction implements Frame {
    
    /**
     * The role performing the action.
     */
    private Role role;
    
    /** 
     * Creates a new instance of IncrementStuckCounterAction 
     * @param role The role performing the action.
     */
    public IncrementStuckCounterAction(Role role) {
        this.role = role;
    } // end IncrementStuckCounterAction
    
    /**
     * The execute method simply increments the stuck counter for the
     * correct role type.
     * FUTURE WORK: Should implement an interface to avoid all the if-else
     * statements.
     */
    public void execute() {
        if (role instanceof LeaderRole) {
            ((LeaderRole)role).incrementStuckCounter();
        } else if (role instanceof ArmsDealerRole) {
            ((ArmsDealerRole)role).incrementStuckCounter();
        } else if (role instanceof FinancierRole) {
            ((FinancierRole)role).incrementStuckCounter();
        } else if (role instanceof LogisticianRole) {
            ((LogisticianRole)role).incrementStuckCounter();
        } else if (role instanceof ContactRole) {
            ((ContactRole)role).incrementStuckCounter();
        } else if (role instanceof OperatorRole) {
            ((OperatorRole)role).incrementStuckCounter();
        } // end if-else
    } // end execute
    
} // end class IncrementStuckCounterAction
