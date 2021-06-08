package tns.actions;
import tns.frames.*;
import tns.roles.*;
import mil.navy.nps.relate.*;

/**
 * This action simply resets the stuck counter for the agent.
 * @author  Rob Michael and Zac Staples
 */
public class ResetStuckCounterAction implements Frame {
    
    /**
     * The role performing the action.
     */
    private Role role;
    
    /**
     * Creates a new instance of ResetStuckCounterAction 
     * @param role The role performing the action.
     */
    public ResetStuckCounterAction(Role role) {
        this.role = role;
    } // end ResetStuckCounterAction constructor
    
    /**
     * The execute method simply resets the stuck counter for the agent.
     */
    public void execute() {
        if (role instanceof LeaderRole) {
            ((LeaderRole)role).resetStuckCounter();
        } else if (role instanceof ArmsDealerRole) {
            ((ArmsDealerRole)role).resetStuckCounter();
        } else if (role instanceof FinancierRole) {
            ((FinancierRole)role).resetStuckCounter();
        } else if (role instanceof LogisticianRole) {
            ((LogisticianRole)role).resetStuckCounter();
        } else if (role instanceof ContactRole) {
            ((ContactRole)role).resetStuckCounter();
        } else if (role instanceof OperatorRole) {
            ((OperatorRole)role).resetStuckCounter();
        } // end if-else
    } // end execute
    
} // end class ResetStuckCounterAction
