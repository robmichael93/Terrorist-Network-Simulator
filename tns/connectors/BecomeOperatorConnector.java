package tns.connectors;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * Recruits extend this connector that trainers can connect with and provide 
 * training to the recruits.
 * @author  Rob Michael and Zac Staples
 */
public class BecomeOperatorConnector extends Connector {
    
    /** 
     * Creates a new instance of BecomeOperatorConnector 
     * @param role The role that owns the connector.
     */
    public BecomeOperatorConnector(Role role) {
        super(role, "BecomeOperatorConnector");
    } // end BecomeOperatorConnector constructor
    
} // end class BecomeOperatorConnector
