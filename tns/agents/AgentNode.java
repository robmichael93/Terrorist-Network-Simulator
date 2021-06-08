package tns.agents;
import tns.util.*;
import tns.roles.*;
import mil.navy.nps.relate.*;
import com.touchgraph.graphlayout.*;
import java.awt.*;
import java.util.*;

/**
 * The agent node is a wrapper for a TouchGraph Node and a TNS Agent.
 * @author  Rob Michael and Zac Staples
 */
public class AgentNode extends Node implements StateChangeListener {
    
    /**
     * The agent associated with the node.
     */
    private Agent agent;
    
    /** 
     * Creates a new instance of AgentNode 
     * @param agent The agent associated with the TouchGraph Node.
     */
    public AgentNode(Agent agent) {
        super(Integer.toString((int)agent.getEntityID()), agent.getAgentName());
        this.agent = agent;
    } // end AgentNode constructor
    
    /**
     * Returns the agent object.
     * @return Agent The agent associated with this role.
     */
    public Agent getAgent() { return agent; }
    
    /**
     * This method is used to update the look of the graphical representation
     * of the agent when the agent's role(s) change.
     * @param sce The StateChangeEvent representing the change in the agent.
     */
    public void StateChanged(StateChangeEvent sce) {
        Agent nodeAgent = sce.getAgent();
        setLabel(nodeAgent.getEntityName());
        Vector roles = nodeAgent.getRoleVector();
        if (roles.size() > 1) {
            setBackColor(Color.cyan);
        } else {
            TNSRole role = (TNSRole)roles.firstElement();
            setBackColor(role.getBackgroundColor());
            setTextColor(role.getTextColor());
        } // end if
    } // end StateChanged
    
} // end class AgentNode
