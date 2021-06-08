package tns.connectors;

/**
 * This interface is used by classes interesting in hearing changes in
 * connectors.
 * @author  Rob Michael and Zac Staples
 */
public interface ConnectorChangeListener {
    
    /**
     * Notifies the listener of changes in the connectors status.
     * @param cce The conector change event that encapsulates the connector
     * whose status has changed.
     */
    public void ConnectorChanged(ConnectorChangeEvent cce);
    
} // end interface ConnectorChangeListener
