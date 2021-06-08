package tns.tickets;
import tns.agents.*;
import tns.actions.*;
import tns.roles.*;
import tns.connectors.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 *  With this ticket the recruiter extends a connector that sensed contacts 
 * could hear and then make a decision whether or not to join the organization.
 * @author  Rob Michael and Zac Staples
 */
public class FindContactsTicket extends Ticket {
    
    /** 
     * Creates a new instance of FindContactsTicket 
     * @param role The role that owns this ticket.
     */
    public FindContactsTicket(Role role) {
        super(role, "FindContactsTicket");
        Vector connectors = ((TNSRole)role).getConnectors();
        Enumeration e = connectors.elements();
        while (e.hasMoreElements()) {
            Connector c = (Connector)e.nextElement();
            if (c instanceof FindContactConnector) {
                addFrame(new ExtendRetractConnectorAction(c));
            } // end if
        } // end while
    } // end FindContactsTicket constructor
    
} // end class FindContactsTicket
