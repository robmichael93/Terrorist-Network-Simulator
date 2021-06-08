package tns.agents;
import tns.goals.*;
import tns.messages.*;
import tns.util.*;
import tns.roles.*;
import tns.graphics.*;
import tns.sensors.*;
import mil.navy.nps.relate.*;
import java.util.*;
import java.awt.*;
import com.touchgraph.graphlayout.*;

/**
 * Terrorist agents are the key components of the TNS.  Each agent represents an 
 * individual that takes on different roles in the organization and has p
 * articular goals related to those roles.  Some of the roles carry with them 
 * particular needs and capabilities, but the majority of the functionality 
 * remained the same across roles.  Each agent can take on more than one role 
 * and the authors’ implementation reflects this fact for the most part, but
 * this initial implementation did not attempt to allow agents this ability for 
 * the simplicity of code creation.  Each agent has its own personality to set 
 * it apart from other agents and to affect its interactions with other agents 
 * in the simulation.  The last key component to the terrorist agents is the 
 * agent’s mental map.
 * @author  Rob Michael and Zac Staples
 */
public class TerroristAgent extends Agent {
    
    /**
     * The agent's sensed network environment, which includes agents directly
     * linked to as well as those sensed by sensors.
     */
    private SensedNetworkEnvironment networkEnvironment;
    /**
     * The agent's MentalMap; his worldview of the network.
     */
    private MentalMap mentalMap;
    /**
     * The graphical representation of the agent.
     */
    private AgentNode node;
    
    /**
     * The agent's inbox for receiving messages.
     */
    private Inbox inbox;
    /**
     * The agent's outbox for sending messages.
     */
    private Outbox outbox;
    
    /**
     * A table of the agents that this agent has communicated with and the
     * number of total times the agent has communicated with each agent.
     */
    private Hashtable agentCommunications;
    
    /**
     * The agent's location in the logical space of the simulation.
     */
    private Point location;
    
    /**
     * The collection of listeners for changes in the agent's links.
     */
    private Vector linkListeners;
    /**
     * The collection of listeners for changes in the agent's state.
     */
    private Vector stateListeners;
    /**
     * The collection of listeners for changes in the agent's history value
     * with another agent.
     */
    private Vector historyListeners;
    
    /**
     * The collection of agents in the simulation.  Used to calculate ratios
     * of roles to one another in the simulation when determining if an agent
     * can switch to certain roles; necessary to prevent saturation of the
     * simulation by particular roles.
     */
    private Vector simulationAgents;
    
    /** 
     * Creates a new instance of TerroristAgent 
     * @param id The agent's unique id.  Controlled by the top level simulation
     * object.
     * @param x The horizontal location of the agent in the logical space.
     * @param y The vertical location of the agent in the logical space.
     */
    public TerroristAgent(int id, int x, int y) {
        super((long) id, new String("TA" + id));
//        System.out.println("Agent " + entityName + " created.");
        location = new Point(x, y);
        node = new AgentNode(this);
        node.setLocation(location);
        networkEnvironment = new SensedNetworkEnvironment();
        sensedEnvironment = networkEnvironment;
        mentalMap = new MentalMap(this);
        inbox = new Inbox(this);
        outbox = new Outbox(this);
        linkListeners = new Vector();
        stateListeners = new Vector();
        addStateChangeListener(node);
        historyListeners = new Vector();
        agentCommunications = new Hashtable();
        pastActiveGoal = null;
        simulationAgents = null;
    }
    
