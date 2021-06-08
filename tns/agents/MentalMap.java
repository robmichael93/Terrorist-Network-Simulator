package tns.agents;
import mil.navy.nps.relate.*;
import com.touchgraph.graphlayout.graphelements.*;
import com.touchgraph.graphlayout.*;
import java.util.*;
import tns.util.*;
import tns.agents.*;
import tns.graphics.*;

/**
 * The mental map is the agent’s mental space of how it perceives the network 
 * environment based on whom that agent knows directly and indirectly knows 
 * about.  A mental map is the agent’s own worldview.  The agent evaluates its 
 * goals and acts upon them largely based on the agent’s mental map.  An agent’s 
 * mental map likely differs from the explicit map of the network, or the ground 
 * truth of who knows whom in the organization.
 * @author  Rob Michael and Zac Staples
 */
public class MentalMap implements SensedEnvironment {
    
    /**
     * The agent who owns this MentalMap.
     */
    private Agent agent;
    /**
     * The agent's collection of known agents.  Known agents include all the
     * directly linked agents but the inverse is not true.
     */
    private Vector knownAgents;
    /**
     * All the agents directly linked the agent who owns this mental map.  Does
     * not include indirectly linked agents, as those are kept in the known
     * agents Vector.
     */
    private Vector directlyLinkedAgents;
    /**
     * The helper class that manages the links/edges in the MentalMap.
     */
    private SubNet subNet;
    /**
     * A collection of objects that listen for changes in the nodes in the
     * MentalMap.
     */
    private Vector nodeListeners;
    
    /** 
     * Creates a new instance of MentalMap 
     * @param agent The agent who owns this MentalMap.
     */
    public MentalMap(Agent agent) {
        this.agent = agent;
//        System.out.println("MentalMap created.");
        knownAgents = new Vector();
        directlyLinkedAgents = new Vector();
        subNet = new SubNet();
        nodeListeners = new Vector();
    } // end MentalMap constructor
    
    /** Vector containing all sensed agents.  This is used by the relationshipManager
     * in checking for new or formed relationships.
     * @return  A vector containing all currently sensed agents.
     *
     */
    public Vector getSensedAgents() { return directlyLinkedAgents; }
    
    /** Returns an Object that contains all required elements of the
     * SensedEnvironment.  This method puts all of the various elements
     * of the PerceivedEnvironment into an appropriate Object using
     * getter-methods.
     * @return  A SensedEnvironment object.
     *
     */
    public SensedEnvironment getSensedEnvironment() { return this; }
    
    /**
     * Returns the collection of agents directly linked to the one that owns
     * this MentalMap.
     * @return Vector The collection of directly linked agents.
     */
    public Vector getDirectlyLinkedAgents() { return directlyLinkedAgents; }
    
    /**
     * Returns the collection of agents known to the one that own this
     * MentalMap.
     * @return Vector The collection of known agents.
     */
    public Vector getKnownAgents() { return knownAgents; }
    
    /**
     * Returns the SubNet object.
     * @return SubNet The SubNet object associated with this MentalMap.
     */
    public SubNet getSubNet() { return subNet; }
    
    /**
     * Adds a known agent and specifies which other agent the link originates
     * from.
     * @param from The agent that the link originates from to the known agent.
     * @param a The known agent being added.
     */
    public void addKnownAgent(Agent from, Agent a) {
//        System.out.println(a.getAgentName() + " added as known agent.");
        knownAgents.add(a);
        AgentPair addPair = new AgentPair(from, a);
        subNet.addPair(addPair);
        changeNode("add", a);
    } // end addKnownAgent
    
    /**
     * Adds a agent that is directly linked to the agent that owns this
     * MentalMap.
     * @param a The directly linked agent being added.
     */
    public void addDirectlyLinkedAgent(Agent a) {
        if (!directlyLinkedAgents.contains(a)) {
            directlyLinkedAgents.add(a);
//            System.out.println(a.getAgentName() + " added as directly linked agent of " + agent.getEntityName());
            AgentPair addPair = new AgentPair(agent, a);
            subNet.addPair(addPair);
            if (!knownAgents.contains(a)) {
                knownAgents.add(a);
                changeNode("add", a);
            } // end if
        } // end if
    } // end addDirectlyLinkedAgent
    
