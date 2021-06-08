package tns.util;
import mil.navy.nps.relate.*;

/**
 * A simple class that encapsulates an agent for state change listeners.
 * @author  Rob Michael and Zac Staples
 */
public class StateChangeEvent {
    
    /**
     * The agent whose state has changed.
     */
    private Agent agent;
    
    /** 
     * Creates a new instance of StateChangeEvent 
     * @param agent The agent whose state has changed.
     */
    public StateChangeEvent(Agent agent) {
        this.agent = agent;
    } // end StateChangeEvent constructor
    
    /**
     * Returns the agent related to this event.
     * @return Agent The agent related to this event.
     */
    public Agent getAgent() { return agent; }
    
} // end class StateChangeEvent
