package tns.tickets;
import tns.actions.*;
import mil.navy.nps.relate.*;

/**
 * This ticket works like the recruiter's "verify recruit's allegiance" ticket in 
 * that the trainer gives each recruit he is training a point of experience. 
 * If the recruit's experience exceeds a threshold, then the trainer turns the 
 * recruit into an operator.
 * @author  Rob Michael and Zac Staples
 */
public class TrainRecruitsTicket extends Ticket {
    
    /** 
     * Creates a new instance of TrainRecruitsTicket 
     * @param role The role that owns this ticket.
     */
    public TrainRecruitsTicket(Role role) {
        super(role, "TrainRecruitsTicket");
        addFrame(new TrainRecruitsAction(role));
    } // end TrainRecruitsTicket constructor
    
} // end class TrainRecruitsTicket
