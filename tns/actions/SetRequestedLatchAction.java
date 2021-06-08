package tns.actions;
import tns.frames.*;
import tns.roles.*;
import mil.navy.nps.relate.*;

/**
 * This action simply sets a Boolean latch for the specialist indicating that a 
 * leader requested a resource from the specialist via a message as described 
 * above.
 * @author Rob Michael and Zac Staples
 */
public class SetRequestedLatchAction implements Frame {
    
    /**
     * The specialist performing the action.
     */
    private Role role;
    
    /** 
     * Creates a new instance of SetRequestedLatchAction 
     * @param role The specialist performing the action.
     */
    public SetRequestedLatchAction(Role role) {
        this.role = role;
    } // end SetRequestedLatchAction
    
    /**
     * The execute method simply sets the resource requested latch for the
     * specialist.
     */
    public void execute() {
//        System.out.println("SetRequestedLatchAction executing");
        if (role instanceof ArmsDealerRole) {
            ((ArmsDealerRole)role).setArmsRequestedLatch();
//            System.out.println("Requested Arms Latch: " + ((ArmsDealerRole)role).armsRequested());
        } else if (role instanceof FinancierRole) {
            ((FinancierRole)role).setFinancesRequestedLatch();
//            System.out.println("Requested Finances Latch: " + ((FinancierRole)role).financesRequested());
        } else if (role instanceof LogisticianRole) {
            ((LogisticianRole)role).setLogisticsRequestedLatch();
//            System.out.println("Requested Logistics Latch: " + ((LogisticianRole)role).logisticsRequested());
        } // end if
    } // end execute
    
} // end SetRequestedLatchAction
