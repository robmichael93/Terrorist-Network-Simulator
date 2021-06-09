package tns.tickets;
import mil.navy.nps.relate.*;
import tns.agents.*;
import tns.actions.*;
import tns.roles.*;
import tns.connectors.*;
import tns.messages.*;
import java.util.*;

/**
 * The tickets for requesting resources (the leader has one for each type of 
 * resource the specialists provide) increments the leader's stuck counter for 
 * requesting resources.  If the leader does not have all the operators needed 
 * for a mission, the stuck counter for getting operators is incremented as 
 * well.  The reason for incrementing the counter for getting operators is if
 * the leader is just starting out and his goal for getting operators has a low 
 * weight, but the leader does not know any specialists, he'll never satisfy his
 * request resource goals and the leader will remain stuck on those goals.  
 * Therefore, by incrementing the counter for getting operators allows the 
 * leader to find a source of operators and the leader can promote operators to 
 * specialists, therefore the leader can grow a network.  The other counter is 
 * used to model the leader's impatience with waiting for a specialist to 
 * provide a resource or for the leader to get in touch with a specialist that 
 * has a needed resource.  This counter is used to weight the "convert an 
 * operator to a specialist" goal, which in turn allows the leader to turn an 
 * operator into the needed specialist and the leader can progress along on the
 * mission, albeit at probably a slower rate since the newly converted 
 * specialist will likely been unskilled.  This type of ticket is also 
 * interruptible.  The leader extends a connector specific to the type of 
 * resource requested and if a corresponding specialist hears the connector, 
 * then the ticket is interrupted, preventing the get resource message from 
 * being placed in the leader's outbox.
 * @author  Rob Michael and Zac Staples
 */
public class RequestLogisticsTicket extends Ticket {
    
    /** 
     * Creates a new instance of RequestLogisticsTicket 
     * @param role The role that owns this ticket.
     */
    public RequestLogisticsTicket(Role role) {
        super(role, "RequestLogisticsTicket", true);

        addFrame(new IncrementStuckCounterAction(role));
        Mission mission = ((LeaderRole)role).getMission();
        if (!mission.hasNeededOperators()) {
            addFrame(new IncrementGetOperatorsStuckCounterAction(role));
        } // end if
        Vector connectors = ((TNSRole)role).getConnectors();
        Enumeration e = connectors.elements();
        while (e.hasMoreElements()) {
            Connector c = (Connector)e.nextElement();
            if (c instanceof RequestLogisticsConnector) {
                ((RequestLogisticsConnector)c).associateTicket(this);
                addFrame(new ExtendRetractConnectorAction(c));
            } // end if
        } // end while
        Agent leader = ((TNSRole)role).getAgent();
        Class[] argumentTypes = {Agent.class, String.class, String.class, int.class};
        Object[] arguments = {leader, "LogisticianRole", "GetResourceTicket", new Integer(Message.GET_RESOURCE_MESSAGE)};
        addFrame(new PutMessageInOutboxAction(leader, "GetResourceMessage", argumentTypes, arguments));
    } // end RequestLogisticsTicket constructor
    
} // end class RequestLogisticsTicket
