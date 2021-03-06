package tns.goals;
import tns.roles.*;
import tns.tickets.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * Specialists also have a behavior that allows them to advance to a leader 
 * under certain conditions.  The first condition occurs if a specialist has 
 * become bored through continually providing a resource without anyone 
 * requesting that resource, he will go off and promote himself to a leader and
 * start his own cell.  The other condition of this behavior occurs if the
 * specialist becomes disavowed from the organization by losing his connections 
 * to the main network system, in which the specialist advances himself to a 
 * leader and starts a splinter cell.
 * @author  Rob Michael and Zac Staples
 */
public class AdvanceGoal implements TNSGoal {
    
    /**
     * The role that owns this goal.
     */
    private Role role;
    /**
     * The class Name of the goal.
     */
    private String goalName;
    /**
     * The type of goal as defined in the TNSGoal interface.
     */
    private int goalType;
    /**
     * The names of relationships this role can belong to.
     */
    private Vector relationshipNames;
    /**
     * The current weight of the goal.
     */
    private int weight;
    /**
     * Whether or not the goal has been completed.
     */
    private boolean completed;

    /** 
     * Creates a new instance of AdvanceGoal 
     * @param role The role that owns this goal.
     */
    public AdvanceGoal(Role role) {
        this.role = role;
        goalName = new String("AdvanceGoal");
//        System.out.println(goalName + " created.");
        relationshipNames = new Vector();
        relationshipNames.add(new String("FinancialBarteringRelationship"));
        relationshipNames.add(new String("LogisticsBarteringRelationship"));
        relationshipNames.add(new String("ArmsBarteringRelationship"));
        weight = 0;
        completed = false;
        goalType = TNSGoal.ORGANIZE;
    }
    
    /** Adds the passed in Rule to the ruleList Vector. <B>Not used in the TNS.</B>
     * @param pRule The Rule that is to be added
     */
    public void addRule(Rule pRule) {}
    
    /** Assigns credit to itself (i.e. updates goal attainment weight )and the
     * lastRule by receiving the new PerceivedEnvironment to the Goal and letting
     * the Goal determine the credit assignment to itself (goal attainment) and
     * all of the Rules associated with that Goal, both active and inactive.
     * Assigns the Rule with the highest weight to the activeRule.
     * @param pPE The agent's perceived environment.
     *
     */
    public void assignCredit(SensedEnvironment pPE) {
        int produceWeight = 0;
        int provideWeight = 0;
        int stuckWeight = 0;
        int threshold = ((TerroristAgentPersonality)((TNSRole)role).getAgent().getPersonality()).getInfluence();
        int currentLevel = 0;
        if (role instanceof ArmsDealerRole) {
            currentLevel = ((ArmsDealerRole)role).getArms().getLevel();
        } else if (role instanceof FinancierRole) {
            currentLevel = ((FinancierRole)role).getFinances().getLevel();
        } else if (role instanceof LogisticianRole) {
            currentLevel = ((LogisticianRole)role).getLogistics().getLevel();
        } // end if-else
        int neededResource = threshold - currentLevel;
        if (neededResource < 0) {
            produceWeight = 0;
        } else {
            produceWeight = neededResource * 5;
        } // end if-else

        int minimumResourceLevel = (int) (0.1 * threshold);
        if (currentLevel <= minimumResourceLevel) {
            provideWeight = 0;
        } else {
            provideWeight = (currentLevel - minimumResourceLevel) * 5;
        } // end if-else
        
        if (role instanceof ArmsDealerRole) {
            stuckWeight = ((ArmsDealerRole)role).getStuckCounter() * 10;
        } else if (role instanceof FinancierRole) {
            stuckWeight = ((FinancierRole)role).getStuckCounter() * 10;
        } else if (role instanceof LogisticianRole) {
            stuckWeight = ((LogisticianRole)role).getStuckCounter() * 10;
        } // end if-else
        
        weight = stuckWeight - (produceWeight + provideWeight);
        if (weight < 0) {
            weight = 0;
        } // end if
//        System.out.println(goalName + " weight: " + weight);
    }
    
    /** Gets the Goal's active rule. <B>Not used in the TNS.</B>
     * @return the Goals active rule
     */
    public Rule getActiveRule() { return null; }
    
    /** Gets the Goal name
     * @return a String of the goals name (should be the goal class name)
     *
     */
    public String getGoalName() { return goalName; }
    
    /** Gets the Goal type
     * @return an Int representing the goal type
     *
     */
    public int getGoalType() { return goalType; }
    
    /**
     * Returns the weight of the goal.
     * @return Number The weight of the goal.
     */
    public Number getGoalWeight() { return new Integer(weight); }
    
    /** Gets the Goal past active rule. <B>Not used in the TNS.</B>
     * @return the Goals oast active rule
     */
    public Rule getPastActiveRule() { return null; }
    
    /**
     * Returns the role that owns this goal.
     * @return Role The role that owns this goal.
     */
    public Role getRole() { return role; }
    
    /** Returns the ruleList Vector. <B>Not used in the TNS.</B>
     * @return ruleList
     */
    public Vector getRuleList() { return null; }
    
    /**
     * Marks the goal complete by setting the goal completion boolean flag.
     */
    public void markGoalComplete() {
        completed = true;
//        System.out.println(goalName + " completed.");
    }
    
    /** Removes the Rule from the ruleList Vector. <B>Not used in the TNS.</B>
     * @param pRule  The Rule that is to be removed
     * @return The success of the removal operation:
     * <li> true - The Rule was successfully removed
     * <li> false - The Rule did not exist in the Goal's ruleList
     */
    public boolean removeRule(Rule pRule) {
        return false;
    } // end removeRule
    
    /**
     * Passes the PerceivedEnvironment to the activeRule in its calculate()
     * method. <B>Not used in the TNS</B>
     * @param pPE The sensed environment.
     * @return Object An Object associated with the active Rules calculation.
     */
    public Object runActiveRule(SensedEnvironment pPE) {
        return null;
    } // end runActiveRule
    
    /** Sets the ActiveRule, used for goal attainment. <B>Not used in the TNS.</B>
     * @param pRule The agent's active rule.
     */
    public void setActiveRule(Rule pRule) {}
    
    /** Sets the Goals type, the developer should designate specific goal types to
     * be used in the RELATE architecture.
     * @param pType Integer representing the goal type.
     *
     */
    public void setGoalType(int pType) {
        goalType = pType;
    }
    
    /** Sets the PastActiveRule, used for comparison and rule weight calculation. <B>Not used in the TNS.</B>
     * @param pRule The agent's past active rule.
     */
    public void setPastActiveRule(Rule pRule) {}
    
    /**
     * Takes whatever action is necessary for this particular goal in the form
     * of a ticket.  Replaces the run active rule method from the RELATE
     * architecture in the TNS.
     */
    public void executeActiveTicket() {
        Ticket advanceTicket = new AdvanceTicket(role);
        advanceTicket.execute();
    }
    
    /**
     * Returns whether or not the goal has been completed.
     * @return boolean Whether or not the goal has been completed.
     */
    public boolean isCompleted() { return completed; }
    
    /**
     * Resets the goal to the uncompleted state by reseting the boolean flag.
     */
    public void resetGoal() {
        completed = false;
    }
    
}
