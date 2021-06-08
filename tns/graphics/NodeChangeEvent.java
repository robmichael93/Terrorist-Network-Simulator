package tns.graphics;
import mil.navy.nps.relate.*;

/**
 * This class encapsulates the agent whose node has changed an what the type of
 * change is.
 * @author  Rob Michael and Zac Staples
 */
public class NodeChangeEvent {
    
    /**
     * The type of change action to take.  Valid entries are "add" and "remove."
     */
    private String type;
    /**
     * The agent who's node is changing.
     */
    private Agent agent;
    
    /** 
     * Creates a new instance of NodeChangeEvent 
     * @param type The type of change action to take.
     * @param agent The agent who's node is changing.
     */
    public NodeChangeEvent(String type, Agent agent) {
        this.type = type;
        this.agent = agent;
    } // end NodeChangeEvent constructor
    
    /**
     * Returns the type of action to perform.
     * @return String The type of action to perform.
     */
    public String getChangeType() { return type; }
    
    /**
     * Returns the agent who's node is changing.
     * @return Agent The agent who's node is changing.
     */
    public Agent getAgent() { return agent; }
    
} // end class NodeChangeEvent
