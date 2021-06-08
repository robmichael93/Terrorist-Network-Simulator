package tns.actions;
import tns.frames.*;
import tns.roles.*;
import mil.navy.nps.relate.*;

/**
 * This action simply sets a Boolean latch for the specialist indicating that 
 * the specialist did provide resources during the turn.
 * @author  Rob Michael and Zac Staples
 */
public class SetProvideLatchAction implements Frame {
    
    /**
     * The specialist performing the action.
     */
    private Role role;
    
    /** 
     * Creates a new instance of SetProvideLatchAction 
     * @param role The specialist performing the action.
     */
    public SetProvideLatchAction(Role role) {
        this.role = role;
    } // end setProvideLatchAction
    
    /**
     * The execute method simlpy sets the specialist's provide resource latch.
     */
    public void execute() {
//        System.out.println("SetProvideLatchAction executing");
        if (role instanceof ArmsDealerRole) {
            ((ArmsDealerRole)role).setProvidedArmsLatch();
//            System.out.println("Provided Arms Latch: " + ((ArmsDealerRole)role).providedArms());
        } else if (role instanceof FinancierRole) {
            ((FinancierRole)role).setProvidedFinancesLatch();
//            System.out.println("Provided Finances Latch: " + ((FinancierRole)role).providedFinances());
        } else if (role instanceof LogisticianRole) {
            ((LogisticianRole)role).setProvidedLogisticsLatch();
//            System.out.println("Provided Logistics Latch: " + ((LogisticianRole)role).providedLogistics());
        } // end if
    } // end execute
    
} // end class SetProvideLatchAction
