package tns.connectors;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * Recruits extend this connector that recruiters can connect with so that the 
 * recruiter can introduce recruits to a trainer.
 * @author  Rob Michael and Zac Staples
 */
public class GetTrainedConnector extends Connector {
    
    /** 
     * Creates a new instance of GetTrainedConnector 
     * @param role The role that owns this connector.
     */
    public GetTrainedConnector(Role role) {
        super(role, "GetTrainedConnector");
    } // end GetTrainedConnector constructor
    
} // end class GetTrainedConnector
