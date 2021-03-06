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
 * The leader role models the masterminds of some of the most despicable acts 
 * inflicted upon humankind.  Leaders devise targets and lead the rehearsal and 
 * execution of missions.  Leaders handle the gathering of resources from 
 * specialists to meet the target requirements and work with trainers and 
 * recruiters for their manpower requirements.  The TNS allows leaders to grow 
 * from small-time thugs to the kingpins of large criminal and terrorist 
 * organizations.  As each leader successfully completes a mission, he becomes 
 * more experienced and more influential, raising his stature in the 
 * organization over time, and therefore becoming capable of pulling off acts 
 * of terrorism that reach the front pages of newspapers worldwide.  For these
 * leaders to accomplish all of these goals, they need to establish 
 * relationships.
 * @author  Rob Michael and Zac Staples
 */
public class LeaderRole implements TNSRole {
    
    /**
     * The role's symbol.
     */
    private String symbol = "L";
    /**
     * The role's display background color.
     */
    private Color roleBackgroundColor = Color.red;
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
     * The leader's mission.
     */
    private Mission mission;
    
    /**
     * This role's stuck counter.
     */
    private int stuckCounter;
    /**
     * The stuck counter threshold.
     */
    private int stuckThreshold;
    
    /**
     * The stuck counter for getting operators.
     */
    private int getOperatorsStuckCounter;
    
    /** 
     * Creates a new instance of LeaderRole 
     * @param agent The agent that owns this role.
     */
    public LeaderRole(Agent agent) {
        this.agent = agent;
        name = new String("LeaderRole");
//        System.out.println(agent.getAgentName() + "'s " + name + " created.");
        goals = new Vector();
//        System.out.println("Adding goals...");
        goals.add(new GetOperatorsGoal(this));
        goals.add(new PlanMissionGoal(this));
        goals.add(new LeadRehearsalGoal(this));
        goals.add(new LeadMissionGoal(this));
        goals.add(new RequestFinancesGoal(this));
        goals.add(new RequestLogisticsGoal(this));
        goals.add(new RequestArmsGoal(this));
        goals.add(new RequestTrainingGoal(this));
        goals.add(new ConvertOperatorGoal(this));
        sensors = new Vector();
        sensors.add(new RecruiterSensor(25.0));
        sensors.add(new TrainerSensor(25.0));
//        System.out.println("Adding sensors...");
//        addSensor(new ContactSensor(0.0));
        relationships = new Vector();
//        System.out.println("Adding relationships...");
        relationships.add(new String("OrganizeMissionCellRelationship"));
        relationships.add(new String("CellOpsRelationship"));
        relationships.add(new String("FinancialBarteringRelationship"));
        relationships.add(new String("LogisticsBarteringRelationship"));
        relationships.add(new String("ArmsBarteringRelationship"));
        relationships.add(new String("RecruiterRecruitingRelationship"));
        relationships.add(new String("TrainerRecruitingRelationship"));
        agent.addRelationshipNames(relationships);
        connectors = new Vector();
        connectors.add(new LeadRehearsalConnector(this));
        connectors.add(new LeadMissionConnector(this));
        connectors.add(new RequestFinancesConnector(this));
        connectors.add(new RequestLogisticsConnector(this));
        connectors.add(new RequestArmsConnector(this));
        connectors.add(new ConvertOperatorConnector(this));
        connectors.add(new FindRecruiterConnector(this));
        connectors.add(new FindTrainerConnector(this));
        tickets = new Vector();
        tickets.add(new PlanMissionTicket(this));
        tickets.add(new GetOperatorsTicket(this));
        tickets.add(new LeadRehearsalTicket(this));
        tickets.add(new LeadMissionTicket(this));
//        tickets.add(new RequestFinancesTicket(this));
//        tickets.add(new RequestLogisticsTicket(this));
//        tickets.add(new RequestArmsTicket(this));
        tickets.add(new RequestTrainingTicket(this));
//        tickets.add(new ConvertOperatorTicket(this));
        mission = null;
        stuckCounter = 0;
        stuckThreshold = 15;
        getOperatorsStuckCounter = 0;
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
     * Returns the leader's mission.
     * @return Mission The leader's mission.
     */
    public Mission getMission() { return mission; }
    
    /**
     * Assigns a mission to the leader.
     * @param m The mission to be assigned.
     */
    public void assignMission(Mission m) {
        mission = m;
    } // end assignMission
    
    /**
     * This method de-references the mission from the leader.
     */
    public void clearMission() {
        mission = null;
    } // end clearMission
    
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
    
    /**
     * Returns the current value of the stuck counter for getting operators.
     * @return int The current value of the stuck counter for getting operators.
     */
    public int getGetOperatorsStuckCounter() { return getOperatorsStuckCounter; }
    
    /**
     * Increments the stuck counter for getting operators by one.
     */
    public void incrementGetOperatorsStuckCounter() { getOperatorsStuckCounter++; }
    
    /**
     * Resets the stuck counter for getting operators to zero.
     */
    public void resetGetOperatorsStuckCounter() { getOperatorsStuckCounter = 0; }
    
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
