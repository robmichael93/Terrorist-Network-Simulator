package tns.graphics;
import mil.navy.nps.relate.*;

/**
 * This class encapsulates the needed information to add or remove a link
 * from a graphical display in the simulation.
 * @author  Rob Michael and Zac Staples
 */
public class LinkChangeEvent {
    
    /**
     * The type of action to perform.  Valid entries are "add" and "remove."
     */
    private String type;
    /**
     * One of the agents that is part of the link.
     */
    private Agent agent1;
    /**
     * The other agent that is part of the link.
     */
    private Agent agent2;
    
    /** 
     * Creates a new instance of LinkChangeEvent 
     * @param The type of action to perform.
     * @param agent1 One of the agents that is part of the link.
     * @param agent2 The other agent that is part of the link.
     */
    public LinkChangeEvent(String type, Agent agent1, Agent agent2) {
        this.type = type;
        this.agent1 = agent1;
        this.agent2 = agent2;
    } // end LinkChangeEvent constructor
    
    /**
     * Returns the type of change action to take.
     * @return String The type of change action to take.
     */
    public String getChangeType() { return type; }
    
    /**
     * Returns one of the agents involved in the link change.
     * @return Agent One of the agents involved in the link change.
     */
    public Agent getAgent1() { return agent1; }
    
    /**
     * Returns the other agent involved in the link change.
     * @return Agent The other agent involved in the link change.
     */
    public Agent getAgent2() { return agent2; }
    
} // end class LinkChangeEvent
