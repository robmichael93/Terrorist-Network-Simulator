package tns.connectors;

/**
 * This class encapsulates a connector for notifying listeners of changes in
 * the connector's status.
 * @author  Rob Michael and Zac Staples
 */
public class ConnectorChangeEvent {
    
    /**
     * The connector associated with this event.
     */
    private Connector source;
    
    /** 
     * Creates a new instance of ConnectorChangeEvent 
     * @param c The connector associated with this event.
     */
    public ConnectorChangeEvent(Connector c) {
        source = c;
    } // end ConnectorChangeEvent constructor
    
    /**
     * Returns whether the connector is extended or not.
     * @return boolean Whether the connector is extended or not.
     */
    public boolean isConnectorExtended() {
        return source.isExtended();
    } // end isConnectorExtended

    /**
     * Returns the connector's class name.
     * @return String The connector's class name.
     */
    public String getConnectorName() {
        return source.getName();
    } // end getConnectorName
    
    /**
     * Returns the connector associated with this event.
     * @return Connector The connector associated with this event.
     */
    public Connector getSource() { return source; }
    
} // end class ConnectorChangeEvent
