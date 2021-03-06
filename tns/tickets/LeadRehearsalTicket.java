package tns.tickets;
import mil.navy.nps.relate.*;
import tns.agents.*;
import tns.actions.*;
import tns.roles.*;
import tns.connectors.*;
import java.util.*;

/**
 * This ticket simply extends a connector that operators in the mission can 
 * hear, which causes the operators to execute their "rehearse mission" ticket.  
 * The leader also performs similar actions as the operators.
 * @author  Rob Michael and Zac Staples
 */
public class LeadRehearsalTicket extends Ticket {
    
    /** 
     * Creates a new instance of LeadRehearsalTicket 
     * @param role The role that owns this ticket.
     */
    public LeadRehearsalTicket(Role role) {
        super(role, "LeadRehearsalTicket");
        Vector connectors = ((TNSRole)role).getConnectors();
        Enumeration e = connectors.elements();
        while (e.hasMoreElements()) {
            Connector c = (Connector)e.nextElement();
            if (c instanceof LeadRehearsalConnector) {
                addFrame(new ExtendRetractConnectorAction(c));
            } // end if
        } // end while
        addFrame(new RehearseAction(role));
    } // end LeadRehearsalTicket constructor
    
} // end class LeadRehearsalTicket
