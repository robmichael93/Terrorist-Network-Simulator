package tns.connectors;
import mil.navy.nps.relate.*;

/**
 * Leaders extend this connector that the operators in their mission can connect 
 * with and increment their rehearsal counters, indicating progress in mission 
 * rehearsal.
 * @author  Rob Michael and Zac Staples
 */
public class LeadRehearsalConnector extends Connector {
    
    /** 
     * Creates a new instance of LeadRehearsalConnector 
     * @param role The role that owns this connector.
     */
    public LeadRehearsalConnector(Role role) {
        super(role, "LeadRehearsalConnector");
    } // end LeadRehearsalConnector constructor
    
} // end class LeadRehearsalConnector
