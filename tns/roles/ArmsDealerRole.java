package tns.roles;
import tns.agents.*;
import tns.connectors.*;
import tns.goals.*;
import tns.sensors.*;
import tns.tickets.*;
import tns.actions.*;
import tns.frames.*;
import tns.util.*;
import mil.navy.nps.relate.*;
import java.util.*;
import java.awt.*;

/**
 * The last of the specialists, arms dealers, provide the instruments of terror 
 * that mark the unconventional and often shocking methods these organizations 
 * employ.  Bit player arms dealers might rob a gun store or make a fertilizer
 * bomb from something they read off the Internet while real players in the arms 
 * market know how to obtain untraceable small arms, quality explosives, and 
 * possibly weapons of mass effects, such as “dirty” bombs or even quite 
 * possibly nuclear weapons, biological pathogens such as anthrax or small pox, 
 * or chemical toxins such as Sarin gas.
 * @author  Rob Michael and Zac Staples
 */
public class ArmsDealerRole implements TNSRole, ConnectorChangeListener {
    
    /**
     * The role's symbol.
     */
    private String symbol = "A";
    /**
     * The role's display background color.
     */
    private Color roleBackgroundColor = Color.orange;
    /**
     * The role's display text color.
     */
    private Color textColor = Color.black;
    
    /**
     * The agent that owns this role.
     */
    private Agent agent;
    /**
     * The name of the role.
     */
    private String name;
    /**
     * The goals associated with this role.
     */
    private Vector goals;
    /**
     * The sensors associated with this role.
     */
    private Vector sensors;
    /**
     * The relationships this role is a part of.
     */
    private Vector relationships;
    /**
     * The connectors associated with this role.
     */
    private Vector connectors;
    /**
     * The tickets associated with this role.
     */
    private Vector tickets;
    /**
     * The arms dealer's resource.
     */
    private Resource arms;
    /**
     * Flag for whether or not this role attempted to provide a resource during 
     * the last turn.
     */
    private boolean providedArms;
    /**
     * Flag for whether or not a leader requested resources from this role
     * during the last turn.
     */
    private boolean armsRequested;
    /**
     * The additional weight of a leader's request for resources when requested
     * via message.
     */
    private int requestWeight;

    /**
     * This role's stuck counter.
     */
    private int stuckCounter;
    /**
     * The stuck counter threshold.
     */
    private int stuckThreshold;
    
    /** 
     * Creates a new instance of ArmsDealerRole 
     * @param agent The agent that owns this role.
     */
    public ArmsDealerRole(Agent agent) {
        this.agent = agent;
        name = new String("ArmsDealerRole");
//        System.out.println(agent.getAgentName() + "'s " + name + " created.");
        goals = new Vector();
//        System.out.println("Adding goals...");
        goals.add(new ProvideArmsGoal(this));
        goals.add(new ProduceArmsGoal(this));
        goals.add(new AdvanceGoal(this));
        sensors = new Vector();
//        System.out.println("Adding sensors...");
//        addSensor(new ContactSensor(0.0));
        relationships = new Vector();
//        System.out.println("Adding relationships...");
        relationships.add(new String("ArmsBarteringRelationship"));
        agent.addRelationshipNames(relationships);
        connectors = new Vector();
        tickets = new Vector();
        tickets.add(new ProduceArmsTicket(this));
        tickets.add(new IncrementStuckCounterTicket(this));
        arms = new Resource(this);
        providedArms = false;
        armsRequested = false;
        requestWeight = 0;
        stuckCounter = 0;
        stuckThreshold = 15;
    } // end ArmsDealerRole constructor
    
    /** Adds the indicated action to the actionList
     * <B> Not used in the TNS. </B>
     * @param pAction  The action to be added
     *
     */
    public void addAction(Action pAction) {}
    
    /** Adds the passed in Goal to the goalList.
     * @param pGoal The goal being added.
     */
    public void addNewGoal(Goal pGoal) {
        goals.add(pGoal);
    } // end addNewGoal
    
    /** Adds the indicated sensor to the sensorList
     * @param pSensor An integer representing the sensor to be added
     *
     */
    public void addSensor(Sensor pSensor) {
        sensors.add(pSensor);
    } // end addSensor
    
    /** Getter for actionList.  Returns a Vector containing a list of integers
     * representing all of the actions available to the agent due to this Role
     * <B> Not used in the TNS. </B>
     * @return actionList
     *
     */
    public Vector getActionListVec() { return null; }
    
    /**
     * Returns the agent that owns this role.
     * @return Agent The agent that owns this role.
     */
    public Agent getAgent() { return agent; }
    
    /**
     * Returns the role's connectors.
     * @return Vector The role's connectors.
     */
    public Vector getConnectors() { return connectors; }
    
    /** Getter for goalList
     * @return goalList vector
     *
     */
    public Vector getGoalListVec() { return goals; }
    
    /**
     * Returns the names of the relationships this role is a part of.
     * @return Vector The names of the relationships this role is a part of.
     */
    public Vector getRelationships() { return relationships; }
    
    /** Gets the role name
     * @return The "string" role name.
     *
     */
    public String getRoleName() { return name; }
    
    /** Getter for sensorList
     * @return sensorList vector
     *
     */
    public Vector getSensorListVec() { return sensors; }
    
    /**
     * Returns the role's tickets.
     * @return Vector The role's tickets.
     */
    public Vector getTickets() { return tickets; }
    
