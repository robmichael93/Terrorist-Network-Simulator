package tns.connectors;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * Connectors allow one type of interaction between agents.  Connectors follow a
 * biological metaphor of proteins interacting with a cell that was developed by
 * John Hiles of the MOVES Institute at the Naval Postgraduate School (NPS) 
 * (Hiles et. al., 2002).  His work has been implemented in Brian Osborn�s Story 
 * Engine (Osborn, 2002, p. 55).  Connectors are described by their type and 
 * their state of being extended or retracted.  Connector types are receptors 
 * and stimulators.  When a receptor connector is in an extended state, it can 
 * connect with a stimulator that is also in an extended state.  When the 
 * connectors connect, then several actions take place within each agent based 
 * on the type of connection made.  Actions that take place include the exchange 
 * of information, the issuing of actions or orders for the other agent to carry 
 * out, or the transformation of one or both of the agents into another state or 
 * type.  These actions are carried out in the form of procedural knowledge 
 * known as tickets.
 * NOTE: See the related thesis for references.
 * @author  Rob Michael and Zac Staples
 */
public class Connector {
    
    /**
     * The class name of the connector.
     */
    protected String name;
    /**
     * Whether or not the connector is extended.
     */
    protected boolean extended;
    /**
     * The container of listeners for this connector.
     */
    protected Vector listeners;
    /**
     * The role that owns this connector.
     */
    protected Role role;
    
    /**
     * The risk of the connection.
     */
    private int risk;

    /** 
     * Creates a new instance of Connector 
     * @param role The role that owns this connector.
     * @param name The class name of the connector.
     */
    public Connector(Role role, String name) {
        this.role = role;
        this.name = name;
//        System.out.println(name + " created.");
        listeners = new Vector();
        extended = false;
        risk = 0;
    } // end Connector constructor
    
    /**
     * Returns whether or not the connector is extended.
     * @return boolean Whether or not the connector is extended.
     */
    public boolean isExtended() { return extended; }
    /**
     * Returns whether or not the connector is retracted.
     * @return boolean Whether or not the connector is retracted.
     */
    public boolean isRetracted() { return !extended; }
    
    /**
     * Returns the name of the connector.
     * @return String The name of the connector.
     */
    public String getName() {
        return name;
    } // end getName

    /**
     * Extends the connector, which notifies any listeners.
     */
    public void extendConnector() {
//        System.out.println(name + " extended.");
        changeStatus(true);
    } // end extendConnector

    /**
     * Retracts the connector, which notifies any listeners.
     */
    public void retractConnector() {
//        System.out.println(name + " retracted.");
        changeStatus(false);
    } // end retractConnector

    /**
     * Adds a connector change listener.
     * @param v The connector change listener being added.
     * @return boolean Returns true if the add was successful (the connector
     * change listener did not already exist in the collection of listeners).
     */
    public boolean addConnectorChangeListener(ConnectorChangeListener v) {
        if (listeners.contains(v)) {
         return false;
        }
        listeners.add(v);
//        System.out.println(name + " listener added.");
        return true;
    } // end addConnectorChangeListener

    /**
     * Removes a connector change listener.
     * @param v The connector change listener to be removed.
     * @return boolean Returns true if the remove was successful (the connector
     * change listener existed in the collection of listeners such that it
     * could be removed).
     */
    public boolean removeConnectorChangeListener(ConnectorChangeListener v) {
        boolean removed = listeners.remove(v);
        if (removed) {
//            System.out.println(name + " listener removed.");
            return true;
        } else {
            return false;
        } // end if-else
    } // end removeConnectorChangeListener

    /**
     * Notifies the connecotr change listeners of a change in the status of
     * the connector.
     * @param s The status of the connector where true = extended.
     */
    public void changeStatus(boolean s) {
        extended = s;

        // notify listeners of status change
        Enumeration enum = listeners.elements();
        while (enum.hasMoreElements()) {
            ConnectorChangeListener ccl = (ConnectorChangeListener) enum.nextElement();
            ccl.ConnectorChanged(new ConnectorChangeEvent(this));
        } // end while
    } // end changeStatus

    /**
     * Sets the status of the connector where true = extended.
     * @param s The status of the connector where true = extended.
     */
    public void setStatus (boolean s) {
        extended = s;
    } // end setStatus

    /**
     * Notifies any listeners of the status of the connector.
     */
    public void reportStatus() {
        changeStatus(extended);
    } // end reportStatus
    
    /**
     * Returns the role that owns this connector.
     * @return Role The role that owns this connector.
     */
    public Role getRole() { return role; }
    
    /**
     * Sets the risk value for the connector.
     * @param r The risk value to be set for the connector.
     */
    public void setRisk(int r) {
        risk = r;
    } // end setRisk
    
    /**
     * Returns the risk value of the connector.
     * @return int The risk value of the connector.
     */
    public int getRisk() { return risk; }

    /**
     * Updates the risk value by a specified amount.
     * @param r The amount to update the risk by.
     */
    public void updateRisk (int r) {
        risk += r;
    } // end updateRisk
} // end class Connector
