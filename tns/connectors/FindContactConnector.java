package tns.connectors;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * Recruiters extend this connector so that contacts can connect with it and 
 * make a decision to join the organization or turn the offer down.
 * @author  Rob Michael and Zac Staples
 */
public class FindContactConnector extends Connector {
    
    /** 
     * Creates a new instance of FindContactConnector 
     * @param role The role that owns this connector.
     */
    public FindContactConnector(Role role) {
        super(role, "FindContactConnector");
    } // end FindContactConnector constructor
    
} // end class FindContactConnector
