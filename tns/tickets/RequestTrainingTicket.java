package tns.tickets;
import mil.navy.nps.relate.*;
import tns.agents.*;
import tns.actions.*;
import tns.roles.*;
import tns.connectors.*;
import tns.messages.*;

/**
 * This ticket allows a leader to find a trainer so that he can rehearse his
 * mission.
 * @author  Rob Michael and Zac Staples
 */
public class RequestTrainingTicket extends Ticket {
    
    /** 
     * Creates a new instance of RequestTrainingTicket 
     * @param role The role that owns this ticket.
     */
    public RequestTrainingTicket(Role role) {
        super(role, "RequestTrainingTicket", true);
        Agent leader = ((TNSRole)role).getAgent();
        Class[] argumentTypes = {Agent.class, String.class, String.class, int.class};
        Object[] arguments = {leader, "TrainerRole", "FindPersonTicket", new Integer(Message.FIND_PERSON_MESSAGE)};
        addFrame(new PutMessageInOutboxAction(leader, "FindPersonMessage", argumentTypes, arguments));
    } // end RequestTrainingTicket constructor
    
} // end class RequestTrainingTicket
