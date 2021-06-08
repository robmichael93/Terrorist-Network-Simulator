package tns.graphics;
import java.util.*;
import tns.util.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import com.touchgraph.graphlayout.*;

/**
 * This class handles the drawing of the mental map and its associated listeners.
 * @author  Rob Michael and Zac Staples
 */
public class CognitiveMapPanel extends TNSPanel {
    
    /**
     * The agent who owns the mental map.
     */
    private Agent agent;
    
    /** 
     * Creates a new instance of CognitiveMapPanel 
     * @param subNet The sub-network for the mental map.
     * @param agents The agent's set of known agents.
     * @param agent The agent who owns the mental map.
     */
    public CognitiveMapPanel(SubNet subNet, Vector agents, Agent agent) {
        super();
        this.subNet = subNet;
        this.agents = new Vector(agents);
        this.agents.add(agent);
        this.agent = agent;
        ((TerroristAgent)(this.agent)).getMentalMap().addNodeChangeListener(this);
//        ((TerroristAgent)(this.agent)).addLinkChangeListener(this);
        Iterator i2 = this.agents.iterator();
        while (i2.hasNext()) {
            Agent a = (Agent)i2.next();
            ((TerroristAgent)a).addLinkChangeListener(this);
        } // end while
        agentNodes = new Vector();
        try {
            createGraph();
        } catch ( TGException tge ) {
            System.err.println(tge.getMessage());
            tge.printStackTrace(System.err);
        } // end try-catch
/*        Iterator i = this.agents.iterator();
        while (i.hasNext()) {
            Agent a = (Agent)i.next();
            ((TerroristAgent)a).changeState(a);
        } // end while*/
    } // end CognitiveMapPanel constructor
    
    /**
     * This method creates the graph to be rendered.  State change listeners
     * are added to all of the nodes so that changes in roles can be captured.
     * This method adds the nodes and edges to the TouchGraph panel for
     * drawing.
     */
    public void createGraph() throws TGException {
        Vector uniquePairs = subNet.getUniquePairs();
        Iterator i = agents.iterator();
        while (i.hasNext()) {
            Agent agent = (Agent)i.next();
            AgentNode addNode = new AgentNode(agent);
            ((TerroristAgent)agent).addStateChangeListener(addNode);
//            AgentNode addNode = ((TerroristAgent)agent).getNode();
            agentNodes.add(addNode);
            try {
                tgPanel.addNode(addNode);
            } catch ( TGException tge ) {
                System.err.println(tge.getMessage());
                tge.printStackTrace(System.err);
            } // end try-catch*/
            
        } // end while
        i = uniquePairs.iterator();
        while (i.hasNext()) {
            AgentPair agentPair = (AgentPair)i.next();
            Agent fromAgent = agentPair.getFrom();
            Agent toAgent = agentPair.getTo();
            AgentNode fromAgentNode = null;
            AgentNode toAgentNode = null;
            Enumeration e = agentNodes.elements();
            while (e.hasMoreElements()) {
                AgentNode node = (AgentNode)e.nextElement();
                Agent agent = node.getAgent();
                if (agent == fromAgent) {
                    fromAgentNode = node;
                } else if (agent == toAgent) {
                    toAgentNode = node;
                } // end if
            } // end while
            if (fromAgentNode != null && toAgentNode != null) {
                int distance = (int) ((TerroristAgent)fromAgent).getLocation().distance(((TerroristAgent)toAgent).getLocation());
                tgPanel.addEdge(fromAgentNode, toAgentNode, distance * 100);
                tgPanel.addEdge(toAgentNode, fromAgentNode, distance * 100);
            } // end if
        } // end while
    } // end createGraph
    
    /**
     * Returns a name for this cognitive map panel.
     * @return String The name for this cognitive map panel.
     */
    public String toString() {
        return getClass().getName() + "." + agent.getEntityName();
    } // end toString
    
    /**
     * Either adds or removes a node from the TouchGraph panel.
     * @param nce The node change event that captures what action should take
     * place.  Valid types of changes are "add" and "remove."  State change
     * listeners are added or removed when nodes are added or removed as well.
     */
    public void NodeChanged(NodeChangeEvent nce) {
        String type = nce.getChangeType();
        if (type.equalsIgnoreCase("add")) {
            // do add method
            Agent agent = nce.getAgent();
            AgentNode agentNode = new AgentNode(agent);
            ((TerroristAgent)agent).addStateChangeListener(agentNode);
//            AgentNode agentNode = ((TerroristAgent)agent).getNode();
            agentNodes.add(agentNode);
            if (agentNode != null) {
                try {
                    tgPanel.addNode(agentNode);
                } catch ( TGException tge ) {
                    System.err.println(tge.getMessage());
                    tge.printStackTrace(System.err);
                } // end try-catch
            } // end if
        } else if (type.equalsIgnoreCase("remove")) {
            Agent agent = nce.getAgent();
            AgentNode agentNode = null;
            Iterator i = agentNodes.iterator();
            while (i.hasNext()) {
                AgentNode node = (AgentNode)i.next();
                Agent nodeAgent = node.getAgent();
                if (agent == nodeAgent) {
                    agentNode = node;
                } // end
            } // end while
            if (agentNode != null) {
                ((TerroristAgent)agent).removeStateChangeListener(agentNode);
                tgPanel.deleteNode(agentNode);
            } // end if
        } // end if-else
    }
    
    /**
     * This method removes all remaining state and link change listeners in
     * the cognitive map panel.
     */
    public void deregisterListeners() {
        Iterator i = agents.iterator();
        while (i.hasNext()) {
            Agent agent = (Agent)i.next();
            AgentNode agentNode = null;
            Iterator i2 = agentNodes.iterator();
            while (i2.hasNext()) {
                AgentNode node = (AgentNode)i2.next();
                Agent nodeAgent = node.getAgent();
                if (agent == nodeAgent) {
                    agentNode = node;
                } // end
            } // end while
            if (agentNode != null) {
                ((TerroristAgent)agent).removeStateChangeListener(agentNode);
                ((TerroristAgent)agent).removeLinkChangeListener(this);
            } // end if
        } // end while
    } // end deregisterListeners
    
} // end class CognitiveMapPanel
