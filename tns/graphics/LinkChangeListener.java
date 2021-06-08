package tns.graphics;

/**
 * This interface is used by classes that want to detect changes in the links
 * in the network.
 * @author  Rob Michael and Zac Staples.
 */
public interface LinkChangeListener {
    
    /**
     * Notifies the listener that a link changed.
     * @param lce The link change event that captures the type of action to
     * take and the two agents involved in the link change.
     */
    public void LinkChanged(LinkChangeEvent lce);
    
} // end interface LinkChangeListener
