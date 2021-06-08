package tns.frames;

/**
 * A frame in a ticket encapsulates an atomic action.  Frames consist of either 
 * of an action, a connector, or another ticket.  Actions encapsulate reusable 
 * functions performed by the agents such that ticket composition is 
 * accomplished by selecting the associated actions into the desired order 
 * necessary to accomplish some procedure or process.  Connectors are included 
 * in a frame so that their state can be changed and connections can be made 
 * with other agents.
 * @author  Rob Michael and Zac Staples
 */
public interface Frame {
    
    /**
     * Performs whatever action is defined in the frame.
     */
    void execute();
    
} // end interface Frame