    /**
     * Removes a directly linked agent from the one that owns this MentalMap.
     * This method also removes the directly linked agent from any relationships
     * the two agents share.  If there are no other edges in the MentalMap that
     * link to the agent being removed, then the agent is removed from the list
     * of known agents and the node listeners are notified of the change.
     * @param a The directly linked agent being removed.
     */
    public void removeDirectlyLinkedAgent(Agent a) {
        if (directlyLinkedAgents.remove(a)) { // only continue if agent successfully removed
            AgentPair removePair = new AgentPair(agent, a);
//            System.out.println(agent.getEntityName() + "'s MentalMap removing pair from " + agent.getEntityName() + " to " + a.getEntityName());
            subNet.removePair(removePair);
            Hashtable agentCommunications = ((TerroristAgent)agent).getAgentCommunications();
            agentCommunications.remove(a);
//            System.out.println(a.getAgentName() + " removed as directly linked agent of " + agent.getEntityName());
            
            // Remove Agent a from all relationships that this agent and Agent a have together
            Hashtable relationshipTable = agent.getRelationshipTable();
            Enumeration e = relationshipTable.keys();
            while (e.hasMoreElements()) {
                String relationshipName = (String)e.nextElement();
                Object removeRelationships = relationshipTable.get(relationshipName);
                if (removeRelationships instanceof Vector) {
                    Enumeration e2 = ((Vector)removeRelationships).elements();
                    while (e2.hasMoreElements()) {
                        Relationship removeRelationship = (Relationship)e2.nextElement();
                        if (removeRelationship.getMembers().contains(a)) {
                            removeRelationship.removeAgent(a);
                        } // end if
                    } // end while
                } // end if
            } // end while
        } // end if
        boolean foundAnotherEdge = false;
        Vector subNetEdges = subNet.getUniquePairs();
        Iterator i = subNetEdges.iterator();
        while (i.hasNext()) {
            AgentPair agentPair = (AgentPair)i.next();
            if (agentPair.contains(a)) {
                foundAnotherEdge = true;
            } // end if
        } // end while
        if (!foundAnotherEdge) {
//            System.out.println(a.getAgentName() + " removed entirely from " + agent.getEntityName() + "'s MentalMap");
            knownAgents.remove(a);
            changeNode("remove", a);
        } // end if
    } // end removeDirectlyLinkedAgent
    
    /**
     * Adds a node change listener.
     * @param v The node change listener being added.
     * @return boolean Returns true if the add was successful (the node change
     * listener did not already exist in the collection of listeners).
     */
    public boolean addNodeChangeListener(NodeChangeListener v) {
        if (nodeListeners.contains(v)) {
         return false;
        } // end if
        nodeListeners.add(v);
//        System.out.println("NodeChangeListener added.");
        return true;
    } // end addLinkChangeListener

    /**
     * Removes a node change listener.
     * @param v The node change listener to be removed.
     * @return boolean Returns true if the remove was successful (the node
     * change listener existed in the collection of listeners such that it
     * could be removed).
     */
    public boolean removeNodeChangeListener(NodeChangeListener v) {
        boolean removed = nodeListeners.remove(v);
        if (removed) {
//            System.out.println("NodeChangeListener removed.");
            return true;
        } else {
            return false;
        } // end if-else
    } // end removeLinkChangeListener

    /**
     * Notifies the node change listeners of a change in a node in the MentalMap.
     * @param type The type of change happening to a node.  Valid values are
     * "add" and "remove"
     * @param agent The agent whose node is being changed.
     */
    public void changeNode(String type, Agent agent) {
        // notify listeners of status change
        Enumeration enum = nodeListeners.elements();
        while (enum.hasMoreElements()) {
            NodeChangeListener ncl = (NodeChangeListener) enum.nextElement();
            ncl.NodeChanged(new NodeChangeEvent(type, agent));
        } // end while
    } // end changeLink
} // end class MentalMap
