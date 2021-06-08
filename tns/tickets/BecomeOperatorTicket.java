package tns.tickets;
import mil.navy.nps.relate.*;
import tns.agents.*;
import tns.actions.*;
import tns.roles.*;
import tns.connectors.*;
import java.util.*;

/**
 * The connector in this ticket is heard by a trainer, who then trains the 
 * operator for the turn.
 * @author  Rob Michael and Zac Staples
 */
public class BecomeOperatorTicket extends Ticket {
    
    /** 
     * Creates a new instance of BecomeOperatorTicket 
     * @param role The role that owns this ticket.
     */
    public BecomeOperatorTicket(Role role) {
        super(role, "BecomeOperatorTicket");
        Vector connectors = ((TNSRole)role).getConnectors();
        Enumeration e = connectors.elements();
        while (e.hasMoreElements()) {
            Connector c = (Connector)e.nextElement();
            if (c instanceof BecomeOperatorConnector) {
                addFrame(new ExtendRetractConnectorAction(c));
            } // end if
        } // end while
    } // end BecomeOperatorTicket constructor
    
} // end class BecomeOperatorTicket
