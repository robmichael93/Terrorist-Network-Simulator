package tns.tickets;
import mil.navy.nps.relate.*;
import tns.actions.*;

/**
 * This ticket gives the recruiter the ability to increase the allegiance value 
 * of recruits until they reach a threshold at which time the recruits are 
 * eligible to get trained.
 * @author  Rob Michael and Zac Staples
 */
public class RecruitVerificationTicket extends Ticket {
    
    /** 
     * Creates a new instance of RecruitVerificationTicket 
     * @param role The role that owns this ticket.
     */
    public RecruitVerificationTicket(Role role) {
        super(role, "RecruitVerificationTicket");
        addFrame(new RecruitVerificationAction(role));
    } // end RecruitVerificationTicket constructor
    
} // end class RecruitVerificationTicket