    /** Removes the indicated action from the actionList
     * <B> Not used in the TNS. </B>
     * @param pAction The action to be removed
     * @return The success of the removal operation:
     * <li> true - The action was successfully removed
     * <li> false - The action did not exist in the Role's actionList
     *
     */
    public boolean removeAction(Action pAction) {
        return false;
    } // end removeActions
    
    /** Removes the Goal from the goalList.
     * @param pGoal The goal being removed.
     * @return The success of the removal operation:
     * <li> true - The Goal was successfully removed
     * <li> false - The Goal did not exist in the Role's goalList
     */
    public boolean removeGoal(Goal pGoal) {
        return goals.remove(pGoal);
    } // end removeGoal
    
    /** Removes the indicated sensor from the sensorList
     * @param pSensor An integer representing the sensor to be removed
     * @return The success of the removal operation:
     * <li> true - The sensor was successfully removed
     * <li> false - The sensor did not exist in the Role's sensorList
     *
     */
    public boolean removeSensor(Sensor pSensor) {
        return sensors.remove(pSensor);
    } // end removeSensor
    
    /** Setter for actionList.  Replaces the actionList Vector with a new list of
     * integers representing all of the actions available to the agent due to
     * this Role
     * <B> Not used in the TNS. </B>
     * @param pActionList The action list being set.
     */
    public void setActionList(Vector pActionList) {}
    
    /** Sets the role name of the role object, this should be the exact class name
     * of the role.
     * @param pRoleName  The role name
     *
     */
    public void setRoleName(String pRoleName) {
        name = pRoleName;
    } // end setRoleName
    
    /** Setter for sensorList
     * @param pSensorList The sensor list being set.
     */
    public void setSensorList(Vector pSensorList) {
        sensors = pSensorList;
    } // end setSensorList
    
    /**
     * Method for hearing a connector.  If a RequestArmsConnector was extended
     * and the agent's active goal was ProvideArms or Advance, then this
     * stimulator connects and takes the necessary actions.
     * @param cce The connector change event that encapsulates the connector
     * and its associated ticket.
     */
    public void ConnectorChanged(ConnectorChangeEvent cce) {
        Connector c = cce.getSource();
        if (c.isExtended()) {
            if (c instanceof RequestArmsConnector &&
                agent.getActiveGoal() instanceof ProvideArmsGoal ||
                agent.getActiveGoal() instanceof AdvanceGoal) {
//                System.out.println(agent.getAgentName() + " detected RequestArmsConnector.");
                Role leader = c.getRole();
                Agent leaderAgent = ((TNSRole)leader).getAgent();
                AgentPair pair = new AgentPair(agent, leaderAgent);
                ((TerroristAgent)agent).changeHistory(pair, 15);
                Ticket ticket = ((RequestArmsConnector)c).getTicket();
                Ticket provideArms = new ProvideResourceTicket(leader, this, ticket);
//                Ticket provideArms = new ProvideResourceTicket(leader, this);
                provideArms.execute();
            } // end if
        } // end if
    }
    
    /**
     * Returns the role's resource.
     * @return Resource The role's resource.
     */
    public Resource getArms() { return arms; }
    
    /**
     * Returns whether or not the specialist provided arms during the last turn.
     * @return boolean Whether or not the specialist provided arms during the
     * last turn.
     */
    public boolean providedArms() { return providedArms; }
    
    /**
     * Sets the latch for providing arms during a turn.
     */
    public void setProvidedArmsLatch() { providedArms = true; }
    
    /**
     * Resets the latch for providing arms during a turn.
     */
    public void resetProvidedArmsLatch() { providedArms = false; }
    
    /**
     * Returns whether or not the specialist was requested to provide arms by
     * a leader in the last turn.
     * @return boolean Whether or not the specialist was requested to provide
     * arms by a leader in the last turn.
     */
    public boolean armsRequested() { return armsRequested; }
    
    /**
     * Sets the latch for a leader requesting a resource from the specialist
     * during a turn.
     */
    public void setArmsRequestedLatch() { armsRequested = true; }
    
    /**
     * Resets the latch for a leader requesting a resource from the specialist
     * during a turn.
     */
    public void resetArmsRequestedLatch() { armsRequested = false; }
    
    /**
     * Sets the weight of the leader's request.
     * @param w The weight of the leader's request.
     */
    public void setRequestWeight(int w) {
        requestWeight = w;
//        System.out.println("Request weight = " + requestWeight);
    } // end setRequestWeight
    
    /**
     * Returns the weight of a leader's request to the specialist.
     * @return int The weight of a leader's request to the specialist.
     */
    public int getRequestWeight() { return requestWeight; }

    /**
     * Returns the weight of a leader's request to zero.
     */
    public void resetRequestWeight() { requestWeight = 0; }
    
    /**
     * Returns this role's display symbol.
     * @return String This role's display symbol.
     */
    public String getSymbol() { return symbol; }
    
    /**
     * Returns this role's display color.
     * @return Color This role's display color.
     */
    public Color getBackgroundColor() { return roleBackgroundColor; }
    
    /**
     * Returns this role's display text color.
     * @return Color This role's display text color.
     */
    public Color getTextColor() { return textColor; }
    
    /**
     * Returns the current value of the stuck counter.
     * @return int The current value of the stuck counter.
     */
    public int getStuckCounter() { return stuckCounter; }
    
    /**
     * Increments the stuck counter by one.
     */
    public void incrementStuckCounter() { stuckCounter++; }
    
    /**
     * Resets the stuck counter to zero.
     */
    public void resetStuckCounter() { stuckCounter = 0; }
    
    /**
     * Returns the stuck counter threshold.
     * @return int The stuck counter threshold.
     */
    public int getStuckThreshold() { return stuckThreshold; }
    
}
