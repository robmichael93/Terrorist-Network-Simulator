package tns.roles;
import tns.agents.*;
import tns.connectors.*;
import tns.goals.*;
import tns.sensors.*;
import tns.tickets.*;
import mil.navy.nps.relate.*;
import java.util.*;
import java.awt.*;

/**
 * Trainers model those individuals in the organization who are good at what 
 * they do as far as the ins and outs of mission operations.  As such, these 
 * trainers are ideal for passing that knowledge down to young recruits. 
 * Trainers are like career Non-Commissioned Officers (NCOs) in militaries 
 * today, people who’ve “been around the block” and are looking to share that
 * wisdom with raw recruits.  Trainers also sit at the top of one track of 
 * advancement for individuals in the organization.  Those individuals who 
 * either do not become promoted by a leader to a specialist role and continue 
 * to gain experience and influence through carrying out missions, or become 
 * disavowed by the organization, convert into trainers and do not advance to
 * any other role beyond the trainer.
 * @author  Rob Michael and Zac Staples
 */
public class TrainerRole implements TNSRole, ConnectorChangeListener {
    
    /**
     * The role's symbol.
     */
    private String symbol = "T";
    /**
     * The role's display background color.
     */
    private Color roleBackgroundColor = Color.yellow;
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
     * Creates a new instance of Trainer 
     * @param agent The agent that owns this role.
     */
    public TrainerRole(Agent agent) {
        this.agent = agent;
        name = new String("TrainerRole");
//        System.out.println(agent.getAgentName() + "'s " + name + " created.");
        goals = new Vector();
//        System.out.println("Adding goals...");
        goals.add(new TrainRecruitsGoal(this));
        goals.add(new SendOperatorsToLeaderGoal(this));
        sensors = new Vector();
//        System.out.println("Adding sensors...");
//        addSensor(new ContactSensor(0.0));
        relationships = new Vector();
//        System.out.println("Adding relationships...");
        relationships.add(new String("RecruitTrainingRelationship"));
        relationships.add(new String("OrganizeMissionCellRelationship"));
        relationships.add(new String("TrainerRecruitingRelationship"));
        agent.addRelationshipNames(relationships);
        connectors = new Vector();
        tickets = new Vector();
    }
    
    /**
     * Method for hearing a connector.  The trainer can hear Become Operator,
     * Join Leader and Find Trainer connectors.
     * @param cce The connector change event that encapsulates the connector
     * and its associated ticket.
     */
    public void ConnectorChanged(ConnectorChangeEvent cce) {
        Connector c = cce.getSource();
        if (c.isExtended()) {
            if (c instanceof BecomeOperatorConnector &&
                agent.getActiveGoal() instanceof TrainRecruitsGoal) {
//                System.out.println(agent.getAgentName() + " detected BecomeOperatorConnector.");
                Role recruit = c.getRole();
                Agent recruitAgent = ((TNSRole)recruit).getAgent();
                AgentPair pair = new AgentPair(agent, recruitAgent);
                ((TerroristAgent)agent).changeHistory(pair, 5);
                Ticket trainRecruits = new TrainRecruitsTicket(recruit);
                trainRecruits.execute();
            } else if (c instanceof JoinLeaderConnector &&
                       agent.getActiveGoal() instanceof SendOperatorsToLeaderGoal) {
//                System.out.println(agent.getAgentName() + " detected JoinLeaderConnector.");
                Role recruit = c.getRole();
                Agent recruitAgent = ((TNSRole)recruit).getAgent();
                AgentPair pair = new AgentPair(agent, recruitAgent);
                ((TerroristAgent)agent).changeHistory(pair, 5);
                Ticket sendOperatorToLeader = new SendOperatorsToLeaderTicket(this, c);
                sendOperatorToLeader.execute();
            } else if (c instanceof FindTrainerConnector) {
//                System.out.println(agent.getAgentName() + " detected FindTrainerConnector.");
                Role leader = c.getRole();
                Agent leaderAgent = ((TNSRole)leader).getAgent();
                Ticket ticket = ((FindTrainerConnector)c).getTicket();
                Ticket linkWithLeader = new LinkWithLeaderTicket(this, leaderAgent, ticket);
                linkWithLeader.execute();
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
    
}