    /*******************************************************************************
    * Adds the passed in role to the role vector.  It then adds the new Role's
    * Goals to the goalList.
    * @param pRole The new Role being assigned
    *******************************************************************************
    */
    public void addRole( Role pRole ) {
        Role temp = pRole;
        roleVec.add( temp );
        Vector tempVec = temp.getGoalListVec();
        for( int ix = 0; ix < tempVec.size(); ix++ ) {
            Goal tempGoal = (Goal)tempVec.get( ix );
            //store these in a hashtable with the goal name being the key and the
            //goal being the element.
            String tempString = tempGoal.getGoalName();
            goalList.put( tempString, tempGoal );
        } // end for
        
        StringBuffer newName = new StringBuffer("TA" + entityID);
        Iterator i = roleVec.iterator();
        while (i.hasNext()) {
            Role r = (Role)i.next();
            newName.append("(" + ((TNSRole)pRole).getSymbol() + ")");
        } // end while
        setEntityName(new String(newName));
        node.setLabel(entityName);
    } // end addRole
    
    
    /** Assigns credit to current goal set.  This should call assignCredit() in all
     * of the agents held goals. The result of these calls will assign weights to
     * all goals.  These weights will determine the current active goal for each
     * goaltype held.
     * If the agent has more than one highest goal with the same weight, a goal
     * is randomly selected.
     */
    public void assignCredit() {
        int highestGoalWeight = 0;
        int tempWeight = 0;
        pastActiveGoal = activeGoal;
        activeGoal = null;
        Vector highestGoals = new Vector();
        
//        System.out.println(entityName + " evaluating goals.");
        Enumeration e = goalList.elements();
        while (e.hasMoreElements()) {
            Goal goal = (Goal)e.nextElement();
//            System.out.println(entityName + " evaluating " + goal.getGoalName());
            // Only the FindContacts goal needs to use the agents sensed
            // within a specific radius for calculating goal weight
            goal.assignCredit(sensedEnvironment);
        } // end while
        
        // Now find the goal with the highest weight and assign it to be
        // the active goal
        Enumeration e2 = goalList.elements();
        while (e2.hasMoreElements()) {
            TNSGoal tempGoal = (TNSGoal)e2.nextElement();
            tempWeight = ((Integer)tempGoal.getGoalWeight()).intValue();
            if (tempWeight > highestGoalWeight) {
                highestGoalWeight = tempWeight;
                highestGoals = new Vector();
                highestGoals.add(tempGoal);
//                activeGoal = tempGoal;
            } else if (tempWeight == highestGoalWeight) {
                highestGoals.add(tempGoal);
            } // end if-else
        } // end while
        if (highestGoals.size() > 0) {
            int numberOfGoals = highestGoals.size();
            activeGoal = (Goal)highestGoals.elementAt((int)(Math.random() * numberOfGoals));
        } // end if
        changeState(this);
        if (activeGoal == null) {
            activeGoal = (Goal)((Role)roleVec.get(0)).getGoalListVec().get(0);
        } // end if
//        System.out.println(entityName + "'s active goal is " + activeGoal.getGoalName());
    } // end assignCredit
    
    /** drawSelf() can be used by the developer to draw the geometry of the entity
     * if there is to be a visual representation of the entity in the simulation.
     *
     */
    public void drawSelf() {
    }
    
    /** Gets the SensedEnvironment.
     * @return A SensedEnvironment containing the perceived environement.
     *
     */
    public SensedEnvironment getSensedEnvironment() { return sensedEnvironment; }
    
    /** Sets the agents SensedEnvironment to the passed in object
     * @param pPEnv SensedEnvironment object for use by this agent.
     *
     */
    public void setSensedEnvironment(SensedEnvironment pPEnv) {
    }
    
    /** step() can be used by the developer to indicate a simulation cycle or
     * discrete event occurrance.
     *
     */
    public void step() {}
    
    /**
     * This method first clears out the agent's sensed environment.  It then
     * finds all the agents this agent detects via all of the sensors
     * associated with its roles.  The sensed agents and those directly
     * linked to the agent are added to the agent's sensed environment.
     * @param a The container of agents in the simulation to use for detection.
     */
    public void detectAgents(Vector a) {
        networkEnvironment.reset();
        simulationAgents = a;
        Vector sensedAgents = new Vector();
        
        // for each role, get the associated sensors and check if any agents
        // are within sensor range
        Enumeration e = roleVec.elements();
        while (e.hasMoreElements()) {
            Role r = (Role)e.nextElement();
            // get the sensors associated with the role
            Vector sensors = r.getSensorListVec();
            Enumeration e2 = sensors.elements();
            while (e2.hasMoreElements()) {
                TNSSensor sensor = (TNSSensor)e2.nextElement();
                sensedAgents.addAll(sensor.findSensedAgents(this, simulationAgents));
            } // end while
        } // end while
        networkEnvironment.setSensedAgents(sensedAgents);
        networkEnvironment.addAgents(mentalMap.getDirectlyLinkedAgents());
//        networkEnvironment.addAgents(mentalMap.getSensedAgents());
    } // end detectAgents
    
