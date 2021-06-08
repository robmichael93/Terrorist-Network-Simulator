package tns.connectors;
import mil.navy.nps.relate.*;
import tns.tickets.*;

/**
 * Leaders extend this connector like the connectors that recruiters use to 
 * find contacts, but leaders use this connector to find recruiters so that they
 * can obtain a source of operators for their missions.
 * @author  Rob Michael and Zac Staples
 */
public class FindRecruiterConnector extends Connector {
    
    /**
     * The ticket associated with this connector.
     */
    private Ticket ticket;
    
    /** 
     * Creates a new instance of FindRecruitersConnector 
     * @param role The role that owns this connector.
     */
    public FindRecruiterConnector(Role role) {
        super(role, "FindRecruiterConnector");
        ticket = null;
    } // end FindRecruiterConnector constructor
    
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
    
} // end class FindRecruiterConnector
