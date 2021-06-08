package tns.connectors;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * Recruits extend this connector that recruiters can connect with so that 
 * recruits can get initiated and become eligible for training.
 * @author  Rob Michael and Zac Staples
 */
public class ProveAllegianceConnector extends Connector {
    
    /** 
     * Creates a new instance of ProveAllegianceConnector 
     * @param role The role that owns this connector.
     */
    public ProveAllegianceConnector(Role role) {
        super(role, "ProveAllegianceConnector");
    } // end ProveAllegianceConnector constructor
    
} // end class ProveAllegianceConnector
