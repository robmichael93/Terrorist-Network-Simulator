package tns.tickets;
import mil.navy.nps.relate.*;
import tns.roles.*;
import tns.actions.*;
import tns.messages.*;
import tns.connectors.*;
import tns.agents.*;
import java.util.*;

/**
 * This ticket works similarly to the "join a leader on a mission" ticket.  If 
 * the leader does not know a recruiter, then the leader extends a connector 
 * that sensed recruiters can hear and puts a find person message for a 
 * recruiter role in the outbox unless a recruiter hears the connector and 
 * interrupts the ticket.  The purpose of this ticket is to put the leader in
 * contact with the source of new recruits in the organization. 
 * @author  Rob Michael and Zac Staples
 */
public class FindRecruiterTicket extends Ticket {
    
    /** 
     * Creates a new instance of FindRecruiterTicket 
     * @param role The role that owns this ticket.
     */
    public FindRecruiterTicket(Role role) {
        super(role, "FindRecruiterTicket", true);
        boolean recruiterFound = false;
        Vector directlyLinkedAgents = ((TerroristAgent)((TNSRole)role).getAgent()).getMentalMap().getDirectlyLinkedAgents();
        Iterator i = directlyLinkedAgents.iterator();
        while (i.hasNext() && !recruiterFound) {
            Agent agent = (Agent)i.next();
            Vector roles = agent.getRoleVector();
            Iterator i2 = roles.iterator();
            while (i2.hasNext()) {
                Role r = (Role)i2.next();
                if (r instanceof RecruiterRole) {
                    recruiterFound = true;
                } // end if
            } // end while
        } // end while
        if (!recruiterFound) {
            Vector connectors = ((TNSRole)role).getConnectors();
            Enumeration e = connectors.elements();
            while (e.hasMoreElements()) {
                Connector c = (Connector)e.nextElement();
                if (c instanceof FindRecruiterConnector) {
                    ((FindRecruiterConnector)c).associateTicket(this);
                    addFrame(new ExtendRetractConnectorAction(c));
                } // end if-else
            } // end while
        Agent leader = ((TNSRole)role).getAgent();
        Class[] argumentTypes = {Agent.class, String.class, String.class, int.class};
        Object[] arguments = {leader, "RecruiterRole", "FindPersonTicket", new Integer(Message.FIND_PERSON_MESSAGE)};
        addFrame(new PutMessageInOutboxAction(leader, "FindPersonMessage", argumentTypes, arguments));
        } // end if
    } // end FindRecruiterTicket constructor
    
} // end class FindRecruiterTicket
