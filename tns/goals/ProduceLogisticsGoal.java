package tns.goals;
import tns.roles.*;
import tns.tickets.*;
import tns.agents.*;
import mil.navy.nps.relate.*;
import java.util.*;

/**
 * With this goal, the specialist exhibits the behavior that he will produce 
 * resources until he exceeds his capacity to acquire the resource.  The agent's
 * influence value determines how much of a resource an agent can stockpile.  
 * The idea with this design decision was that more influential specialists 
 * would have access to greater levels of resources as described above.  In 
 * evaluating this goal, the specialist checks if providing a resource was the 
 * active goal the turn before and if the agent did not provide a resource, then 
 * an additional weight is given to this goal so that the specialist has a 
 * chance to go back to producing resources and not become stuck on providing 
 * resources.
 * @author  Rob Michael and Zac Staples
 */
public class ProduceLogisticsGoal implements TNSGoal {
    
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
     * The name of relationship this role belongs to.
     */
    private String relationshipName;
    /**
     * The current weight of the goal.
     */
    private int weight;
    /**
     * Whether or not the goal has been completed.
     */
    private boolean completed;

    /** 
     * Creates a new instance of ProduceLogisticsGoal 
     * @param role The role that owns this goal.
     */
    public ProduceLogisticsGoal(Role role) {
        this.role = role;
        goalName = new String("ProduceLogisticsGoal");
//        System.out.println(goalName + " created.");
        relationshipName = new String("LogisticsBarteringRelationship");
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
        int threshold = ((TerroristAgentPersonality)((TNSRole)role).getAgent().getPersonality()).getInfluence();
        int currentLevel = ((LogisticianRole)role).getLogistics().getLevel();
        int neededLogistics = threshold - currentLevel;
        if (neededLogistics < 0) {
            weight = 0;
        } else {
            weight = neededLogistics * 10;
        } // end if-else
        Goal lastActiveGoal = ((TerroristAgent)((TNSRole)role).getAgent()).getPastActiveGoal();
        if (lastActiveGoal != null && lastActiveGoal instanceof ProvideLogisticsGoal && 
            !((LogisticianRole)role).providedLogistics()) {
            weight = (int) (1.5 * weight);
        } // end if
        ((LogisticianRole)role).resetProvidedLogisticsLatch();
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
        Vector tickets = ((TNSRole)role).getTickets();
        Enumeration e = tickets.elements();
        while (e.hasMoreElements()) {
            Ticket t = (Ticket)e.nextElement();
            if (t instanceof ProduceLogisticsTicket) {
                t.execute();
            }  // end if
        } // end while
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
