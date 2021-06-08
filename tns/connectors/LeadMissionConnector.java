package tns.connectors;
import mil.navy.nps.relate.*;

/**
 * Leaders extend this connector that the operators in their mission can connect 
 * with and increment their execute counters, indicating progress in mission 
 * execution.
 * @author  Rob Michael and Zac Staples
 */
public class LeadMissionConnector extends Connector {
    
    /** 
     * Creates a new instance of LeadMissionConnector 
     * @param role The role that owns this connector.
     */
    public LeadMissionConnector(Role role) {
        super(role, "LeadMissionConnector");
    } // end LeadMissionConnector constructor
    
} // end class LeadMissionConnector
