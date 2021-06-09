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
 * A recruit represents a greenhorn in the organization.  Recruits join the 
 * organization with a given level of allegiance and if that level is not high 
 * enough for the recruiter, then the recruiter will take that recruit out on a 
 * mini-mission to let him prove his worth, possibly with other recruits.  Once 
 * a recruit has been "proven," then the recruiter sends him off to a trainer to
 * be turned into an operator.
 * @author  Rob Michael and Zac Staples
 */
public class RecruitRole implements TNSRole {
    
    /**
     * The role's symbol.
     */
    private String symbol = "r";
    /**
     * The role's display background color.
     */
    private Color roleBackgroundColor = Color.gray;
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
     * Creates a new instance of RecruitRole 
     * @param agent The agent that owns this role.
     */
    public RecruitRole(Agent agent) {
        this.agent = agent;
        name = new String("RecruitRole");
//        System.out.println(agent.getAgentName() + "'s " + name + " created.");
        goals = new Vector();
//        System.out.println("Adding goals...");
        goals.add(new ProveAllegianceGoal(this));
        goals.add(new GetTrainedGoal(this));
        goals.add(new BecomeOperatorGoal(this));
        sensors = new Vector();
//        System.out.println("Adding sensors...");
//        addSensor(new ContactSensor(0.0));
        relationships = new Vector();
//        System.out.println("Adding relationships...");
        relationships.add(new String("RecruitTrainingRelationship"));
        agent.addRelationshipNames(relationships);
        connectors = new Vector();
        connectors.add(new ProveAllegianceConnector(this));
        connectors.add(new GetTrainedConnector(this));
        connectors.add(new BecomeOperatorConnector(this));
        tickets = new Vector();
        tickets.add(new ExtendConnectorTicket(this, "ProveAllegianceConnector"));
        tickets.add(new ExtendConnectorTicket(this, "GetTrainedConnector"));
        tickets.add(new BecomeOperatorTicket(this));
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
    }
    
    /** Setter for sensorList
     * @param pSensorList The sensor list being set.
     *
     */
    public void setSensorList(Vector pSensorList) {
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
    
}
