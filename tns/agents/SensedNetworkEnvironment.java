package tns.agents;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * This class represents all the agents a given agent has sensed or is
 * directly linked to.
 * @author  Rob Michael and Zac Staples
 */
public class SensedNetworkEnvironment implements SensedEnvironment {
    
    /**
     * The contain of agents a given agent has sensed.
     */
    private Vector sensedAgents;
    
    /** 
     * Creates a new instance of SensedNetworkEnvironment 
     */
    public SensedNetworkEnvironment() {
        sensedAgents = new Vector();
    } // end SensedNetworkEnvironment constructor
    
    /**
     * Clears out the container of sensed agents.
     */
    public void reset() {
        sensedAgents.clear();
    }
    
    /** Vector containing all sensed agents.  This is used by the relationshipManager
     * in checking for new or formed relationships.
     * @return  A vector containing all currently sensed agents.
     *
     */
    public Vector getSensedAgents() { return sensedAgents; }
    
    /**
     * Sets the collection of sensed agents to another collection.
     * @param agents The collection of agents to set the sensed agents
     * collection to.
     */
    public void setSensedAgents(Vector agents) {
        sensedAgents = agents;
    } // end setSensedAgents
    
    /** Adds another collection of agents to the current one.
     * @param agents The collection of agents to add to the current one.
     * @return Vector The new collection of sensed agents.
     */
    public boolean addAgents(Vector agents) {
        Enumeration e = agents.elements();
        while (e.hasMoreElements()) {
            Agent a = (Agent)e.nextElement();
            if (!sensedAgents.contains(a)) {
                sensedAgents.add(a);
            } // end if
        } // end while
        return true;
    } // end addAgents
    
    /** Returns an Object that contains all required elements of the
     * SensedEnvironment.  This method puts all of the various elements
     * of the PerceivedEnvironment into an appropriate Object using
     * getter-methods.
     * @return  A SensedEnvironment object.
     *
     */
    public SensedEnvironment getSensedEnvironment() { return this; }
    
} // end class SensedNetworkEnvironment
