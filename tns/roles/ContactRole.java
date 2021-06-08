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
 * The contact role represents those individual who are sought out by recruiters
 * looking for potential supporters of the organizations cause.  Contacts exist 
 * in the scenario for a particular period of time in which they may be 
 * contacted by a recruiter.  At the time of contact, the contact decides 
 * whether or not to join the organization.  If the contact does decide to join 
 * the organization, he will turn into a recruit.
 * @author  Rob Michael and Zac Staples
 */
public class ContactRole implements TNSRole, ConnectorChangeListener {

    /**
     * The role's symbol.
     */
    private String symbol = "C";
    /**
     * The role's display background color.
     */
    private Color roleBackgroundColor = Color.lightGray;
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
     * Whether or not the contact has been contacted by a recruiter.
     */
    private boolean beenContacted;
    /**
     * Whether or not the contact has joined the organization.
     */
    private boolean joined;
    
    /**
     * This role's stuck counter.
     */
    private int stuckCounter;
    /**
     * The stuck counter threshold.
     */
    private int stuckThreshold;
    
    /** 
     * Creates a new instance of Contact 
     * @param agent The agent that owns this role.
     */
    public ContactRole(Agent agent) {
        this.agent = agent;
        name = new String("ContactRole");
//        System.out.println(agent.getAgentName() + "'s " + name + " created.");
        goals = new Vector();
//        System.out.println("Adding goals...");
        Goal makeContact = new MakeContactGoal(this);
        addNewGoal(makeContact);
        addNewGoal(new BecomeRecruitGoal(this));
        agent.setActiveGoal(makeContact);
        sensors = new Vector();
//        System.out.println("Adding sensors...");
//        addSensor(new ContactSensor(0.0));
        relationships = new Vector();
//        System.out.println("Adding relationships...");
        relationships.add(new String("OperatorRecruitingRelationship"));
        agent.addRelationshipNames(relationships);
        connectors = new Vector();
        tickets = new Vector();
//        addTicket(new MakeContactTicket(this));
        addTicket(new BecomeRecruitTicket(this));
        addTicket(new IncrementStuckCounterTicket(this));
        beenContacted = false;
        joined = false;
        stuckCounter = 0;
        stuckThreshold = 5;
    }
    
    /**
     * Method for hearing a connector.  The can can hear Find Contact connectors.
     * @param cce The connector change event that encapsulates the connector
     * and its associated ticket.
     */
    public void ConnectorChanged(ConnectorChangeEvent cce) {
        // Here the contact needs to evaluate whether or not to join
        // the Terrorist Organization.  If so, need to create a link to the
        // "recruiter", mark the MakeContactGoal complete, and switch roles
        // to Recruit
        
        // For Build 1, assume test to join is good
        if (cce.getSource() instanceof FindContactConnector && 
            cce.getSource().isExtended() &&
            agent.getActiveGoal() instanceof MakeContactGoal & !beenContacted) {
//            System.out.println(agent.getAgentName() + " detected FindContactConnector.");
            
            // Check to see if the contact joins
            Role recruiter = ((FindContactConnector)cce.getSource()).getRole();
            Agent recruiterAgent = ((TNSRole)recruiter).getAgent();
            int recruiterInfluence = ((TerroristAgentPersonality)((TerroristAgent)recruiterAgent).getPersonality()).getInfluence();
            int contactAllegiance = ((TerroristAgentPersonality)((TerroristAgent)agent).getPersonality()).getAllegiance(); 
            ContactDecision decision = new ContactDecision(recruiterInfluence, contactAllegiance);
            if (decision.decide()) {
                Ticket makeContact = new MakeContactTicket(this, recruiterAgent);
                makeContact.execute();
                beenContacted = true;
                joined = true;
//                System.out.println("\t\t\t\t\t\t\t\t\t\t\t" + agent.getEntityName() + " joined.");
            } else {
                beenContacted = true;
                joined = false;
//                System.out.println("\t\t\t\t\t\t\t\t\t\t\t" + agent.getEntityName() + " DID NOT JOIN!");
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
    
    /** Getter for goalList
     * @return goalList vector
     *
     */
    public Vector getGoalListVec() { return goals; }
    
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
     * Returns the role's connectors.
     * @return Vector The role's connectors.
     */
    public Vector getConnectors() {
        return connectors;
    } // end getConnectors
    
    /**
     * Returns the agent that owns this role.
     * @return Agent The agent that owns this role.
     */
    public Agent getAgent() { return agent; }
    
    /**
     * Returns the role's tickets.
     * @return Vector The role's tickets.
     */
    public Vector getTickets() { return tickets; }
    
    /**
     * Adds a ticket to the agent's collection of tickets.
     * @param t The ticket to be added.
     */
    public void addTicket(Ticket t) {
        tickets.add(t);
    }
    
    /**
     * Returns the names of the relationships this role is a part of.
     * @return Vector The names of the relationships this role is a part of.
     */
    public Vector getRelationships() { return relationships; }
    
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
     * Returns whether or not the contact has been contacted by a recruiter.
     * @return boolean Whether or not the contact has been contacted by a
     * recruiter.
     */
    public boolean beenContacted() { return beenContacted; }
    
    /**
     * Returns whether or not the contact has joined the organization.
     * @return boolean Whether or not the contact has joined the organizaton.
     */
    public boolean joined() { return joined; }
    
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
