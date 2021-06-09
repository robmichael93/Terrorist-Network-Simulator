package tns.tickets;
import mil.navy.nps.relate.*;
import tns.roles.*;
import tns.actions.*;
import tns.messages.*;
import tns.connectors.*;
import tns.agents.*;
import java.util.*;

/**
 * This ticket works exactly like the "find recruiters" ticket, except that it 
 * is designed for connecting to trainers.
 * @author  Rob Michael and Zac Staples
 */
public class FindTrainerTicket extends Ticket {
    
    /** 
     * Creates a new instance of FindTrainerTicket 
     * @param role The role that owns this ticket.
     */
    public FindTrainerTicket(Role role) {
        super(role, "FindTrainerTicket", true);
        boolean trainerFound = false;
        Vector directlyLinkedAgents = ((TerroristAgent)((TNSRole)role).getAgent()).getMentalMap().getDirectlyLinkedAgents();
        Iterator i = directlyLinkedAgents.iterator();
        while (i.hasNext() && !trainerFound) {
            Agent agent = (Agent)i.next();
            Vector roles = agent.getRoleVector();
            Iterator i2 = roles.iterator();
            while (i2.hasNext()) {
                Role r = (Role)i2.next();
                if (r instanceof TrainerRole) {
                    trainerFound = true;
                } // end if
            } // end while
        } // end while
        if (!trainerFound) {
            Vector connectors = ((TNSRole)role).getConnectors();
            Enumeration e = connectors.elements();
            while (e.hasMoreElements()) {
                Connector c = (Connector)e.nextElement();
                if (c instanceof FindTrainerConnector) {
                    ((FindTrainerConnector)c).associateTicket(this);
                    addFrame(new ExtendRetractConnectorAction(c));
                } // end if-else
            } // end while
        Agent leader = ((TNSRole)role).getAgent();
        Class[] argumentTypes = {Agent.class, String.class, String.class, int.class};
        Object[] arguments = {leader, "TrainerRole", "FindPersonTicket", new Integer(Message.FIND_PERSON_MESSAGE)};
        addFrame(new PutMessageInOutboxAction(leader, "FindPersonMessage", argumentTypes, arguments));
        } // end if
    } // end FindTrainerTicket constructor
    
} // end class FindTrainerTicket
