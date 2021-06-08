package tns.util;

/**
 * This interface is used by classes that want to detect changes in the history
 * of agent pairs.
 * @author  Rob Michael and Zac Staples
 */
public interface HistoryChangeListener {
    
    /**
     * Method that notifies a listener of a change.
     * @param hce The HistoryChangeEvent encapsulates the agent pair to update
     * and the amount by which to update the history.
     */
    public void HistoryChanged(HistoryChangeEvent hce);
    
} // end interface HistoryChangeListener
