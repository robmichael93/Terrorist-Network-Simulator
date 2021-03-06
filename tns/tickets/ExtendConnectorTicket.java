package tns.tickets;
import tns.agents.*;
import tns.actions.*;
import tns.roles.*;
import tns.connectors.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This ticket simply uses an ExtendRetractConnectorAction to extend and
 * retract a connector.
 * @author  Rob Michael and Zac Staples
 */
public class ExtendConnectorTicket extends Ticket {
    
    /**
     * The name of the connector associated with this ticket.
     */
    private String connectorName;
    
    /** 
     * Creates a new instance of ExtendConnectorTicket 
     * @param role The role that owns this ticket.
     * @param connectorName The name of the connector associated with this
     * ticket.
     */
    public ExtendConnectorTicket(Role role, String connectorName) {
        super(role, "ExtendConnectorTicket");
        this.connectorName = connectorName;
        Vector connectors = ((TNSRole)role).getConnectors();
        Enumeration e = connectors.elements();
        while (e.hasMoreElements()) {
            Connector c = (Connector)e.nextElement();
            if (c.getName().equalsIgnoreCase(connectorName)) {
                addFrame(new ExtendRetractConnectorAction(c));
            } // end if
        } // end while
    } // end ExtendConnectorTicket constructor
    
    /**
     * Returns the name of the connector associated with this ticket.
     * @return String The name of the connecetor associated with this ticket.
     */
    public String getConnectorName() { return connectorName; }
    
} // end class ExtendConnectorTicket
