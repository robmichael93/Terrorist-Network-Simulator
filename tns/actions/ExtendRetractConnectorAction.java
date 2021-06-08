package tns.actions;
import tns.frames.*;
import tns.connectors.*;
import tns.tickets.*;

/**
 * This action simply extends and then retracts a connector.
 * @author  Rob Michael and Zac Staples
 */
public class ExtendRetractConnectorAction implements Frame {
    
    /**
     * The connector to be extended.
     */
    private Connector connector;
    /**
     * The ticket that the connector is associated with.
     */
    private Ticket ticket;
    
    /** Creates a new instance of ExtendRetractConnectorAction
     * @param connector The connector to be extended.
     * @param ticket The ticket that the connector is associated with.
     */
    public ExtendRetractConnectorAction(Connector connector, Ticket ticket) {
        this.connector = connector;
        this.ticket = ticket;
    } // end ExtendRetractConnectorAction constructor
    
    /**
     * An alternate constructor that does not associate a ticket with the
     * connector.
     * @param connector The connector to be extended.
     */
    public ExtendRetractConnectorAction(Connector connector) {
        this(connector, null);
    } // end ExtendRectractConnectorAction constructor
    
    /**
     * This action simply extends and then retracts a connector.
     */
    public void execute() {
        connector.extendConnector();
        connector.retractConnector();
    } // end execute
    
    /**
     * Returns the associated ticket.
     * @return Ticket The ticket that the connector is associated with.
     */
    public Ticket getTicket() { return ticket; }
    
} // end class ExtendRetractConnectorAction