    /**
     * Takes the active goal and executes the ticket associated with that goal.
     */
    public void executeActiveGoal() {
//        System.out.println(entityName + " executing active goal " + activeGoal.getGoalName());
        if (activeGoal != null) {
            ((TNSGoal)activeGoal).executeActiveTicket();
        } // end if
    } // end executeActiveGoal
    
    /**
     * Returns the active goal from the previous turn.
     * @return Goal The active goal from the previous turn.
     */
    public Goal getPastActiveGoal() { return pastActiveGoal; }
    
    /**
     * Returns the agent's location in the logical space.
     * @return Point The agent's location in the logical space.
     */
    public Point getLocation() { return location; }
    
    /**
     * Returns the agent's mental map.
     * @return MentalMap The agent's mental map.
     */
    public MentalMap getMentalMap() { return mentalMap; }
    
    /**
     * Returns the agent's inbox.
     * @return Inbox The agent's inbox.
     */
    public Inbox getInbox() { return inbox; }
    
    /**
     * Returns the agent's outbox.
     * @return Outbox The agent's outbox.
     */
    public Outbox getOutbox() { return outbox; }
    
    /**
     * Processes the messages in the agent's inbox.
     */
    public void processInbox() {
        inbox.process();
    } // end processInbox
    
    /**
     * Processes the messages in the agent's outbox.
     */
    public void processOutbox() {
        outbox.process();
    } // end processOutbox

    /**
     * Adds a link change listener.
     * @param lcl The link change listener being added.
     * @return boolean Returns true if the add was successful (the link change
     * listener did not already exist in the collection of listeners).
     */
    public boolean addLinkChangeListener(LinkChangeListener lcl) {
        if (linkListeners.contains(lcl)) {
         return false;
        } // end if
        linkListeners.add(lcl);
//        System.out.println("LinkChangeListener added.");
        return true;
    } // end addLinkChangeListener

    /**
     * Removes a link change listener.
     * @param lcl The link change listener to be removed.
     * @return boolean Returns true if the remove was successful (the link
     * change listener existed in the collection of listeners such that it
     * could be removed).
     */
    public boolean removeLinkChangeListener(LinkChangeListener lcl) {
        boolean removed = linkListeners.remove(lcl);
        if (removed) {
//            System.out.println("LinkChangeListener removed.");
            return true;
        } else {
            return false;
        } // end if-else
    } // end removeLinkChangeListener

    /**
     * Notifies the link change listeners of a change in one of the agent's 
     * links.
     * @param type The type of change happening to a link.  Valid values are
     * "add" and "remove"
     * @param agent1 One of the agents on one side of the link.
     * @param agent2 The other agent on the other side of the link.
     */
    public void changeLink(String type, Agent agent1, Agent agent2) {
        // notify listeners of status change
        Enumeration enum = linkListeners.elements();
        while (enum.hasMoreElements()) {
            LinkChangeListener lcl = (LinkChangeListener) enum.nextElement();
            lcl.LinkChanged(new LinkChangeEvent(type, agent1, agent2));
        } // end while
    } // end changeLink

    /**
     * Adds a state change listener.
     * @param scl The state change listener being added.
     * @return boolean Returns true if the add was successful (the state change
     * listener did not already exist in the collection of listeners).
     */
    public boolean addStateChangeListener(StateChangeListener scl) {
        if (stateListeners.contains(scl)) {
         return false;
        } // end if
        stateListeners.add(scl);
//        System.out.println("StateChangeListener added.");
        return true;
    } // end addLinkChangeListener

