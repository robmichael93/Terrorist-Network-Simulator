package tns.roles;
import tns.agents.*;
import tns.connectors.*;
import tns.goals.*;
import tns.sensors.*;
import tns.tickets.*;
import tns.actions.*;
import tns.frames.*;
import mil.navy.nps.relate.*;
import java.util.*;
import java.awt.*;

/**
 * The first of three specialist roles, the financier provides the 
 * ever-essential component for any operation, money.  In the specialist roles, 
 * the agent�s experience drives their ability to produce their particular 
 * resource and their influence controls how much resource; therefore 
 * specialists represent a range of individuals in this class.  Financiers with 
 * a low experience and influence model individuals only capable of knocking
 * over some convenience stores or conducting credit card fraud to grab a 
 * relatively small amount of money.  Experienced and influential financiers on 
 * the hand are like bankers who know how to move large amounts of money 
 * discreetly, or oil sheiks who want to support the organization while at the 
 * same time appearing impartial, or individuals with �old money� that donate to 
 * the cause.
 * @author  Rob Michael and Zac Staples
 */
public class FinancierRole implements TNSRole, ConnectorChangeListener {
    
    /**
     * The role's symbol.
     */
    private String symbol = "F";
    /**
     * The role's display background color.
     */
    private Color roleBackgroundColor = Color.green;
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
    private Resource finances;
    /**
     * Flag for whether or not this role attempted to provide a resource during 
     * the last turn.
     */
    private boolean providedFinances;
    /**
     * Flag for whether or not a leader requested resources from this role
     * during the last turn.
     */
    private boolean financesRequested;
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
     * Creates a new instance of FinancierRole 
     * @param agent The agent that owns this role.
     */
    public FinancierRole(Agent agent) {
        this.agent = agent;
        name = new String("FinancierRole");
//        System.out.println(agent.getAgentName() + "'s " + name + " created.");
        goals = new Vector();
//        System.out.println("Adding goals...");
        goals.add(new ProvideFinancesGoal(this));
        goals.add(new ProduceFinancesGoal(this));
        goals.add(new AdvanceGoal(this));
        sensors = new Vector();
//        System.out.println("Adding sensors...");
//        addSensor(new ContactSensor(0.0));
        relationships = new Vector();
//        System.out.println("Adding relationships...");
        relationships.add(new String("FinancialBarteringRelationship"));
        agent.addRelationshipNames(relationships);
        connectors = new Vector();
        tickets = new Vector();
        tickets.add(new ProduceFinancesTicket(this));
        tickets.add(new IncrementStuckCounterTicket(this));
        finances = new Resource(this);
        providedFinances = false;
        financesRequested = false;
        requestWeight = 0;
        stuckCounter = 0;
        stuckThreshold = 15;
    }
    
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
    }
    
    /** Adds the indicated sensor to the sensorList
     * @param pSensor An integer representing the sensor to be added
     *
     */
    public void addSensor(Sensor pSensor) {
        sensors.add(pSensor);
    }
    
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
     *
     */
    public boolean removeGoal(Goal pGoal) {
        return goals.remove(pGoal);
    }
    
    /** Removes the indicated sensor from the sensorList
     * @param pSensor An integer representing the sensor to be removed
     * @return The success of the removal operation:
     * <li> true - The sensor was successfully removed
     * <li> false - The sensor did not exist in the Role's sensorList
     *
     */
    public boolean removeSensor(Sensor pSensor) {
        return sensors.remove(pSensor);
    }
    
    /** Setter for actionList.  Replaces the actionList Vector with a new list of
     * integers representing all of the actions available to the agent due to
     * this Role
     * <B> Not used in the TNS. </B>
     * @param pActionList The action list being set.
     *
     */
    public void setActionList(Vector pActionList) {}
    
    /** Sets the role name of the role object, this should be the exact class name
     * of the role.
     * @param pRoleName  The role name
     *
     */
    public void setRoleName(String pRoleName) {
        name = pRoleName;
    }
    
    /** Setter for sensorList
     * @param pSensorList The sensor list being set.
     *
     */
    public void setSensorList(Vector pSensorList) {
        sensors = pSensorList;
    }
    
    /**
     * Method for hearing a connector.  If a RequestFinancesConnector was 
     * extended and the agent's active goal was ProvideFinances or Advance, then 
     * this stimulator connects and takes the necessary actions.
     * @param cce The connector change event that encapsulates the connector
     * and its associated ticket.
     */
    public void ConnectorChanged(ConnectorChangeEvent cce) {
        Connector c = cce.getSource();
        if (c.isExtended()) {
            if (c instanceof RequestFinancesConnector &&
                agent.getActiveGoal() instanceof ProvideFinancesGoal ||
                agent.getActiveGoal() instanceof AdvanceGoal) {
//                System.out.println(agent.getAgentName() + " detected RequestFinancesConnector.");
                Role leader = c.getRole();
                Agent leaderAgent = ((TNSRole)leader).getAgent();
                AgentPair pair = new AgentPair(agent, leaderAgent);
                ((TerroristAgent)agent).changeHistory(pair, 15);
                Ticket ticket = ((RequestFinancesConnector)c).getTicket();
                Ticket provideFinances = new ProvideResourceTicket(leader, this, ticket);
//                Ticket provideFinances = new ProvideResourceTicket(leader, this);
                provideFinances.execute();
            } // end if
        } // end if
    }
    
    /**
     * Returns the role's resource.
     * @return Resource The role's resource.
     */
    public Resource getFinances() { return finances; }
    
    /**
     * Returns whether or not the specialist provided arms during the last turn.
     * @return boolean Whether or not the specialist provided arms during the
     * last turn.
     */
    public boolean providedFinances() { return providedFinances; }
    
    /**
     * Sets the latch for providing arms during a turn.
     */
    public void setProvidedFinancesLatch() { providedFinances = true; }
    
    /**
     * Resets the latch for providing arms during a turn.
     */
    public void resetProvidedFinancesLatch() { providedFinances = false; }
    
    /**
     * Returns whether or not the specialist was requested to provide arms by
     * a leader in the last turn.
     * @return boolean Whether or not the specialist was requested to provide
     * arms by a leader in the last turn.
     */
    public boolean financesRequested() { return financesRequested; }
    
    /**
     * Sets the latch for a leader requesting a resource from the specialist
     * during a turn.
     */
    public void setFinancesRequestedLatch() { financesRequested = true; }
    
    /**
     * Resets the latch for a leader requesting a resource from the specialist
     * during a turn.
     */
    public void resetFinancesRequestedLatch() { financesRequested = false; }

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
