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
 * Operators are the workhorses of the organization, providing the muscle and 
 * manpower to accomplish a leader's mission.  Operators have the potential to 
 * advance up in the organization, either through a type of self-promotion or 
 * through a leader-directed promotion.
 * @author  Rob Michael and Zac Staples
 */
public class OperatorRole implements TNSRole, ConnectorChangeListener {
    
    /**
     * The role's symbol.
     */
    private String symbol = "O";
    /**
     * The role's display background color.
     */
    private Color roleBackgroundColor = Color.darkGray;
    /**
     * The role's display text color.
     */
    private Color textColor = Color.white;
    
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
     * The number of turns the operator has rehearsed his current mission.
     */
    private int rehearseTurns;
    /**
     * The number of turns the operator has executed his current mission.
     */
    private int executeTurns;
    
    /**
     * The operator's current target.
     */
    private Target currentTarget;

    /**
     * This role's stuck counter.
     */
    private int stuckCounter;
    /**
     * The stuck counter threshold.
     */
    private int stuckThreshold;
    
    /** 
     * Creates a new instance of OperatorRole 
     * @param agent The agent that owns this role.
     */
    public OperatorRole(Agent agent) {
        this.agent = agent;
        name = new String("OperatorRole");
//        System.out.println(agent.getAgentName() + "'s " + name + " created.");
        goals = new Vector();
//        System.out.println("Adding goals...");
        goals.add(new JoinLeaderGoal(this));
        goals.add(new RehearseMissionGoal(this));
        goals.add(new ExecuteMissionGoal(this));
        goals.add(new OperatorAdvanceGoal(this));
        sensors = new Vector();
//        System.out.println("Adding sensors...");
//        addSensor(new ContactSensor(0.0));
        relationships = new Vector();
//        System.out.println("Adding relationships...");
        relationships.add(new String("OrganizeMissionCellRelationship"));
        relationships.add(new String("CellOpsRelationship"));
        agent.addRelationshipNames(relationships);
        connectors = new Vector();
        connectors.add(new JoinLeaderConnector(this));
        tickets = new Vector();
        tickets.add(new RehearseMissionTicket(this));
        tickets.add(new ExecuteMissionTicket(this));
        rehearseTurns = 0;
        executeTurns = 0;
        currentTarget = null;
        stuckCounter = 0;
        stuckThreshold = 15;
    }
    
    /**
     * Method for hearing a connector.  The operator can hear Lead Rehearsal,
     * Lead Mission, and Convert Operator connectors.
     * @param cce The connector change event that encapsulates the connector
     * and its associated ticket.
     */
    public void ConnectorChanged(ConnectorChangeEvent cce) {
        Connector c = cce.getSource();
        if (c.isExtended()) {
            if (c instanceof LeadRehearsalConnector &&
                (agent.getActiveGoal() instanceof RehearseMissionGoal || 
                 agent.getActiveGoal() instanceof JoinLeaderGoal)) {
                Role leader = c.getRole();
                Agent leaderAgent = ((TNSRole)leader).getAgent();
                Mission leadersMission = ((LeaderRole)leader).getMission();
                Target leadersTarget = leadersMission.getTarget();
                if (currentTarget != null && currentTarget == leadersTarget) {
//                    System.out.println(agent.getAgentName() + " detected LeadRehearsalConnector.");
                    Iterator i = tickets.iterator();
                    while (i.hasNext()) {
                        Ticket ticket = (Ticket)i.next();
                        if (ticket instanceof RehearseMissionTicket) {
                            ticket.execute();
                        } // end if
                    } // end while
                    AgentPair pair = new AgentPair(agent, leaderAgent);
                    ((TerroristAgent)agent).changeHistory(pair, 5);
                } // end if
            } else if (c instanceof LeadMissionConnector &&
                       agent.getActiveGoal() instanceof ExecuteMissionGoal) {
//                System.out.println(agent.getAgentName() + " detected LeadMissionConnector.");
                Role leader = c.getRole();
                Agent leaderAgent = ((TNSRole)leader).getAgent();
                Mission leadersMission = ((LeaderRole)leader).getMission();
                Target leadersTarget = leadersMission.getTarget();
                if (currentTarget == leadersTarget) {
                    Iterator i = tickets.iterator();
                    while (i.hasNext()) {
                        Ticket ticket = (Ticket)i.next();
                        if (ticket instanceof ExecuteMissionTicket) {
                            ticket.execute();
                        } // end if
                    } // end while
                    AgentPair pair = new AgentPair(agent, leaderAgent);
                    ((TerroristAgent)agent).changeHistory(pair, 5);
                } // end if
            } else if (c instanceof ConvertOperatorConnector &&
                       (agent.getActiveGoal() instanceof RehearseMissionGoal || 
                        agent.getActiveGoal() instanceof ExecuteMissionGoal)) {
                if (((ConvertOperatorConnector)c).getAgent() == agent) {
//                    System.out.println(agent.getAgentName() + " detected ConvertOperatorConnector.");
                    String newRole = ((ConvertOperatorConnector)c).getNewRole();
                    Role leader = c.getRole();
                    Agent leaderAgent = ((TNSRole)leader).getAgent();
                    AgentPair pair = new AgentPair(agent, leaderAgent);
                    ((TerroristAgent)agent).changeHistory(pair, 5);
                    Ticket operatorAdvance = new PromoteOperatorTicket(this, newRole);
                    operatorAdvance.execute();
                } // end if
            } // end if-else
        } // end if
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
     * Returns the number of turns the operator has rehearsed the current
     * mission.
     * @return int The number of turns the operator has rehearsed the current
     * mission.
     */
    public int getRehearseTurns() { return rehearseTurns; }
    
    /**
     * Increments the number of turns the operator has rehearsed the current
     * mission by one.
     */
    public void incrementRehearseTurns() { rehearseTurns++; }
    
    /**
     * Returns the number of turns the operator has executed the current
     * mission.
     * @return int The number of turns the operator has executed the current
     * mission.
     */
    public int getExecuteTurns() { return executeTurns; }
    
    /**
     * Increments the number of turns the operator has executed the current
     * mission by one.
     */
    public void incrementExecuteTurns() { executeTurns++; }
    
    /**
     * Returns the operator's current target.
     * @return Target The operator's current target.
     */
    public Target getCurrentTarget() { return currentTarget; }
    
    /**
     * Assigns a target to the operator.
     * @param target The target to be assigned.
     */
    public void assignTarget(Target target) {
        currentTarget = target;
    } // end assignTarget
    
    /**
     * This method de-references the operator's target and resets the number
     * of rehearse and execute turns to zero.
     */
    public void clearTarget() {
        currentTarget = null;
        rehearseTurns = 0;
        executeTurns = 0;
    }
    
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