    /**
     * Removes a state change listener.
     * @param scl The state change listener to be removed.
     * @return boolean Returns true if the remove was successful (the state
     * change listener existed in the collection of listeners such that it
     * could be removed).
     */
    public boolean removeStateChangeListener(StateChangeListener scl) {
        boolean removed = stateListeners.remove(scl);
        if (removed) {
//            System.out.println("StateChangeListener removed.");
            return true;
        } else {
            return false;
        } // end if-else
    } // end removeLinkChangeListener

    /**
     * Notifies the state change listeners of a change in the agent's state.
     * @param agent The agent whose state has changed.
     */
    public void changeState(Agent agent) {
        // notify listeners of status change
        Enumeration enum = stateListeners.elements();
        while (enum.hasMoreElements()) {
            StateChangeListener scl = (StateChangeListener) enum.nextElement();
            scl.StateChanged(new StateChangeEvent(agent));
        } // end while
    } // end changeLink
    
    /**
     * Adds a history change listener.
     * @param hcl The history change listener being added.
     * @return boolean Returns true if the add was successful (the history 
     * change listener did not already exist in the collection of listeners).
     */
    public boolean addHistoryChangeListener(HistoryChangeListener hcl) {
        if (historyListeners.contains(hcl)) {
         return false;
        } // end if
        historyListeners.add(hcl);
//        System.out.println("HistoryhangeListener added.");
        return true;
    } // end addLinkChangeListener

    /**
     * Removes a history change listener.
     * @param hcl The history change listener to be removed.
     * @return boolean Returns true if the remove was successful (the history
     * change listener existed in the collection of listeners such that it
     * could be removed).
     */
    public boolean removeHistoryChangeListener(HistoryChangeListener hcl) {
        boolean removed = historyListeners.remove(hcl);
        if (removed) {
//            System.out.println("HistoryChangeListener removed.");
            return true;
        } else {
            return false;
        } // end if-else
    } // end removeLinkChangeListener

    /**
     * Notifies the history change listeners of a change in an agent pair the
     * agent is a part of.
     * @param agentPair The agent pair to update the history on.
     * @param amount The amount to update the history by.
     */
    public void changeHistory(AgentPair agentPair, int amount) {
        // notify listeners of status change
        Enumeration enum = historyListeners.elements();
        while (enum.hasMoreElements()) {
            HistoryChangeListener hcl = (HistoryChangeListener) enum.nextElement();
            hcl.HistoryChanged(new HistoryChangeEvent(agentPair, amount));
        } // end while
    } // end changeLink

    /**
     * Returns the agent's communications table.
     * @return Hashtable The agent's communications table.
     */
    public Hashtable getAgentCommunications() { return agentCommunications; }
    
    /**
     * Adds a new entry to the agent's communications table.
     * @param agent The agent to add to this agent's communications table.
     */
    public void newAgentCommunication(Agent agent) {
        agentCommunications.put(agent, new Integer(1));
    } // end newAgentCommunications
    
    /**
     * Increments the number of times this agent has communicated with another
     * agent by one.
     * @param agent The agent whose count is being incremented.
     */
    public void incrementAgentCommunicationCount(Agent agent) {
        if (agentCommunications.get(agent) != null) {
            int currentCount = ((Integer)agentCommunications.get(agent)).intValue();
            currentCount++;
            agentCommunications.put(agent, new Integer(currentCount));
        } // end if
    } // end incrementAgentCommunicationsCount
    
    /**
     * Returns the graphical drawing object for this agent.
     * @return AgentNode The graphical drawing object for this agent.
     */
    public AgentNode getNode() { return node; }
    
    /**
     * Returns the container of all agents in the simulation.  Used for the
     * purpose of calculating role ratios.
     * @return Vector The container of all agents in the simulation.
     */
    public Vector getSimulationAgents() { return simulationAgents; }
    
} // end class TerroristAgent
