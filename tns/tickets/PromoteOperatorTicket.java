package tns.tickets;
import tns.actions.*;
import mil.navy.nps.relate.*;

/**
 *  This ticket allows the leader to convert his most experienced operator into 
 * a specialist to satisfy an outstanding resource requirement.  The leader 
 * finds the most experienced operator and then extends a connector that the 
 * operator can hear, resulting in the operator changing roles to the desired 
 * specialist.  The ticket also resets the stuck counter for requesting a 
 * resource.
 * @author  Rob Michael and Zac Staples
 */
public class PromoteOperatorTicket extends Ticket {
    
    /** 
     * Creates a new instance of PromoteOperatorTicket 
     * @param role The role that owns this ticket.
     * @param newRole The name of the role the operator is being promoted to.
     */
    public PromoteOperatorTicket(Role role, String newRole) {
        super(role, "PromoteOperatorTicket");
        addFrame(new RemoveSelfFromMissionAction(role));
        addFrame(new ChangeRoleAction(role, newRole));
    } // end PromoteOperatorTicket constructor
    
} // end PromoteOperatorTicket
