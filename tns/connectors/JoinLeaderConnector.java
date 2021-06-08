package tns.connectors;
import mil.navy.nps.relate.*;
import tns.tickets.*;

/**
 * Operators extend this connector that trainers can connect with so that the 
 * trainer can introduce the operator to a leader.
 * @author  Rob Michael and Zac Staples
 */
public class JoinLeaderConnector extends Connector {
    
    /**
     * The ticket associated with this connector.
     */
    private Ticket ticket;
    
    /** 
     * Creates a new instance of JoinLeaderConnector 
     * @param role The role that owns this connector.
     */
    public JoinLeaderConnector(Role role) {
        super(role, "JoinLeaderConnector");
        ticket = null;
    } // end JoinLeaderConnector constructor
    
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
    
} // end class JoinLeaderConnector
