package tns.connectors;
import mil.navy.nps.relate.*;
import tns.tickets.*;

/**
 * Leaders extend these types of connectors that specialists can connect with to 
 * provide the leader with needed resources for conducting a mission.  This one
 * is used for requesting finances.
 * @author  Rob Michael and Zac Staples
 */
public class RequestFinancesConnector extends Connector {
    
    /**
     * The ticket associated with this connector.
     */
    private Ticket ticket;
    
    /** 
     * Creates a new instance of RequestFinancesConnector 
     * @param role The role that owns this connector.
     */
    public RequestFinancesConnector(Role role) {
        super(role, "RequestFinancesConnector");
        ticket = null;
    } // end RequestFinancesConnector constructor
    
    /**
     * Associates a ticket with the connector.  This method is used so that
     * the receptor agent can interrupt the stimulator's ticket.
     * @param t The ticket being associated with this connector.
     */
    public void associateTicket(Ticket t) {
        ticket = t;
    } // end associateTicket
    
    /**
     * Returns the ticket associated with this connector.
     * @return Ticket The ticket associated with this connector.
     */
    public Ticket getTicket() { return ticket; }
    
} // end RequestFinancesConnector
