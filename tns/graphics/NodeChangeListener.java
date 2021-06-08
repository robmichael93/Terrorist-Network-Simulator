package tns.graphics;

/**
 * This interface is used by objects that want to know about changes in nodes
 * in the network.
 * @author  Rob Michael and Zac Staples
 */
public interface NodeChangeListener {
    
    /**
     * Notifies the listener that a node has changed.
     * @param nce The node change event that encapsulates the type of action
     * to take and the agent who's node is changing.
     */
    public void NodeChanged(NodeChangeEvent nce);
    
} // end interface NodeChangeListener
