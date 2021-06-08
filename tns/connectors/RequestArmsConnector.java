package tns.connectors;
import mil.navy.nps.relate.*;
import tns.tickets.*;

/**
 * Leaders extend these types of connectors that specialists can connect with to
 * provide the leader with needed resources for conducting a mission.  This one
 * is used for requesting arms.
 * @author  Rob Michael and Zac Staples
 */
public class RequestArmsConnector extends Connector {
    
    /**
     * The ticket associated with this connector.
     */
    private Ticket ticket;
    
    /** 
     * Creates a new instance of RequestArmsConnector 
     * @param role The role that owns this connector.
     */
    public RequestArmsConnector(Role role) {
        super(role, "RequestArmsConnector");
        ticket = null;
    } // end RequestArmsConnector constructor
        
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
    
} // end class RequestArmsConnector
