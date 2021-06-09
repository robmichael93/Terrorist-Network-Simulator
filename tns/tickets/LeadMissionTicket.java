package tns.tickets;
import mil.navy.nps.relate.*;
import tns.agents.*;
import tns.actions.*;
import tns.roles.*;
import tns.connectors.*;
import java.util.*;

/**
 * This ticket works like the "lead mission rehearsal" ticket, except that it is 
 * used for executing missions.
 * @author  Rob Michael and Zac Staples
 */
public class LeadMissionTicket extends Ticket {
    
    /** 
     * Creates a new instance of LeadMissionTicket 
     * @param role The role that owns this ticket.
     */
    public LeadMissionTicket(Role role) {
        super(role, "LeadMissionTicket");
        Vector connectors = ((TNSRole)role).getConnectors();
        Enumeration e = connectors.elements();
        while (e.hasMoreElements()) {
            Connector c = (Connector)e.nextElement();
            if (c instanceof LeadMissionConnector) {
                addFrame(new ExtendRetractConnectorAction(c));
            } // end if
        } // end while
        addFrame(new ExecuteAction(role));
    } // end LeadMissionTicket constructor
    
} // end class LeadMissionTicket
