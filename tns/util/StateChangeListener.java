package tns.util;

/**
 * This interface is used by classes that want to listen to changes in the
 * state of agents.
 * @author  Rob Michael and Zac Staples
 */
public interface StateChangeListener {
    
    /**
     * Notifies listeners of a change in the state of an agent.
     * @param sce The StateChangeEvent encapsulates the agent whose state
     * has changed.
     */
    public void StateChanged(StateChangeEvent sce);
    
} // end StateChangeListener
