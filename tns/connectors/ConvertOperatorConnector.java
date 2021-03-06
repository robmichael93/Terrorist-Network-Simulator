package tns.connectors;
import mil.navy.nps.relate.*;

/**
 * Leaders extend this connector that operators in their mission can connect 
 * with (they always have a receptor connector for this stimulator extended once
 * they are in a mission) so that the leader can promote them to a specialist 
 * role.
 * @author  Rob Michael and Zac Staples
 */
public class ConvertOperatorConnector extends Connector {
    
    /**
     * The operator agent being converted to a specialist.
     */
    private Agent agent;
    /**
     * The name of the role the operator is changing to.
     */
    private String newRole;
    
    /** 
     * Creates a new instance of ConvertOperatorConnector 
     * @param role The role that owns this connector.
     */
    public ConvertOperatorConnector(Role role) {
        super(role, "ConvertOperatorConnector");
        agent = null;
        newRole = null;
    } // end ConvertOperatorConnector constructor

    /**
     * Associates an agent (operator) with the connector so that the correct
     * agent hears the connector.
     * @param a The agent being associated with the connector.
     */
    public void associateAgent(Agent a) {
        agent = a;
    } // end associateTicket
    
    /**
     * Returns the agent associated with the connector.
     * @return Agent The agent associated with the connector.
     */
    public Agent getAgent() { return agent; }

    /**
     * Associates the name of the new role with this connector.
     * @param r The name of the new role associated with this connector.
     */
    public void associateNewRole(String r) {
        newRole = r;
    } // end associateTicket
    
    /**
     * Returns the name of the new role associated with this connector.
     * @return String The name of the new role associated with this connector.
     */
    public String getNewRole() { return newRole; }
    
} // end class ConvertOperatorConnector
